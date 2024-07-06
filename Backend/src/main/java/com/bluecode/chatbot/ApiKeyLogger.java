package com.bluecode.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyLogger {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyLogger.class);

    @Value("${api.key}")
    private String apiKey;

    public void logApiKey() {
        String maskedApiKey = apiKey.substring(0, 3) + "*".repeat(apiKey.length() - 3);
        logger.info("Current API Key: {}", maskedApiKey);
    }
}