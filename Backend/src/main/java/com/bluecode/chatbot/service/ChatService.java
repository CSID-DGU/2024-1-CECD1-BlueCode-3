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
        String content;
        if (response.startsWith("{")) {
            // JSON인 경우 텍스트로 응답만 추출
            content = extractContentFromResponse(response);
        } else {
            // 텍스트 그대로 사용
            content = response;
        }

        // 채팅을 데이터베이스에 저장
        List<String> responseParts = splitResponse(content);
        Chats chat = saveChat(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), userMessage, content, questionType, 1);

        return createResponseDtoFromChat(chat, responseParts);
    }

    // api의 답변을 dto 형태로 데이터베이스에 저장
    private QuestionResponseDto createResponseDtoFromChat(Chats chat, List<String> responseParts) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionType(chat.getQuestionType());
        questionResponseDto.setChatId(chat.getChatId());
        questionResponseDto.setAnswerList(responseParts);  // 단계적 답변들을 모두 저장
        if (!responseParts.isEmpty()) {
            questionResponseDto.setAnswer(responseParts.get(0));  // 첫 번째 답변을 기본 응답으로 설정
        }
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

        // 다음 단계로 넘어갈 수 있는지 확인
        int currentLevel = chat.getLevel();
        if (currentLevel < responseParts.size()) {
            chat.setLevel(currentLevel + 1);  // 다음 단계로 업데이트
            chatRepository.save(chat);  // 변경사항 저장
            questionResponseDto.setAnswer(responseParts.get(currentLevel));  // 현재 단계의 답변 반환
        } else {
            questionResponseDto.setAnswer("No more step.");
        }

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
            messages.add(Map.of("role", "system", "content", "답변은 '=>' 표시 다음에 나오는 형태를 참고해 총 4개의 단계로 답변해야하며" +
                    "1단계를 제외한 다른 3개의 단계 앞에는 구분을 위하여 '$'을 반드시 넣어주고, 앞에 1단계, 2단계 이런 표현도 모두 생략하여 내용만 출력해야하며 " +
                    "괄호 안 내용은 그대로 출력하라는게 아니라 응답 시 참고하여 해당 위치에 적절하게 삽입하고 괄호는 당연히 생략해야하며 CODE인지 ERROR인지 응답에는 적지 않음\n" +
                    "다음과 같은 형태 =>" +
                    "1단계: (CODE인 경우: 작성된 코드의 기능 및 작동 목적을 파악 후 응답, ERRORS인 경우: 코드에서 발생하는 에러타입과 에러에 대한 설명)" +
                    "2단계: (CODE인 경우: 사용자가 제공한 코드가 목적에 맞는지 여부를 응답, ERRORS인 경우: 바로 해답을 알려주지 않고 문제 발생 위치 상기)" +
                    "3단계: (CODE인 경우: 목적에 맞게 코드를 보완하거나 코드량을 줄이는 방법 안내, ERRORS인 경우: 문제를 완벽하게 해결한 올바른 코드로 수정하여 응답)" +
                    "추가 단계: (CODE인 경우: 같은 목적 및 작동 방식의 다른 예제 코드 안내, ERRORS인 경우: 비슷한 에러가 발생할만한 다른 예제 코드 안내)"));
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
            JsonNode choice = root.path("choices").path(0);
            return choice.path("message").path("content").asText()
                    .replaceAll("(?m)^\\s*=>\\s*", ""); // 단계적 답변 첫번째 응답 앞에 '=>' 제거
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
        return List.of(response.split("(?m)^\\$")); // '$' 기호로 시작하는 행을 기준으로 분할
    }

    // 전체 채팅 기록을 로드
    public List<Chats> getChatHistory(Long userId, Long parentId) {
        return chatRepository.findAllByUserIdAndParentIdOrderByChapterNumAndChatDate(userId, parentId);
    }

    // 특정 커리큘럼에 대한 채팅 기록 조회
    public List<Chats> getChatsByCurriculum(Long userId, Long curriculumId) {
        return chatRepository.findAllByUserIdAndChapterIdOrderByChatDate(userId, curriculumId);
    }

    // 특정 질문 유형에 대한 채팅 기록 조회
    public List<Chats> getChatsByQuestionType(Long userId, QuestionType questionType) {
        return chatRepository.findAllByUserIdAndQuestionTypeOrderByChatDate(userId, questionType);
    }

    // 특정 커리큘럼과 질문 유형에 대한 채팅 기록 조회
    public List<Chats> getChatsByCurriculumAndQuestionType(Long userId, Long curriculumId, QuestionType questionType) {
        return chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(userId, curriculumId, questionType);
    }
}