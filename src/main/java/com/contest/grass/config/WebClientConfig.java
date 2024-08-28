package com.contest.grass.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


// Spring WebClient 설정
    // HttpClient: Netty 기반의 비동기 HTTP 클라이언트를 생성. 연결 타임아웃과 읽기/쓰기 타임아웃을 설정.
    // WebClient: HTTP 요청을 비동기적으로 보내기 위한 Spring WebClient를 구성.
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        HttpClient client = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))
                )
                .responseTimeout(Duration.ofSeconds(1));

        ClientHttpConnector connector =
                new ReactorClientHttpConnector(client);

        return WebClient.builder().clientConnector(connector).build();
    }
}
