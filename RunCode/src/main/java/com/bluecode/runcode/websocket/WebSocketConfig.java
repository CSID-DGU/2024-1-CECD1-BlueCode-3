package com.bluecode.runcode.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CodeExecutionWebSocketHandler codeExecutionHandler;

    public WebSocketConfig(CodeExecutionWebSocketHandler codeExecutionHandler) {
        this.codeExecutionHandler = codeExecutionHandler;
    }

    // 로컬 환경에서는 ws://localhost:8080/codeSocket 로 url 설정
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(codeExecutionHandler, "/codeSocket").setAllowedOrigins("*");
    }
}
