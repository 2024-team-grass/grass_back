package com.contest.grass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// CORS 관련 설정 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 특정 경로에 대한 CORS 설정 (API)
        registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
        registry.addMapping("/**")
                .allowedOrigins("*")  // 모든 도메인에서의 요청 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
                .allowedHeaders("*");  // 모든 헤더 허용

        // 다른 모든 경로에 대한 CORS 설정
        registry.addMapping("/**")
                .allowedOrigins("http://your-production-domain.com")  // 배포된 클라이언트 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // 자격 증명 허용 시 특정 도메인 명시 필수
    }

}
