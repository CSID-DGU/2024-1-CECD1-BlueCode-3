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
    @DisplayName("createCurriculumStudyData 등록되지 않은 root는 예외 발생")
    void invalidRootCurriculumData() {
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
        dto.setCurriculumId(100L); // 존재하지 않는 root 커리큘럼

        try {
            //when
            studyService.createCurriculumStudyData(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 루트 커리큘럼 id 입니다.");
        }
    }

    @Test
    @DisplayName("createCurriculumStudyData root 커리큘럼이 아니면 예외 발생")
    void invalidRootData() {
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
        dto.setCurriculumId(chapters.get(0).getCurriculumId()); // root가 아닌 커리큘럼 id

        try {
            //when
            studyService.createCurriculumStudyData(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("루트 커리큘럼 id가 아닙니다.");
        }
    }

    @Test
    @DisplayName("createCurriculumStudyData 이미 생성된 Study 데이터가 존재하면 예외 발생")
    void DuplicateCreationData() {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        List<Curriculums> chapters = new ArrayList<>();
        chapters.add(Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1));
        chapters.add(Curriculums.createCurriculum(root, "챕터2", "키워드2: easy", "키워드2: normal", "키워드2: hard", true, 2, 1));
        curriculumRepository.saveAll(chapters);

        List<Studies> studies = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            studies.add(Studies.createStudy(user, chapters.get(i),false, null, LevelType.EASY));
            studies.add(Studies.createStudy(user, chapters.get(i),false, null, LevelType.NORMAL));
            studies.add(Studies.createStudy(user, chapters.get(i),false, null, LevelType.HARD));
        }

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(root.getCurriculumId());

        try {
            //when
            studyService.createCurriculumStudyData(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 해당 유저의 학습 대상 커리큘럼 데이터가 생성되어있습니다.");
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

    @Test
    @DisplayName("getCurriculumProgress 등록되지 않은 user는 예외 발생")
    void invalidUser() {
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
        dto.setUserId(100L); // 유효하지 않은 user id
        dto.setCurriculumId(root.getCurriculumId());

        try {
            //when
            studyService.getCurriculumProgress(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }
    }

    @Test
    @DisplayName("getCurriculumProgress 등록되지 않은 root는 예외 발생")
    void invalidRoot() {
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
        dto.setCurriculumId(100L); // 유효하지 않은 root 커리큘럼 id

        try {
            //when
            studyService.getCurriculumProgress(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 루트 커리큘럼 id 입니다.");
        }
    }

    @Test
    @DisplayName("getCurriculumProgress root가 아닌 curriculum은 예외 발생")
    void invalidChapter() {
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
        dto.setCurriculumId(chapters.get(0).getCurriculumId()); // root가 아닌 커리큘럼 id

        try {
            //when
            studyService.getCurriculumProgress(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("루트 커리큘럼 id가 아닙니다.");
        }
    }

    @Test
    @DisplayName("chapterPass 메서드 통과 상황 정상 테스트")
    public void chapterPass() throws Exception {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        Curriculums chapter = Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1);
        curriculumRepository.save(chapter);

        List<Studies> studies = new ArrayList<>();
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.EASY));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.NORMAL));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.HARD));
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(chapter.getCurriculumId());
        // EASY - NORMAL 까지 모두 통과 상황 가정
        dto.setCurrentLevel(LevelType.EASY);
        dto.setNextLevel(LevelType.NORMAL);

        try {
            //when
            studyService.chapterPass(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 루트 커리큘럼 id 입니다.");
        }
    }

    @Test
    @DisplayName("chapterPass 메서드 미통과 상황 정상 테스트")
    public void chapterFail() throws Exception {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        Curriculums chapter = Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1);
        curriculumRepository.save(chapter);

        List<Studies> studies = new ArrayList<>();
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.EASY));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.NORMAL));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.HARD));
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(chapter.getCurriculumId());
        // EASY - EASY로 설정 시, 챕터 미통과 처리
        dto.setCurrentLevel(LevelType.EASY);
        dto.setNextLevel(LevelType.EASY);

        String result = studyService.chapterPass(dto);

        //then
        for (Studies study : studies) {
            // 모든 난이도 false
            Assertions.assertThat(study.isPassed()).isEqualTo(false);
        }
        Assertions.assertThat(result).isEqualTo("챕터 학습 미완료");
    }

    @Test
    @DisplayName("chapterPass 학습중이 아닌 챕터 통과 요청시 예외 발생")
    public void notStartCurriculum() throws Exception {
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 1);
        curriculumRepository.save(root);
        Curriculums chapter = Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1);
        curriculumRepository.save(chapter);

        // root에 포함되지 않는 chapter
        Curriculums root2 = Curriculums.createCurriculum(null, "루트 커리큘럼2", "", "", "", false, 0, 1);
        curriculumRepository.save(root2);

        Curriculums notIncluded = Curriculums.createCurriculum(root2, "챕터", "키워드: easy", "키워드: normal", "키워드: hard", true, 1, 1);
        curriculumRepository.save(notIncluded);

        List<Studies> studies = new ArrayList<>();
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.EASY));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.NORMAL));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.HARD));
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(notIncluded.getCurriculumId());
        dto.setCurrentLevel(LevelType.EASY);
        dto.setNextLevel(LevelType.HARD);

        try {
            //when
            studyService.chapterPass(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("학습중인 챕터가 아닙니다.");
        }
    }

    @Test
    @DisplayName("chapterPass 맞지 않는 Level 설정 시 예외 발생")
    public void invalidLevelSet() throws Exception {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 2);
        curriculumRepository.save(root);
        Curriculums chapter = Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1);
        curriculumRepository.save(chapter);

        List<Studies> studies = new ArrayList<>();
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.EASY));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.NORMAL));
        studies.add(Studies.createStudy(user, chapter, false, null, LevelType.HARD));
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(chapter.getCurriculumId());
        // HARD - EASY로 설정 시, 예외 발생
        dto.setCurrentLevel(LevelType.HARD);
        dto.setNextLevel(LevelType.EASY);

        try {
            //when
            studyService.chapterPass(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 currentLevel 과 NextLevel 입니다.");
        }
    }

    @Test
    @DisplayName("getCurriculumText 정상 로직 테스트(이미 생성된 text 리턴)")
    public void getCurriculumText() throws Exception {
        //given
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        Curriculums root = Curriculums.createCurriculum(null, "루트 커리큘럼", "", "", "", false, 0, 1);
        curriculumRepository.save(root);
        Curriculums chapter = Curriculums.createCurriculum(root, "챕터1", "키워드1: easy", "키워드1: normal", "키워드1: hard", true, 1, 1);
        curriculumRepository.save(chapter);

        List<Studies> studies = new ArrayList<>();
        studies.add(Studies.createStudy(user, chapter, false, "학습 text: EASY", LevelType.EASY));
        studies.add(Studies.createStudy(user, chapter, false, "학습 text: NORMAL", LevelType.NORMAL));
        studies.add(Studies.createStudy(user, chapter, false, "학습 text: HARD", LevelType.HARD));
        studyRepository.saveAll(studies);


        LevelType[] arr = new LevelType[]{LevelType.EASY, LevelType.NORMAL, LevelType.HARD};

        for (LevelType levelType : arr) {
            //when
            CurriculumTextCallDto dto = new CurriculumTextCallDto();
            dto.setUserId(user.getUserId());
            dto.setCurriculumId(chapter.getCurriculumId());
            dto.setLevelType(levelType);

            //then
            StudyTextDto result = studyService.getCurriculumText(dto);
            Assertions.assertThat(result.getText()).isEqualTo("학습 text: " + levelType);
        }
    }


}