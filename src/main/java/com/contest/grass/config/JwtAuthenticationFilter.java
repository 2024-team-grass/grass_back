package com.contest.grass.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        try {
            // JWT가 Authorization 헤더에 존재하는지 확인
            if (header != null && header.startsWith("Bearer ")) {
                jwt = header.substring(7);

                // 먼저 JWT가 유효한지 확인
                if (jwtTokenUtil.validateToken(jwt)) {
                    username = jwtTokenUtil.getUsernameFromToken(jwt);
                } else {
                    throw new JwtException("Invalid or expired JWT token.");
                }
            }

            // SecurityContext에 인증 정보를 설정
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException e) {
            // JWT 처리 중 예외 발생 시 처리
            LOGGER.log(Level.WARNING, "JWT Exception: {0}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Invalid or expired token\", \"message\": \"" + e.getMessage() + "\"}");
            return;

        } catch (UsernameNotFoundException e) {
            // 사용자를 찾지 못한 경우 처리
            LOGGER.log(Level.WARNING, "User not found: {0}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"User not found\", \"message\": \"" + e.getMessage() + "\"}");
            return;

        } catch (Exception e) {
            // 기타 예외 처리
            LOGGER.log(Level.SEVERE, "Unexpected error: {0}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"An unexpected error occurred\", \"message\": \"" + e.getMessage() + "\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
