package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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

    @Autowired
    private QuizCaseRepository quizCaseRepository;

    @Autowired
    private UserMissionRepository userMissionRepository;

    @BeforeEach
    void beforeEach() {
        curriculumRepository.deleteAll();
        studyRepository.deleteAll();
        chatRepository.deleteAll();
        testRepository.deleteAll();
        quizCaseRepository.deleteAll();
        quizRepository.deleteAll();
        userMissionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("createCurriculumStudyData 메서드 정상 테스트")
    void createCurriculumStudyData() {
        //given
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        List<Curriculums> curriculums = new ArrayList<>();

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        curriculums.add(root);
        curriculums.addAll(chapters);
        curriculums.addAll(sub);

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(root.getCurriculumId());

        studyService.createCurriculumStudyData(dto);

        //then
        List<Studies> created = studyRepository.findAllByUser(user);

        for (Studies study : created) {
            Assertions.assertThat(study.getCurriculum()).isIn(curriculums);
            Assertions.assertThat(study.getTextDef()).isNull();
            Assertions.assertThat(study.getTextCode()).isNull();
            Assertions.assertThat(study.getTextQuiz()).isNull();
            Assertions.assertThat(study.getLevel()).isNull();
        }
    }

    @Test
    @DisplayName("createCurriculumStudyData 등록되지 않은 user는 예외 발생")
    void invalidUserData() {
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

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
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        List<Curriculums> curriculums = new ArrayList<>();

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        curriculums.add(root);
        curriculums.addAll(chapters);
        curriculums.addAll(sub);
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
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        List<Curriculums> curriculums = new ArrayList<>();

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        curriculums.add(root);
        curriculums.addAll(chapters);
        curriculums.addAll(sub);

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
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        List<Curriculums> curriculums = new ArrayList<>();

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        curriculums.add(root);
        curriculums.addAll(chapters);
        curriculums.addAll(sub);

        // study
        List<Studies> studies = new ArrayList<>();
        for (Curriculums chapter : chapters) {
            studies.add(Studies.createStudy(user, chapter, false, null, null, null, null));
        }
        for (Curriculums subChapter : sub) {
            studies.add(Studies.createStudy(user, subChapter, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", subChapter.getChapterNum(), subChapter.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", subChapter.getChapterNum(), subChapter.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", subChapter.getChapterNum(), subChapter.getSubChapterNum()), null));
        }
        studyRepository.saveAll(studies);

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

    // temp
    @Test
    @DisplayName("getCurriculumProgress 메서드 정상 테스트")
    void getCurriculumProgress() {
        //given
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
        }
        studyRepository.saveAll(studies);

        //when
        DataCallDto dto = new DataCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(root.getCurriculumId());

        CurriculumPassedDto result = studyService.getCurriculumProgress(dto);
        Deque<Studies> deque = new ArrayDeque<>(subChapStudy);

        //then
        for (int i = 0; i < result.getList().size(); i++) {

            CurriculumPassedElementDto elementDto = result.getList().get(i);

            Assertions.assertThat(elementDto.getCurriculumId()).isEqualTo(chapStudy.get(i).getCurriculum().getCurriculumId());
            Assertions.assertThat(elementDto.getCurriculumName()).isEqualTo(chapStudy.get(i).getCurriculum().getCurriculumName());
            Assertions.assertThat(elementDto.getPassed()).isEqualTo(chapStudy.get(i).isPassed());

            List<CurriculumPassedElementDto> subChap = elementDto.getSubChapters();

            for (int j = 0; j < subChap.size(); j++) {
                CurriculumPassedElementDto subChapElement = subChap.get(j);
                Studies temp = deque.removeFirst();
                Assertions.assertThat(subChapElement.getCurriculumId()).isEqualTo(temp.getCurriculum().getCurriculumId());
                Assertions.assertThat(subChapElement.getCurriculumName()).isEqualTo(temp.getCurriculum().getCurriculumName());
                Assertions.assertThat(subChapElement.getPassed()).isEqualTo(temp.isPassed());
            }
        }
    }

    @Test
    @DisplayName("getCurriculumProgress 등록되지 않은 user는 예외 발생")
    void invalidUser() {
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);
        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
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
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
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
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
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
    void chapterPass() {
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), LevelType.EASY);
            studies.add(temp);
            subChapStudy.add(temp);
        }
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(subChapStudy.get(0).getCurriculum().getCurriculumId());

        boolean result = studyService.subChapterPass(dto);

        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(subChapStudy.get(0).isPassed()).isTrue();
    }

    @Test
    @DisplayName("chapterPass 학습중이 아닌 챕터 통과 요청시 예외 발생")
    void notStartCurriculum() {
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
        }
        studyRepository.saveAll(studies);

        //when
        CurriculumPassCallDto dto = new CurriculumPassCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(sub.get(0).getCurriculumId());

        try {
            //when
            studyService.subChapterPass(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("학습중이 아닌 서브 챕터입니다.");
        }
    }

    @Test
    @DisplayName("getCurriculumText 정상 로직 테스트(이미 생성된 text 리턴)")
    void getCurriculumText() {
        //given
        // 유저
        Users user = Users.createUser("testName", "testEmail", "testId", "1111", "11110033", false);
        userRepository.save(user);

        // 커리큘럼
        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, null, "루트", false, 0, 0, 3, false, true, LangType.PYTHON);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, null, "챕터1",false, 1, 0, 2, false, false, LangType.PYTHON);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, null, "챕터2",false, 2, 0,2, false, false, LangType.PYTHON);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, null, "챕터3",false, 3, 0,2, false, false, LangType.PYTHON);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        chap1.setNext(chap2);
        chap2.setNext(chap3);
        chap3.setNext(null);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap1, root, null, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap2, root, null, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false, LangType.PYTHON));
        sub.add(Curriculums.createCurriculum(chap3, root, null, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false, LangType.PYTHON));

        curriculumRepository.saveAll(sub);

        List<Studies> studies = new ArrayList<>();
        List<Studies> chapStudy = new ArrayList<>();
        List<Studies> subChapStudy = new ArrayList<>();

        // study
        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums curriculums : chapters) {
            Studies temp = Studies.createStudy(user, curriculums, false, null, null, null, null);
            studies.add(temp);
            chapStudy.add(temp);
        }

        for (Curriculums curriculums : sub) {

            Studies temp = Studies.createStudy(user, curriculums, false,
                    String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
                    String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null);
            studies.add(temp);
            subChapStudy.add(temp);
        }
        studyRepository.saveAll(studies);

        //when
        CurriculumTextCallDto dto = new CurriculumTextCallDto();
        dto.setUserId(user.getUserId());
        dto.setCurriculumId(sub.get(0).getCurriculumId());
        dto.setTextType(TextType.DEF);
        dto.setLevelType(LevelType.EASY);

        StudyTextDto result = studyService.getCurriculumText(dto);

        //then
        Assertions.assertThat(result.getText()).isEqualTo("챕터 1 - 서브챕터 1: DEF 학습 자료");
    }
}