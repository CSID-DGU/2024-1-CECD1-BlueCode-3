package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CurriculumServiceTest {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CurriculumService curriculumService;

    private static final Logger logger = LoggerFactory.getLogger(CurriculumServiceTest.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("테스트 설정 완료");
    }

    @Test
    public void testGetCurriculumChapters() {
        logger.info("testGetCurriculumChapters 테스트 시작");

        CurriculumChapCallDto dto = new CurriculumChapCallDto();
        dto.setCurriculumId(1L);  // 초기화된 데이터 사용

        Curriculums rootCurriculum = curriculumRepository.findById(1L).orElse(null);
        assertNotNull(rootCurriculum);

        var result = curriculumService.getCurriculumChapters(dto);

        logger.info("테스트 결과: {}", result.getList());
    }
}