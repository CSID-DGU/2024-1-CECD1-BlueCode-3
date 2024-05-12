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
    private List<String> summarizedConversations = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    @Autowired
    public ChatbotService(WebClient.Builder webClientBuilder, @Value("${api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.apiKey = apiKey;
    }

    public Mono<String> getResponse(String userMessage) {
        return summarizeConversation(userMessage)
                .flatMap(summarized -> {
                    if (!summarized.isEmpty()) {
                        summarizedConversations.add(summarized);
                    }
                    logger.info("요약된 대화 기록: {}", summarizedConversations);
                    return continueConversation(userMessage);
                });
    }

    private Mono<String> summarizeConversation(String message) {
        if (message == null || message.isEmpty()) {
            return Mono.just("");  // 빈 메시지 처리
        }
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", message + " : 10자 이내 단어들만 가지고 요약해줘."));
        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );

        return sendPostRequest(body)
                .map(this::extractContentFromResponse);
    }

    private Mono<String> continueConversation(String userMessage) {
        String rules = loadRules(); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();
        for (String pastMessage : summarizedConversations) {
            messages.add(Map.of("role", "system", "content", "이전 대화 기록: " + pastMessage));
        }
        messages.add(Map.of("role", "user", "content", "대화 규칙: "+rules));
        messages.add(Map.of("role", "user", "content", userMessage));
        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );

        return sendPostRequest(body);
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

    private String extractContentFromResponse(String response) { // 요약된 content 반환
        try {
            JsonNode root = objectMapper.readTree(response);
            String content = root.path("choices").path(0).path("message").path("content").asText();
            return content; // 추출된 'content' 반환
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return ""; // 에러가 발생 시 빈 문자열 반환
        }
    }

    private void logContent(String response) { // 요약되지 않은 content -> log 출력용
        try {
            JsonNode root = objectMapper.readTree(response);
            String content = root.path("choices").path(0).path("message").path("content").asText();
            logger.info("{}", content);
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
