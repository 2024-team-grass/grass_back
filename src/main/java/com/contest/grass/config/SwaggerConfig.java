package com.contest.grass.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "grassAPI",
                version = "v1.0",
                description = "컴퓨터공학부 경진대회 팀 풀떼기 API"
        )
)
public class SwaggerConfig {
}


