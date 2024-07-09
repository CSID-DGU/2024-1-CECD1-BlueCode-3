package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.QuestionType;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.repository.ChatRepository;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CurriculumRepository curriculumRepository;
    private List<Map<String, String>> conversationHistory = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    public ChatbotService(
            RestTemplate restTemplate,
            @Value("${api.key}") String apiKey,
            ChatRepository chatRepository,
            UserRepository userRepository,
            CurriculumRepository curriculumRepository) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.curriculumRepository = curriculumRepository;
    }

    // 사용자의 메시지에 대한 GPT의 응답을 가져오고 이를 단계별로 처리하는 메서드
    public String getResponse(Long userId, Long curriculumId, String userMessage, int questionLevel) {
        conversationHistory.add(Map.of("role", "user", "content", userMessage)); // 사용자의 메시지를 기록

        // 질문 유형을 GPT로 판단
        QuestionType questionType = setQuestionType(userMessage);

        // GPT 대화 생성
        String response = continueConversation(questionType, userMessage);
        conversationHistory.add(Map.of("role", "assistant", "content", response)); // 챗봇의 응답을 기록

        // Chats 엔티티에 저장
        saveChat(userId, curriculumId, userMessage, response, questionType, questionLevel);

        // 단계별 답변 리턴
        if (questionType == QuestionType.CODE || questionType == QuestionType.ERRORS) {
            return getStepByStepResponse(response, questionLevel);
        } else {
            return response;
        }
    }

    // 대화를 리스트에 저장하는 메서드
    private String continueConversation(QuestionType questionType, String userMessage) {
        String rules = loadRules(); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of("role", "system", "content", rules));

        for (Map<String, String> pastMessage : conversationHistory) {
            messages.add(Map.of("role", pastMessage.get("role"), "content", pastMessage.get("content")));
        }

        messages.add(Map.of("role", "user", "content", userMessage));

        if (questionType == QuestionType.CODE || questionType == QuestionType.ERRORS) {
            messages.add(Map.of("role", "system", "content", "답변은 다음 단계로 제공할 것:\n1단계: 코드 라인별 기능 또는 발생 에러 개념 정의\n2단계: 코드로 구현할 내용 또는 에러 발생 지점\n3단계: 완전한 해설 또는 에러 해결 방안 제시\n추가 단계: 비슷한 예제 코드 또는 에러 코드 제시"));
        }

        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", messages,
                "max_tokens", 1000 // 필요에 따라 토큰 수 조정
        );

        return sendPostRequest(body);
    }

    // GPT API에 응답을 요청하는 메서드
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

    // GPT API의 json 형식 응답에서 답변 본문만 추출하는 메서드
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return "";
        }
    }

    // 백엔드 서버 로그에 GPT API 응답을 출력하는 메서드
    private void logContent(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            String content = root.path("choices").path(0).path("message").path("content").asText();
            logger.info("Response from GPT: {}", content);
        } catch (IOException e) {
            logger.error("응답 JSON 파싱 에러", e);
        }
    }

    // rules.txt에 있는 답변규칙을 로드하여 GPT API에 각인시키는 메서드
    private String loadRules() {
        try {
            ClassPathResource resource = new ClassPathResource("rules.txt");
            return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rules", e);
        }
    }

    // 채팅을 Chat DB에 저장하는 메서드
    private void saveChat(Long userId, Long curriculumId, String question, String answer, QuestionType questionType, int level) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Curriculums curriculum = curriculumRepository.findById(curriculumId).orElseThrow(() -> new IllegalArgumentException("Invalid curriculum ID: " + curriculumId));

        Chats chat = new Chats();
        chat.setUser(user);
        chat.setCurriculum(curriculum);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chat.setQuestionType(questionType);
        chat.setLevel(level);

        chatRepository.save(chat);
    }

    // GPT API를 사용하여 질문의 유형을 판단하는 메서드
    public QuestionType setQuestionType(String question) {
        String rules = "질문을 보고 다음 유형 중 하나로 판단해줘 : (코드) (오류) (개념)";

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", rules));
        messages.add(Map.of("role", "user", "content", question));

        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", messages
        );

        String response = sendPostRequest(body);
        return parseQuestionType(response);
    }

    // GPT 응답에서 질문 유형을 분류하는 메서드
    private QuestionType parseQuestionType(String response) {
        if (response.contains("코드")) {
            return QuestionType.CODE;
        } else if (response.contains("오류")) {
            return QuestionType.ERRORS;
        } else {
            return QuestionType.DEF;
        }
    }

    // GPT 응답을 단계별로 슬라이싱하는 메서드
    public String getStepByStepResponse(String response, int step) {
        String[] responseParts = response.split("\\n\\n");
        if (step - 1 < responseParts.length) {
            return responseParts[step - 1];
        } else {
            return "No More Step!";
        }
    }
}