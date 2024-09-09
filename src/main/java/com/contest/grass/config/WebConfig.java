package com.contest.grass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 개발 환경에서 API 경로에 대한 CORS 설정
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")  // 개발 중 프론트엔드 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // 자격 증명 허용

        // 배포 환경에서 API 경로에 대한 CORS 설정
        registry.addMapping("/api/**")
                .allowedOrigins("http://your-production-domain.com")  // 배포된 프론트엔드 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // 정적 리소스 처리 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 파일은 /static 경로로 처리
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // /api/** 경로는 API 요청으로 처리, 정적 리소스에서 제외
    }
}