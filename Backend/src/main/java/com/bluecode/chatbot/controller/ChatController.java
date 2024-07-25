package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.domain.QuestionType;
import com.bluecode.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bluecode.chatbot.dto.QuestionCallDto;
import com.bluecode.chatbot.dto.QuestionResponseDto;
import com.bluecode.chatbot.dto.NextLevelChatCallDto;
import com.bluecode.chatbot.dto.QuestionListResponseElementDto;

import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 사용자의 메시지에 대한 응답을 처리(단계적 답변은 1단계 응답만 반환)
    @PostMapping("/response")
    public ResponseEntity<QuestionResponseDto> getChatResponse(@RequestBody QuestionCallDto questionCallDto) {
        QuestionResponseDto response = chatService.getResponse(questionCallDto);
        return response != null && !response.getAnswerList().isEmpty()
                ? ResponseEntity.ok(response)
                : ResponseEntity.notFound().build();
    }

    // 호출 시마다 다음 단계의 답변을 요청
    @GetMapping("/next")
    public ResponseEntity<QuestionResponseDto> getNextStep(@RequestParam Long chatId) {
        NextLevelChatCallDto nextLevelChatCallDto = new NextLevelChatCallDto();
        nextLevelChatCallDto.setChatId(chatId);

        QuestionResponseDto response = chatService.getNextStep(nextLevelChatCallDto);
        return response != null && !response.getAnswerList().isEmpty()
                ? ResponseEntity.ok(response)
                : ResponseEntity.notFound().build();
    }

    // 루트 커리큘럼에 대한 채팅 기록을 로드
    @GetMapping("/historyByParent")
    public ResponseEntity<List<QuestionListResponseElementDto>> getChatsByParent(@RequestParam Long userId, @RequestParam Long parentId) {
        List<Chats> chats = chatService.getChatHistory(userId, parentId);
        if (chats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        return ResponseEntity.ok(formattedChats); // json 형태로 변환
    }

    // 특정 커리큘럼에 대한 채팅 기록 조회
    @GetMapping("/historyByCurriculum")
    public ResponseEntity<List<QuestionListResponseElementDto>> getChatsByCurriculum(@RequestParam Long userId, @RequestParam Long curriculumId) {
        List<Chats> chats = chatService.getChatsByCurriculum(userId, curriculumId);
        if (chats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        return ResponseEntity.ok(formattedChats); // json 형태로 변환
    }

    // 특정 질문 유형에 대한 채팅 기록 조회
    @GetMapping("/historyByQuestionType")
    public ResponseEntity<List<QuestionListResponseElementDto>> getChatsByQuestionType(@RequestParam Long userId, @RequestParam QuestionType questionType) {
        List<Chats> chats = chatService.getChatsByQuestionType(userId, questionType);
        if (chats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        return ResponseEntity.ok(formattedChats); // json 형태로 변환
    }

    // 특정 커리큘럼과 질문 유형에 대한 채팅 기록 조회
    @GetMapping("/historyByCurriculumAndQuestionType")
    public ResponseEntity<List<QuestionListResponseElementDto>> getChatsByCurriculumAndQuestionType(@RequestParam Long userId, @RequestParam Long curriculumId, @RequestParam QuestionType questionType) {
        List<Chats> chats = chatService.getChatsByCurriculumAndQuestionType(userId, curriculumId, questionType);
        if (chats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        return ResponseEntity.ok(formattedChats); // json 형태로 변환
    }

    // 채팅 기록을 DTO 형태로 변환
    private List<QuestionListResponseElementDto> formatChats(List<Chats> chats) {
        return chats.stream().map(chat -> {
            QuestionListResponseElementDto dto = new QuestionListResponseElementDto();
            dto.setCurriculumText(chat.getCurriculum().getCurriculumName());
            dto.setQuestion(chat.getQuestion());
            dto.setAnswer(chat.getAnswer());
            dto.setQuestionType(chat.getQuestionType());
            dto.setChatDate(chat.getChatDate());
            return dto;
        }).collect(Collectors.toList());
    }
}