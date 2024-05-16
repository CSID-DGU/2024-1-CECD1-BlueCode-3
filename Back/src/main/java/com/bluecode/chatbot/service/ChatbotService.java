package com.bluecode.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChatbotService {
    private final WebClient webClient;
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Map<String, String>> conversationHistory = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    @Autowired
    public ChatbotService(WebClient.Builder webClientBuilder, @Value("${api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.apiKey = apiKey;
    }

    public Mono<String> getResponse(String userMessage) {
        conversationHistory.add(Map.of("role", "user", "content", userMessage)); // 사용자의 메시지를 기록

        return continueConversation()
                .map(response -> {
                    conversationHistory.add(Map.of("role", "assistant", "content", response)); // 챗봇의 응답을 기록
                    return response;
                });
    }

    private Mono<String> continueConversation() {
        String rules = loadRules(); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();

        for (Map<String, String> pastMessage : conversationHistory) {
            messages.add(Map.of("role", pastMessage.get("role"), "content", "지난 대화 기록 : " + pastMessage.get("content")));
        }

        messages.add(Map.of("role", "system", "content", "대화 규칙: " + rules));

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );

        return sendPostRequest(body).map(this::extractContentFromResponse);
    }

    private Mono<String> sendPostRequest(Map<String, Object> body) {
        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> status.isError(), response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                    logger.error("API call failed with status: {} and body: {}", response.statusCode(), errorBody);
                    return Mono.error(new RuntimeException("Error during API call with body: " + errorBody));
                }))
                .bodyToMono(String.class)
                .map(response -> {
                    logContent(response);
                    return response;
                });
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
        try {
            ClassPathResource resource = new ClassPathResource("rules.txt");
            return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rules", e);
        }
    }
}
