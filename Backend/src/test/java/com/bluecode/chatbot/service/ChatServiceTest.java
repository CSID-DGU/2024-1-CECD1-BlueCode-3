package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.QuestionType;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.repository.ChatRepository;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChatServiceTest {

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private ChatService chatService;

    @Value("${api.key}")
    private String apiKey;

    private Users user;
    private Curriculums curriculum;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setUserId(2L);
        user.setUsername("testUser");

        curriculum = new Curriculums();
        curriculum.setCurriculumId(1L);
        curriculum.setCurriculumName("testCurriculum");

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(curriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
    }

    @Test
    public void testGetResponse() {
        String userMessage = "테스트 메세지입니다.";

        // 실제 API 호출
        String response = chatService.getResponse(2L, 1L, userMessage, 1);

        // 응답 확인
        assertThat(response).isNotNull();
        System.out.println("API Response: " + response);

        // Chats 엔티티 저장 확인
        ArgumentCaptor<Chats> chatCaptor = ArgumentCaptor.forClass(Chats.class);
        verify(chatRepository).save(chatCaptor.capture());
        Chats savedChat = chatCaptor.getValue();

        assertThat(savedChat.getUser().getUserId()).isEqualTo(2L);
        assertThat(savedChat.getCurriculum().getCurriculumId()).isEqualTo(1L);
        assertThat(savedChat.getQuestion()).isEqualTo(userMessage);
        assertThat(savedChat.getAnswer()).isNotNull();
    }

    @Test
    public void testSetQuestionType() {
        String question = "print(hello)에서 왜 오류가 나는거야?";

        // 실제 API 호출
        QuestionType questionType = chatService.setQuestionType(question);

        // 응답 확인
        assertThat(questionType).isEqualTo(QuestionType.ERRORS);
        System.out.println("질문 유형: " + questionType);
    }

    @Test
    public void testGetStepByStepResponse() {
        String gptResponse = "1단계\n\n2단계\n\n3단계";
        String step1Response = chatService.getStepByStepResponse(gptResponse, 1);
        String step2Response = chatService.getStepByStepResponse(gptResponse, 2);
        String step3Response = chatService.getStepByStepResponse(gptResponse, 3);
        String step4Response = chatService.getStepByStepResponse(gptResponse, 4);

        assertThat(step1Response).isEqualTo("1단계");
        assertThat(step2Response).isEqualTo("2단계");
        assertThat(step3Response).isEqualTo("3단계");
        assertThat(step4Response).isEqualTo("No More Step!");
    }
}