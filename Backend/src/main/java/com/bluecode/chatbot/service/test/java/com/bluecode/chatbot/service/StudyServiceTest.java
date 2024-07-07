package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.dto.StudyTextDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StudyService studyService;

    private static final Logger logger = LoggerFactory.getLogger(StudyServiceTest.class);

    @BeforeEach
    public void setUp() {
        logger.info("테스트 설정 완료");

        // 테스트 데이터가 존재하지 않는 경우 추가
        if (!curriculumRepository.existsById(1L)) {
            Curriculums rootCurriculum = new Curriculums();
            rootCurriculum.setCurriculumId(1L);
            rootCurriculum.setChapterNum(0);
            rootCurriculum.setCurriculumName("루트");
            curriculumRepository.save(rootCurriculum);

            Curriculums childCurriculum = new Curriculums();
            childCurriculum.setCurriculumId(2L);
            childCurriculum.setParent(curriculumRepository.findById(1L).orElse(null));
            childCurriculum.setChapterNum(1);
            childCurriculum.setCurriculumName("하위 챕터");
            childCurriculum.setKeywordEasy("입문자 키워드");
            childCurriculum.setKeywordNormal("초급자 키워드");
            childCurriculum.setKeywordHard("중급자 키워드");
            curriculumRepository.save(childCurriculum);
        }
    }

    @Test
    public void testGetCurriculumText() {
        logger.info("testGetCurriculumText 테스트 시작");

        DataCallDto dto = new DataCallDto();
        dto.setUserId(1L);
        dto.setCurriculumId(2L);
        LevelType levelType = LevelType.EASY;

        Curriculums curriculum = curriculumRepository.findById(2L).orElse(null);
        assertNotNull(curriculum);

        String keyword = curriculumService.getKeywordForLevel(curriculum, levelType);
        assertNotNull(keyword);

        StudyTextDto result = studyService.getCurriculumText(dto, levelType);

        logger.info("테스트 결과: {}", result.getText());
    }
}