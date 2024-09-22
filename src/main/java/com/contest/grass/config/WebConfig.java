package com.contest.grass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// CORS 관련 설정 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//         // 특정 경로에 대한 CORS 설정 (API)
//        registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
//        registry.addMapping("/**")
//                .allowedOrigins("*")  // 모든 도메인에서의 요청 허용
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
//                .allowedHeaders("*");  // 모든 헤더 허용

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 도메인 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true); // credentials를 허용할 경우 특정 도메인 명시가 필요

//        // ngrok 및 로컬 도메인 허용
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://your-ngrok-url.ngrok-free.app")  // 로컬과 ngrok 허용
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
//                .allowedHeaders("*")  // 모든 헤더 허용
//                .allowCredentials(false);  // 자격 증명 허용 시 특정 도메인 명시 필수

        // 다른 모든 경로에 대한 CORS 설정
        registry.addMapping("/**")
                .allowedOrigins("http://your-production-domain.com")  // 배포된 클라이언트 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // 자격 증명 허용 시 특정 도메인 명시 필수
    }

//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false)
//                .ignoreAcceptHeader(true)
//                .defaultContentType(MediaType.APPLICATION_OCTET_STREAM);
//    }



}
