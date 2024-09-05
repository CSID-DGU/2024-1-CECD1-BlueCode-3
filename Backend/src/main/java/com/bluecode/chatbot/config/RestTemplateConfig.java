package com.bluecode.chatbot.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {

    static final int READ_TIMEOUT = 20000;
    static final int CONN_TIMEOUT = 20000;

    @Bean
    public RestTemplate restTemplate() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(createHttpClient());
        return new RestTemplate(factory);
    }

    private HttpClient createHttpClient() {
        return org.apache.hc.client5.http.impl.classic
                .HttpClientBuilder.create()
                .setConnectionManager(createHttpClientConnectionManager())
                .build();
    }

    private HttpClientConnectionManager createHttpClientConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setSocketTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .setConnectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                        .build())
                .setMaxConnTotal(100)  // 전체 연결 수
                .setMaxConnPerRoute(20) // 각 호스트별 최대 연결 수
                .build();
    }
}