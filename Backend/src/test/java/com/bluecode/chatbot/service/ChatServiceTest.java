package com.bluecode.chatbot.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ChatServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetResponse() {
        QuestionCallDto questionCallDto = new QuestionCallDto();
        questionCallDto.setUserId(1L);
        questionCallDto.setCurriculumId(1L);
        questionCallDto.setText("print(Hello World) 코드에서 에러가 나는 이유가 뭘까?"); // 사용자 모의 질문
        questionCallDto.setType(QuestionType.ERRORS);

        // Mock 데이터 설정(모의 답변)
        String jsonMockResponse = "{\"choices\": [{\"message\": {\"content\": \"1단계: print 함수에서 괄호는 사용되어야 합니다." +
                "\\n\\n2단계: Hello World는 문자열이므로 따옴표 안에 있어야 합니다." +
                "\\n\\n3단계: Python에서는 문자열을 인쇄할 때 따옴표를 사용하여 정의해야 합니다.\"}}]}";
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(jsonMockResponse));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new Users()));
        when(curriculumRepository.findById(anyLong())).thenReturn(Optional.of(new Curriculums()));

        // 메서드 호출
        QuestionResponseDto result = chatService.getResponse(questionCallDto);

        // 결과 검증
        assertNotNull(result);
        assertEquals(3, result.getAnswerList().size());
        assertEquals("1단계: print 함수에서 괄호는 사용되어야 합니다.", result.getAnswerList().get(0));

        // Mock 객체의 사용 확인
        verify(chatRepository, times(1)).save(any(Chats.class));
    }

    @Test
    public void testGetNextStep() {
        NextLevelChatCallDto nextLevelChatCallDto = new NextLevelChatCallDto();
        nextLevelChatCallDto.setChatId(1L);

        // Chat 히스토리 설정
        Chats chat = new Chats();
        chat.setAnswer("1단계: print 함수에서 괄호는 사용되어야 합니다." +
                "\n\n2단계: Hello World는 문자열이므로 따옴표 안에 있어야 합니다." +
                "\n\n3단계: Python에서는 문자열을 인쇄할 때 따옴표를 사용하여 정의해야 합니다.");
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(chat));

        // 메서드 호출
        QuestionResponseDto result = chatService.getNextStep(nextLevelChatCallDto);

        // 결과 검증
        assertEquals("2단계: Hello World는 문자열이므로 따옴표 안에 있어야 합니다.", result.getAnswer());
    }
}