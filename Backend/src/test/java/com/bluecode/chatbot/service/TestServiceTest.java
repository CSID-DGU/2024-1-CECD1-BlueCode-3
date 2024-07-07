package com.bluecode.chatbot.service;

import com.bluecode.chatbot.dto.TestAnswerCallDto;
import com.bluecode.chatbot.dto.TestAnswerResponseDto;
import com.bluecode.chatbot.dto.TestResponseDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        int chapterNum = 1;

        TestResponseDto testResponse = testService.forInitialTest(userId, chapterNum);

        assertNotNull(testResponse);
        testResponse.getTests().forEach(test -> logger.info("Initial test: {}", test));
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