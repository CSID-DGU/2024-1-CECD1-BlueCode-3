package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ChatService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CurriculumRepository curriculumRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    public ChatService(
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

    // 사용자의 질문을 받아 gpt API의 응답을 반환
    public QuestionResponseDto getResponse(QuestionCallDto questionCallDto) {
        String userMessage = questionCallDto.getText();
        QuestionType questionType = questionCallDto.getType();

        List<String> conversationHistory = new ArrayList<>();
        conversationHistory.add(userMessage);
        
        String response = continueConversation(questionType, conversationHistory);
        String content = extractContentFromResponse(response); // json 형식의 응답에서 content만 텍스트로 추출

        // 질문 유형이 코드와 에러이면 단계적 답변으로 분할
        List<String> responseParts;
        if (questionType == QuestionType.CODE || questionType == QuestionType.ERRORS) {
            responseParts = splitResponse(content);
        } else {
            responseParts = List.of(content);
        }

        // 채팅을 데이터베이스에 저장
        Chats chat = saveChat(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), userMessage, content, questionType, 1);

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionType(questionType);
        questionResponseDto.setAnswer(responseParts.get(0));
        questionResponseDto.setChatId(chat.getChatId());
        questionResponseDto.setAnswerList(responseParts);
        return questionResponseDto;
    }

    // 단계적 답변에서 다음 단계의 응답을 로드
    public QuestionResponseDto getNextStep(NextLevelChatCallDto nextLevelChatCallDto) {
        Long chatId = nextLevelChatCallDto.getChatId();
        Chats chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found with ID: " + chatId));
        List<String> responseParts = splitResponse(chat.getAnswer());

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setAnswerList(responseParts);
        questionResponseDto.setChatId(chatId);
        questionResponseDto.setQuestionType(chat.getQuestionType());
        questionResponseDto.setAnswer(responseParts.size() > 1 ? responseParts.get(1) : "No more step.");

        return questionResponseDto;
    }

    // 단계적 답변 및 대화 규칙에 따라서 gpt API의 응답 템플릿을 정의
    private String continueConversation(QuestionType questionType, List<String> conversationHistory) {
        String rules = loadRules(); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of("role", "system", "content", rules));

        for (String pastMessage : conversationHistory) {
            messages.add(Map.of("role", "user", "content", pastMessage));
        }

        if (questionType == QuestionType.CODE || questionType == QuestionType.ERRORS) {
            messages.add(Map.of("role", "system", "content", "답변은 다음과 같은 형태로만 제공할 것" +
                    "(괄호 안 내용은 참고하여 해당 위치에 적절한 응답 생성):" +
                    "\n\n1단계: (코드 라인별 기능 또는 발생 에러 개념 정의)" +
                    "\n\n2단계: (코드로 구현할 내용 또는 에러 발생 지점)" +
                    "\n\n3단계: (완전한 해설 또는 에러 해결 방안 제시)" +
                    "\n\n추가 단계: (비슷한 예제 코드 또는 에러 코드 제시)"));
        }

        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", messages,
                "max_tokens", 1000 // 필요에 따라 토큰 수 조정
        );

        return sendPostRequest(body);
    }

    // gpt API에 응답을 요청
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

    // json에서 content만 추출하여 텍스트로 저장
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return "";
        }
    }

    // 백엔드 서버에 로그로 표시
    private void logContent(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            String content = root.path("choices").path(0).path("message").path("content").asText();
            logger.info("Response from GPT: {}", content);
        } catch (IOException e) {
            logger.error("응답 JSON 파싱 에러", e);
        }
    }
    
    // rules.txt 파일에서 응답 규칙을 로드
    private String loadRules() {
        try {
            ClassPathResource resource = new ClassPathResource("rules.txt"); // Resource 디렉토리에 저장된 응답 규칙
            return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rules", e);
        }
    }

    // 채팅을 데이터베이스에 저장
    private Chats saveChat(Long userId, Long curriculumId, String question, String answer, QuestionType questionType, int level) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Curriculums curriculum = curriculumRepository.findById(curriculumId).orElseThrow(() -> new IllegalArgumentException("Invalid curriculum ID: " + curriculumId));

        Chats chat = new Chats();
        chat.setUser(user);
        chat.setCurriculum(curriculum);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chat.setQuestionType(questionType);
        chat.setLevel(level);

        return chatRepository.save(chat);
    }

    // 단계적 답변을 위해 응답을 분할
    private List<String> splitResponse(String response) {
        return List.of(response.split("\\n\\n"));
    }

    // 전체 채팅 기록을 로드
    public List<Chats> getChatHistory(Long userId, Long curriculumId) {
        return chatRepository.findAllByUserIdAndChapterIdOrderByChatDate(userId, curriculumId);
    }
}