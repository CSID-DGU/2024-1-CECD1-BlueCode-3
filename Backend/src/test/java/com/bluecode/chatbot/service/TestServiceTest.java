package com.bluecode.chatbot.service;

import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.dto.TestAnswerCallDto;
import com.bluecode.chatbot.dto.TestAnswerResponseDto;
import com.bluecode.chatbot.dto.TestResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TestService testService;

    private static final Logger logger = LoggerFactory.getLogger(TestServiceTest.class);

    @Test
    public void testForInitialTest() {
        logger.info("testForInitialTest 테스트 시작");

        Long userId = 2L;
        Long curriculumId = 4L;

        DataCallDto dto = new DataCallDto();
        dto.setUserId(userId);
        dto.setCurriculumId(curriculumId);

        try {
            TestResponseDto testResponse = testService.forInitialTest(dto);
            Assertions.assertThat(testResponse.getTests().size()).isEqualTo(4);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testSubmitAnswer() {
        logger.info("testSubmitAnswer 테스트 시작");

        TestAnswerCallDto answerDto = new TestAnswerCallDto();
        answerDto.setUserId(2L);
        answerDto.setQuizId(1L);
        answerDto.setAnswer("정답");

        TestAnswerResponseDto responseDto = testService.submitAnswer(answerDto);

        assertNotNull(responseDto);
        logger.info("Submit answer response: {}", responseDto);
    }
}