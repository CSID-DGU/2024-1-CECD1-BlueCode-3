package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class StudyRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CurriculumRepository curriculumRepository;
    @Autowired private StudyRepository studyRepository;

    @Test
    @DisplayName("findAllByCurriculumIdAndUserId 쿼리 테스트")
    void findAllByCurriculumIdAndUserId() throws Exception {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작))
        userRepository.save(user);
        Curriculums root = createCurriculum(null, "Test용 루트 커리큘럼", "", "", "", false, 0);
        curriculumRepository.save(root);

        List<Curriculums> chaps = new ArrayList<>();

        Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "1챕터 키워드 easy", "1챕터 키워드 normal", "1챕터 키워드 hard", true, 1);
        Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "2챕터 키워드 easy", "2챕터 키워드 normal", "2챕터 키워드 hard", false, 2);
        Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "3챕터 키워드 easy", "3챕터 키워드 normal", "3챕터 키워드 hard", true, 3);

        curriculumRepository.save(chap1);
        curriculumRepository.save(chap2);
        curriculumRepository.save(chap3);

        chaps.add(chap1);
        chaps.add(chap2);
        chaps.add(chap3);

        List<Studies> studiesNormal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Studies studies = createStudy(user, chaps.get(i), 60L + i, String.format("챕터 %d: " + LevelType.NORMAL + "NORMAL 학습자료: " + chaps.get(i).getCurriculumName() + String.format("테스트 Study 내용 입니다.: %d", i + 1), i + 1), true, LevelType.NORMAL);
            studyRepository.save(studies);
            studiesNormal.add(studies);
        }

        List<Studies> studiesHard = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Studies studies = createStudy(user, chaps.get(i), 60L + i, String.format("챕터 %d: " + LevelType.HARD + "HARD 학습자료: " + chaps.get(i).getCurriculumName() + String.format("테스트 Study 내용 입니다.: %d", i + 1), i + 1), true, LevelType.HARD);
            studyRepository.save(studies);
            studiesHard.add(studies);
        }

        //when
        List<List<Studies>> result = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            result.add(studyRepository.findAllByCurriculumIdAndUserId(chaps.get(i).getCurriculumId(), user.getUserId()));
        }

        //then
        for (int i = 0; i < result.size(); i++) {
            List<Studies> studies = result.get(i);
            Assertions.assertThat(studies.size()).isEqualTo(2);
        }
    }

    private Curriculums createCurriculum(
            Curriculums parent,
            String curriculumName,
            String keywordEasy,
            String keywordNormal,
            String keywordHard,
            boolean testable,
            int chapterNum
    ) {
        Curriculums curriculums = new Curriculums();
        curriculums.setCurriculumName(curriculumName);
        curriculums.setParent(parent);
        curriculums.setKeywordEasy(keywordEasy);
        curriculums.setKeywordNormal(keywordNormal);
        curriculums.setKeywordHard(keywordHard);
        curriculums.setTestable(testable);
        curriculums.setChapterNum(chapterNum);

        return curriculums;
    }

    private Studies createStudy(
            Users user,
            Curriculums curriculum,
            Long totalTime,
            String text,
            boolean passed,
            LevelType level
    ) {
        Studies studies = new Studies();
        studies.setUser(user);
        studies.setCurriculum(curriculum);
        studies.setTotalTime(totalTime);
        studies.setText(text);
        studies.setPassed(passed);
        studies.setLevel(level);

        return studies;
    }
}