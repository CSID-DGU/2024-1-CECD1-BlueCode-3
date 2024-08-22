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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(codeExecutionHandler, "/codeSocket").setAllowedOrigins("*");
    }
}
