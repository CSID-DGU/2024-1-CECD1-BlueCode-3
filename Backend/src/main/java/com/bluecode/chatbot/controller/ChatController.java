package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bluecode.chatbot.dto.QuestionCallDto;
import com.bluecode.chatbot.dto.QuestionResponseDto;
import com.bluecode.chatbot.dto.NextLevelChatCallDto;
import com.bluecode.chatbot.dto.QuestionListResponseDto;
import com.bluecode.chatbot.dto.QuestionListResponseElementDto;

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
        return ResponseEntity.ok(response);
    }

    // 호출 시마다 다음 단계의 답변을 요청
    @PostMapping("/next")
    public ResponseEntity<QuestionResponseDto> getNextStep(@RequestBody NextLevelChatCallDto nextLevelChatCallDto) {
        QuestionResponseDto response = chatService.getNextStep(nextLevelChatCallDto);
        return ResponseEntity.ok(response);
    }

    // 루트 커리큘럼에 대한 채팅 기록을 로드
    @PostMapping("/historyByRoot")
    public ResponseEntity<QuestionListResponseDto> getChatsByParent(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsByRoot(questionCallDto.getUserId(), questionCallDto.getCurriculumId());
        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 챕터에 대한 채팅 기록 조회
    @PostMapping("/historyByChapter")
    public ResponseEntity<QuestionListResponseDto> getChatsByChapter(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsByChapter(questionCallDto.getUserId(), questionCallDto.getCurriculumId());
        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 서브 챕터에 대한 채팅 기록 조회
    @PostMapping("/historyBySubChapter")
    public ResponseEntity<QuestionListResponseDto> getChatsBySubChapter(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsBySubChapter(questionCallDto.getUserId(), questionCallDto.getCurriculumId());
        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 루트 커리큘럼과 질문 유형에 대한 채팅 기록 조회
    @PostMapping("/historyByRootAndQuestionType")
    public ResponseEntity<QuestionListResponseDto> getChatsByRootAndQuestionType(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsByRootAndQuestionType(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), questionCallDto.getType());
        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 챕터 커리큘럼과 질문 유형에 대한 채팅 기록 조회
    @PostMapping("/historyByChapterAndQuestionType")
    public ResponseEntity<QuestionListResponseDto> getChatsByChapterAndQuestionType(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsByChapterAndQuestionType(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), questionCallDto.getType());

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 서브 챕터 커리큘럼과 질문 유형에 대한 채팅 기록 조회
    @PostMapping("/historyBySubChapterAndQuestionType")
    public ResponseEntity<QuestionListResponseDto> getChatsBySubChapterAndQuestionType(@RequestBody QuestionCallDto questionCallDto) {
        List<Chats> chats = chatService.getChatsBySubChapterAndQuestionType(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), questionCallDto.getType());

        List<QuestionListResponseElementDto> formattedChats = formatChats(chats);
        QuestionListResponseDto responseDto = new QuestionListResponseDto();
        responseDto.setList(formattedChats);
        return ResponseEntity.ok(responseDto);
    }

    // 채팅 기록을 DTO 형태로 변환
    private List<QuestionListResponseElementDto> formatChats(List<Chats> chats) {
        return chats.stream().map(chat -> {
            QuestionListResponseElementDto dto = new QuestionListResponseElementDto();
            dto.setCurriculumText(chat.getCurriculum().getCurriculumName());
            dto.setQuestion(chat.getQuestion());
            // 진행한 레벨까지만 단계적 답변 챗 필터링(단일 답변은 1개 요소인 리스트로 반영)
            dto.setAnswer(chatService.splitResponse(chat.getAnswer()).stream().limit(chat.getLevel()).toList());
            dto.setLevel(chat.getLevel());
            dto.setQuestionType(chat.getQuestionType());
            dto.setChatDate(chat.getChatDate());
            return dto;
        }).toList();
    }
}