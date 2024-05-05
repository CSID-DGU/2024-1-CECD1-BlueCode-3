package com.bluecode.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bluecode.chatbot.service.ChatbotService;
import reactor.core.publisher.Mono;

@RestController
public class ChatbotController {
    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/chat/{prompt}")
    public Mono<ResponseEntity<String>> getChatResponse(@PathVariable String prompt) {
        return chatbotService.getResponse(prompt)
                .map(response -> ResponseEntity.ok().body(response))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/chat")
    public Mono<ResponseEntity<String>> postChatResponse(@RequestBody String prompt) {
        System.out.println("Received prompt: " + prompt);
        return chatbotService.getResponse(prompt)
                .map(response -> {
                    System.out.println("Response from GPT: " + response);
                    return ResponseEntity.ok().body(response);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}