package com.contest.grass.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.contest.grass.repository")
@EntityScan(basePackages = "com.contest.grass.entity")
public class JpaConfig {
}

