//package com.bluecode.chatbot.controller;
//
//import com.bluecode.chatbot.service.ChatService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class ChatController {
//    private final ChatService chatService;
//
//    @Autowired
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    @GetMapping("/chat/{prompt}")
//    public ResponseEntity<String> getChatResponse(@PathVariable String prompt) {
//        String response = chatService.getResponse(prompt);
//        if (response != null && !response.isEmpty()) {
//            return ResponseEntity.ok().body(response);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/chat")
//    public ResponseEntity<String> postChatResponse(@RequestBody String prompt) {
//        System.out.println("Received prompt: " + prompt);
//        String response = chatService.getResponse(prompt);
//        if (response != null && !response.isEmpty()) {
//            System.out.println("Response from GPT: " + response);
//            return ResponseEntity.ok().body(response);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
