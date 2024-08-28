package com.contest.grass.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenUtil jwtTokenUtil;

    // 상수 정의
    private static final String LOGIN_URL = "/login";
    private static final String ROOT_URL = "/";
    private static final String AUTH_URL_PATTERN = "/auth/**";
    private static final String CSS_URL_PATTERN = "/css/**";
    private static final String JS_URL_PATTERN = "/js/**";
    private static final String API_URL_PATTERN = "/api/**";
    private static final String LOGIN_ERROR_URL = "/login?error=true";

    // Swagger 관련 경로 추가
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ROOT_URL, AUTH_URL_PATTERN, LOGIN_URL, CSS_URL_PATTERN, JS_URL_PATTERN, API_URL_PATTERN).permitAll()  // URL 패턴 설정
                        .requestMatchers(SWAGGER_WHITELIST).permitAll() // Swagger 경로에 대한 접근 허용
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage(LOGIN_URL)
                        .defaultSuccessUrl(ROOT_URL)
                        .failureUrl(LOGIN_ERROR_URL)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2   // OAuth2 로그인 설정
                        .loginPage(LOGIN_URL)
                        .defaultSuccessUrl(ROOT_URL)
                        .failureUrl(LOGIN_ERROR_URL)
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // 인증되지 않은 요청에 대해 JSON 응답을 반환
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Please log in.\"}");
                        })
                );

        // JWT 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
