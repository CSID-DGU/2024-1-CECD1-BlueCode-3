package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ApiService apiService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CurriculumRepository curriculumRepository;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 사용자의 질문을 받아 gpt API의 응답을 반환
    public QuestionResponseDto getResponse(QuestionCallDto questionCallDto) {
        String userMessage = questionCallDto.getText();
        QuestionType questionType = questionCallDto.getType();
        Long curriculumId = questionCallDto.getCurriculumId();

        Optional<Users> userOptional = userRepository.findByUserId(questionCallDto.getUserId());
        Optional<Curriculums> subChapterOptional = curriculumRepository.findById(questionCallDto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id입니다.");
        }

        if (subChapterOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 서브 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums subChapter = subChapterOptional.get();

        if (!subChapter.isLeafNode()) {
            log.error("서브챕터 커리큘럼이 아닙니다. subChapter={}", subChapter);
            throw new IllegalArgumentException("서브챕터 커리큘럼이 아닙니다.");
        }

        List<String> conversationHistory = new ArrayList<>();
        List<Chats> history = chatRepository.findAllByUserIdAndSubChapterIdOrderByChatDate(user.getUserId(), subChapter.getCurriculumId());

        if (!history.isEmpty()) {
            for (Chats chats : history) {
                conversationHistory.add(chats.getQuestion());
            }
        }

        conversationHistory.add(userMessage);

        log.info("convHist: {}", conversationHistory);

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

    // api의 답변을 dto 형태로 재구성
    private QuestionResponseDto createResponseDtoFromChat(Chats chat, List<String> responseParts) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionType(chat.getQuestionType());
        questionResponseDto.setChatId(chat.getChatId());
        questionResponseDto.setAnswerList(responseParts);  // 단계적 답변들을 모두 저장
        if (!responseParts.isEmpty()) {
            questionResponseDto.setAnswer(responseParts.get(0));  // 첫 번째 답변을 기본 응답으로 설정
        } else {
            throw new IllegalStateException("답변 생성 실패");
        }
        return questionResponseDto;
    }

    // 단계적 답변에서 다음 단계의 응답을 로드
    public QuestionResponseDto getNextStep(NextLevelChatCallDto nextLevelChatCallDto) {
        Long chatId = nextLevelChatCallDto.getChatId();
        Optional<Chats> chatsOptional = chatRepository.findById(chatId);

        if (chatsOptional.isEmpty()) {
            log.error("유효하지 않은 chatId 입니다. chatId: {}", chatId);
            throw new IllegalArgumentException("유효하지 않은 chatId 입니다.");
        }

        Chats chat = chatsOptional.get();

        List<String> responseParts = splitResponse(chat.getAnswer());

        // 각 단계적 답변 앞에 빈 칸이 존재하면 삭제
        List<String> trimmedResponseParts = new ArrayList<>();
        for (String part : responseParts) {
            trimmedResponseParts.add(part.trim());
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setAnswerList(trimmedResponseParts);
        questionResponseDto.setChatId(chatId);
        questionResponseDto.setQuestionType(chat.getQuestionType());

        // 다음 단계로 넘어갈 수 있는지 확인
        int currentLevel = chat.getLevel();
        if (currentLevel < trimmedResponseParts.size()) {
            chat.setLevel(currentLevel + 1);  // 다음 단계로 업데이트
            chatRepository.save(chat);  // 변경사항 저장
            questionResponseDto.setAnswer(trimmedResponseParts.get(currentLevel));  // 현재 단계의 답변 반환

            // 미션 처리 로직
            eventPublisher.publishEvent(new UserActionEvent(this, chat.getUser(), ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(chat.getQuestionType(), chat.getLevel())));

        } else {
            questionResponseDto.setAnswer("더 이상 답변이 존재하지 않습니다.");
        }

        return questionResponseDto;
    }

    // 단계적 답변 및 대화 규칙에 따라서 gpt API의 응답 템플릿을 정의
    private String continueConversation(QuestionType questionType, List<String> conversationHistory, Long curriculumId) {
        List<Map<String, String>> messages = new ArrayList<>();

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

        return apiService.sendPostRequest(messages, curriculumId);
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

        // 미션 처리 로직
        if (questionType == QuestionType.DEF) {
            eventPublisher.publishEvent(new UserActionEvent(this,  user, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION));
        } else if (questionType == QuestionType.CODE) {
            eventPublisher.publishEvent(new UserActionEvent(this,  user, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION));
        } else if (questionType == QuestionType.ERRORS) {
            eventPublisher.publishEvent(new UserActionEvent(this,  user, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION));
        }
        eventPublisher.publishEvent(new UserActionEvent(this,  user, ServiceType.CHAT, MissionConst.CHAT_QUESTION));

        return chatRepository.save(chat);
    }

    // 단계적 답변을 위해 응답을 분할
    public List<String> splitResponse(String response) {
//        return List.of(response.split("(?m)^\\$"));
        return List.of(response.split("\\$")); // '$' 기호를 기준으로 분할
    }

    // 특정 서브챕터에 대한 채팅 기록 조회
    public List<Chats> getChatsBySubChapter(Long userId, Long subChapterId) {
        List<Chats> result = chatRepository.findAllByUserIdAndSubChapterIdOrderByChatDate(userId, subChapterId);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsBySubChapter 결과 없음");
        }

        return result;
    }

    // 특정 챕터에 대한 채팅 기록 조회
    public List<Chats> getChatsByChapter(Long userId, Long chapterId) {
        List<Chats> result = chatRepository.findAllByUserIdAndParentIdOrderBySubChapterNumAndChatDate(userId, chapterId);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsByChapter 결과 없음");
        }

        return result;
    }

    // 특정 루트에 대한 채팅 기록 조회
    public List<Chats> getChatsByRoot(Long userId, Long rootId) {
        List<Chats> result = chatRepository.findAllByUserIdAndRootIdOrderByChapterNumAndSubChapterNumAndChatDate(userId, rootId);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsByRoot 결과 없음");
        }

        return result;
    }


    // 특정 서브 챕터와 질문 유형에 대한 채팅 기록 조회
    public List<Chats> getChatsBySubChapterAndQuestionType(Long userId, Long subChapterId, QuestionType questionType) {

        List<Chats> result = chatRepository.findAllByUserIdAndSubChapterIdAndQuestionTypeOrderByChatDate(userId, subChapterId, questionType);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsBySubChapterAndQuestionType 결과 없음");
        }

        return result;
    }

    // 특정 챕터와 질문 유형에 대한 채팅 기록 조회
    public List<Chats> getChatsByChapterAndQuestionType(Long userId, Long curriculumId, QuestionType questionType) {
        List<Chats> result = chatRepository.findAllByUserIdAndParentIdAAndQuestionTypeOrderBySubChapterNumAndChatDate(userId, curriculumId, questionType);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsByChapterAndQuestionType 결과 없음");
        }

        return result;
    }

    // 특정 루트와 질문 유형에 대한 채팅 기록 조회
    public List<Chats> getChatsByRootAndQuestionType(Long userId, Long rootId, QuestionType questionType) {
        List<Chats> result = chatRepository.findAllByUserIdAndRootIdAAndQuestionTypeOrderByChapterNumAndSubChapterNumAndChatDate(userId, rootId, questionType);

        if (result.isEmpty()) {
            throw new IllegalStateException("getChatsByRootAndQuestionType 결과 없음");
        }

        return result;
    }
}