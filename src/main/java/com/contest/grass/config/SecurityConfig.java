package com.contest.grass.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenUtil jwtTokenUtil;

    // 카카오페이 관련 URL 패턴
    private static final String KAKAOPAY_URL_PATTERN = "/kakaopay/**";

    // 기존 URL 패턴
    private static final String LOGIN_URL = "/login";
    private static final String ROOT_URL = "/";
    private static final String AUTH_URL_PATTERN = "/auth/**";
    private static final String STATIC_RESOURCES_PATTERN = "/(css|js|img)/**";
    private static final String API_URL_PATTERN = "/api/**";
    private static final String LOGIN_ERROR_URL = "/login?error=true";

    // Swagger 관련 경로 추가
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    // favicon.ico 관련 처리 추가
    private static final String FAVICON_URL = "/favicon.ico";

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // 기본 Security 설정 (운영 환경)
    @Bean
    @Profile("!dev")  // 'dev' 프로파일이 아닌 경우에만 이 필터 체인 적용
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ROOT_URL, LOGIN_URL, STATIC_RESOURCES_PATTERN, FAVICON_URL).permitAll() // 기본 경로 허용
                        .requestMatchers(SWAGGER_WHITELIST).permitAll() // Swagger 경로 허용
                        .requestMatchers(KAKAOPAY_URL_PATTERN).permitAll() // 카카오페이 결제 관련 엔드포인트 허용
                        .requestMatchers(AUTH_URL_PATTERN, API_URL_PATTERN).authenticated() // 인증이 필요한 경로
                        .anyRequest().authenticated() // 그 외 나머지 요청도 인증 필요
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
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Please log in.\"}");
                        })
                );

        // JWT 필터 추가 (운영 환경에서만 사용)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 테스트 환경에서 인증 없이 모든 요청 허용
    @Bean
    @Profile("dev")  // 'dev' 프로파일에서만 이 필터 체인 적용
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ROOT_URL, LOGIN_URL, STATIC_RESOURCES_PATTERN, FAVICON_URL).permitAll() // 기본 경로 허용
                        .requestMatchers(SWAGGER_WHITELIST).permitAll() // Swagger 경로 허용
                        .requestMatchers(AUTH_URL_PATTERN, API_URL_PATTERN).permitAll() // 테스트 환경에서는 인증 없이 허용
                        .requestMatchers(KAKAOPAY_URL_PATTERN).permitAll() // 카카오페이 결제 엔드포인트 인증 없이 허용
                        .anyRequest().permitAll() // 모든 요청을 인증 없이 허용
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);  // 테스트 환경에서는 401 대신 200 OK로 응답
                            response.getWriter().write("{\"message\": \"Test environment, no authentication required.\"}");
                        })
                );

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

    // BCryptPasswordEncoder를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // 모든 도메인 허용 (운영 시, 특정 도메인으로 제한 권장)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용 (토큰 등)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}