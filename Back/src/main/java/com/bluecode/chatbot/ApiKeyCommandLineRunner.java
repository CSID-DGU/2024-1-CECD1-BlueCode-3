package com.bluecode.chatbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyCommandLineRunner implements CommandLineRunner {

    private final ApiKeyLogger apiKeyLogger;

    public ApiKeyCommandLineRunner(ApiKeyLogger apiKeyLogger) {
        this.apiKeyLogger = apiKeyLogger;
    }

    @Override
    public void run(String... args) {
        apiKeyLogger.logApiKey();
    }
}
