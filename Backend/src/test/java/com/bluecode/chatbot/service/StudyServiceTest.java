package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class StudyServiceTest {

    @Autowired
    private StudyService studyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    void beforeEach() {
        curriculumRepository.deleteAll();
        studyRepository.deleteAll();
        chatRepository.deleteAll();
        testRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("createCurriculumStudyData 메서드 정상 테스트")
    void createCurriculumStudyData() {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        List<Curriculums> chapters = new ArrayList<>();
        chapters.add(Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1));
        chapters.add(Curriculums.createCurriculum(root, "챕터2", "키워드2: easy", "키워드2: normal", "키워드2: hard", true, 2, 1));
        curriculumRepository.saveAll(chapters);

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(root.getCurriculumId());

        CurriculumChapResponseDto result = studyService.createCurriculumStudyData(dto);

        //then
        for (int i = 0; i < result.getList().size(); i++) {
            Assertions.assertThat(result.getList().get(i).getCurriculumId()).isEqualTo(chapters.get(i).getCurriculumId());
            Assertions.assertThat(result.getList().get(i).getText()).isEqualTo(chapters.get(i).getCurriculumName());
        }
    }

    @Test
    @DisplayName("createCurriculumStudyData 등록되지 않은 user는 예외 발생")
    void invalidUserData() {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        List<Curriculums> chapters = new ArrayList<>();
        chapters.add(Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1));
        chapters.add(Curriculums.createCurriculum(root, "챕터2", "키워드2: easy", "키워드2: normal", "키워드2: hard", true, 2, 1));
        curriculumRepository.saveAll(chapters);

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(100L); // 존재하지 않는 userId
        dto.setCurriculumId(root.getCurriculumId());

        try {
            //when
            studyService.createCurriculumStudyData(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }

    }

    @Test
    @DisplayName("getCurriculumProgress 메서드 정상 테스트")
    void getCurriculumProgress() {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        List<Curriculums> chapters = new ArrayList<>();
        chapters.add(Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1));
        chapters.add(Curriculums.createCurriculum(root, "챕터2", "키워드2: easy", "키워드2: normal", "키워드2: hard", true, 2, 1));
        chapters.add(Curriculums.createCurriculum(root, "챕터3", "키워드3: not testable", null, null, false, 3, 1));
        curriculumRepository.saveAll(chapters);

        List<Studies> studies = new ArrayList<>();
        List<Studies> easy = new ArrayList<>();

        for (Curriculums chapter : chapters) {
            if (chapter.isTestable()) {
                Studies easyStudy = Studies.createStudy(user, chapter, true, null, LevelType.EASY);
                easy.add(easyStudy);
                studies.add(easyStudy);
                studies.add(Studies.createStudy(user, chapter, false, null, LevelType.NORMAL));
                studies.add(Studies.createStudy(user, chapter, false, null, LevelType.HARD));
            } else {
                Studies easyStudy = Studies.createStudy(user, chapter, true, null, LevelType.EASY);
                easy.add(easyStudy);
                studies.add(easyStudy);
            }
        }

        studyRepository.saveAll(studies);

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(root.getCurriculumId());

        CurriculumPassedDto result = studyService.getCurriculumProgress(dto);

        //then
        for (int i = 0; i < result.getList().size(); i++) {
            Assertions.assertThat(result.getList().get(i).getCurriculumId()).isEqualTo(chapters.get(i).getCurriculumId());
            Assertions.assertThat(result.getList().get(i).getCurriculumName()).isEqualTo(chapters.get(i).getCurriculumName());
            Assertions.assertThat(result.getList().get(i).getPassed()).isEqualTo(easy.get(i).isPassed());
        }
    }
}