package com.bluecode.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class ChatbotService {
    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public ChatbotService(WebClient.Builder webClientBuilder, @Value("${api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.apiKey = apiKey;
    }

    public Mono<String> getResponse(String prompt) {
        return webClient.post()
                .uri("/engines/davinci-codex/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(Map.of("prompt", prompt, "max_tokens", 150))
                .retrieve()
                .bodyToMono(String.class);
    }
}
