package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bluecode.chatbot.dto.QuestionCallDto;
import com.bluecode.chatbot.dto.QuestionResponseDto;
import com.bluecode.chatbot.dto.NextLevelChatCallDto;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 사용자의 메시지에 대한 응답을 처리
    @PostMapping("/message")
    public ResponseEntity<QuestionResponseDto> getChatResponse(@RequestBody QuestionCallDto questionCallDto) {
        QuestionResponseDto response = chatService.getResponse(questionCallDto);
        if (response != null && !response.getAnswerList().isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 다음 단계의 답변을 요청
    @GetMapping("/next")
    public ResponseEntity<QuestionResponseDto> getNextStep(@RequestParam Long chatId) {
        NextLevelChatCallDto nextLevelChatCallDto = new NextLevelChatCallDto();
        nextLevelChatCallDto.setChatId(chatId);

        QuestionResponseDto response = chatService.getNextStep(nextLevelChatCallDto);
        if (response != null && !response.getAnswerList().isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 지난 채팅 기록을 로드
    @GetMapping("/history")
    public ResponseEntity<List<Chats>> getChatHistory(@RequestParam Long userId, @RequestParam Long curriculumId) {
        List<Chats> chatHistory = chatService.getChatHistory(userId, curriculumId);
        if (chatHistory != null && !chatHistory.isEmpty()) {
            return ResponseEntity.ok().body(chatHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}