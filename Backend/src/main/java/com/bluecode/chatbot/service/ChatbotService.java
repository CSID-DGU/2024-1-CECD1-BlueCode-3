package com.bluecode.chatbot.service;

import com.bluecode.chatbot.config.Rules;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChatbotService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Map<String, String>> conversationHistory = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    @Autowired
    public ChatbotService(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public String getResponse(String userMessage) {
        conversationHistory.add(Map.of("role", "user", "content", userMessage)); // 사용자의 메시지를 기록

        String response = continueConversation();
        conversationHistory.add(Map.of("role", "assistant", "content", response)); // 챗봇의 응답을 기록
        return response;
    }

    private String continueConversation() {
        String rules = loadRules(); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();

        for (Map<String, String> pastMessage : conversationHistory) {
            messages.add(Map.of("role", pastMessage.get("role"), "content", "지난 대화 기록 : " + pastMessage.get("content")));
        }

        messages.add(Map.of("role", "system", "content", "대화 규칙: " + rules));

        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", messages
        );

        return sendPostRequest(body);
    }

    public String sendPostRequest(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", entity, String.class);
            String response = responseEntity.getBody();
            logContent(response);
            return extractContentFromResponse(response);
        } catch (Exception e) {
            logger.error("API call failed", e);
            throw new RuntimeException("Error during API call", e);
        }
    }

    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return "";
        }
    }

    private void logContent(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            String content = root.path("choices").path(0).path("message").path("content").asText();
            logger.info("Response from GPT: {}", content);
        } catch (IOException e) {
            logger.error("응답 JSON 파싱 에러", e);
        }
    }

    private String loadRules() {
        return Rules.text;
    }
}