package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ApiService apiService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CurriculumRepository curriculumRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatService(
            RestTemplate restTemplate,
            @Value("${api.key}") String apiKey,
            ApiService apiService,
            ChatRepository chatRepository,
            UserRepository userRepository,
            CurriculumRepository curriculumRepository) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiService = apiService;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.curriculumRepository = curriculumRepository;
    }

    // 사용자의 질문을 받아 gpt API의 응답을 반환
    public QuestionResponseDto getResponse(QuestionCallDto questionCallDto) {
        String userMessage = questionCallDto.getText();
        QuestionType questionType = questionCallDto.getType();
        Long curriculumId = questionCallDto.getCurriculumId();

        List<String> conversationHistory = new ArrayList<>();
        conversationHistory.add(userMessage);

        String response = continueConversation(questionType, conversationHistory, curriculumId);
        String content;
        if (response.startsWith("{")) {
            // JSON인 경우 텍스트로 응답만 추출
            content = apiService.extractContentFromResponse(response);
            content = content.replaceAll("^=>", ""); // 답변 앞에 '=>'가 있으면 삭제
        } else {
            // 텍스트 그대로 사용
            content = response;
        }

        // 채팅을 데이터베이스에 저장
        List<String> responseParts = splitResponse(content);

        // 각 단계적 답변 앞에 빈 칸이 존재하면 삭제
        List<String> trimmedResponseParts = new ArrayList<>();
        for (String part : responseParts) {
            trimmedResponseParts.add(part.trim());
        }

        Chats chat = saveChat(questionCallDto.getUserId(), questionCallDto.getCurriculumId(), userMessage, content, questionType, 1);

        return createResponseDtoFromChat(chat, trimmedResponseParts);
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
        Chats chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Not found with chatId: " + chatId));
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
            questionResponseDto.setAnswer("더 이상 답변이 존재하지 않습니다.");
        }

        return questionResponseDto;
    }

    // 단계적 답변 및 대화 규칙에 따라서 gpt API의 응답 템플릿을 정의
    private String continueConversation(QuestionType questionType, List<String> conversationHistory, Long curriculumId) {
        String rules = apiService.loadRules(curriculumId); // 규칙 로드
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

        return apiService.sendPostRequest(messages);
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