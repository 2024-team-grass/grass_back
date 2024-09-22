package com.contest.grass.config;

import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 최대 중첩 깊이를 10000으로 설정
        objectMapper.getFactory()
                .setStreamWriteConstraints(StreamWriteConstraints.builder().maxNestingDepth(10000).build());
        // JavaTimeModule 등록
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}