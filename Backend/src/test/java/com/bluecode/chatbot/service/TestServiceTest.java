package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.*;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class TestServiceTest {

    @Autowired
    private TestService testService;

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
    private UserMissionRepository userMissionRepository;

    @BeforeEach
    void beforeEach() {
        curriculumRepository.deleteAll();
        studyRepository.deleteAll();
        chatRepository.deleteAll();
        testRepository.deleteAll();
        quizRepository.deleteAll();
        userMissionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("createInitTest 메서드 정상 작동 테스트")
    public void createInitTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);

        List<Quiz> giveHard = new ArrayList<>();
        List<Quiz> giveNormal = new ArrayList<>();
        List<Quiz> giveEasy = new ArrayList<>();

        // quiz
        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");

        giveHard.add(quizHard1);
        quizRepository.save(quizHard1);

        Quiz quizHard2 = new Quiz();
        quizHard2.setQuizType(QuizType.NUM);
        quizHard2.setLevel(QuizLevel.HARD);
        quizHard2.setCurriculum(chap1);
        quizHard2.setText("hard2");

        giveHard.add(quizHard2);
        quizRepository.save(quizHard2);

        Quiz quizHard3 = new Quiz();
        quizHard3.setQuizType(QuizType.NUM);
        quizHard3.setLevel(QuizLevel.HARD);
        quizHard3.setCurriculum(chap1);
        quizHard3.setText("hard3");

        giveHard.add(quizHard3);
        quizRepository.save(quizHard3);


        Quiz quizNormal1 = new Quiz();
        quizNormal1.setQuizType(QuizType.NUM);
        quizNormal1.setLevel(QuizLevel.NORMAL);
        quizNormal1.setCurriculum(chap1);
        quizNormal1.setText("normal1");

        giveNormal.add(quizNormal1);
        quizRepository.save(quizNormal1);

        Quiz quizNormal2 = new Quiz();
        quizNormal2.setQuizType(QuizType.NUM);
        quizNormal2.setLevel(QuizLevel.NORMAL);
        quizNormal2.setCurriculum(chap1);
        quizNormal2.setText("normal2");

        giveNormal.add(quizNormal2);
        quizRepository.save(quizNormal2);


        Quiz quizEasy1 = new Quiz();
        quizEasy1.setQuizType(QuizType.NUM);
        quizEasy1.setLevel(QuizLevel.EASY);
        quizEasy1.setCurriculum(chap1);
        quizEasy1.setText("easy1");

        giveEasy.add(quizEasy1);
        quizRepository.save(quizEasy1);

        Quiz quizEasy2 = new Quiz();
        quizEasy2.setQuizType(QuizType.NUM);
        quizEasy2.setLevel(QuizLevel.EASY);
        quizEasy2.setCurriculum(chap1);
        quizEasy2.setText("easy2");

        giveEasy.add(quizEasy2);
        quizRepository.save(quizEasy2);

        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        dto.setUserId(userA.getUserId());

        //when
        TestResponseDto result = testService.createInitTest(dto);

        //then

        // 초기 테스트는 총 4개의 문제가 리턴되어야 한다.
        Assertions.assertThat(result.getTests().size()).isEqualTo(4);

        // result 에서 QuizLevel 순서는 HARD - NORMAL - EASY - HARD 순서여야 한다.
        Assertions.assertThat(result.getTests().get(0).getLevel()).isEqualTo(QuizLevel.HARD);
        Assertions.assertThat(result.getTests().get(1).getLevel()).isEqualTo(QuizLevel.NORMAL);
        Assertions.assertThat(result.getTests().get(2).getLevel()).isEqualTo(QuizLevel.EASY);
        Assertions.assertThat(result.getTests().get(3).getLevel()).isEqualTo(QuizLevel.HARD);
    }

    @Test
    @DisplayName("createNormalTest 메서드 정상 작동 테스트")
    public void createNormalTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);

        List<Quiz> giveHard = new ArrayList<>();
        List<Quiz> giveNormal = new ArrayList<>();
        List<Quiz> giveEasy = new ArrayList<>();

        // quiz
        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");

        giveHard.add(quizHard1);
        quizRepository.save(quizHard1);

        Quiz quizHard2 = new Quiz();
        quizHard2.setQuizType(QuizType.NUM);
        quizHard2.setLevel(QuizLevel.HARD);
        quizHard2.setCurriculum(chap1);
        quizHard2.setText("hard2");

        giveHard.add(quizHard2);
        quizRepository.save(quizHard2);

        Quiz quizHard3 = new Quiz();
        quizHard3.setQuizType(QuizType.NUM);
        quizHard3.setLevel(QuizLevel.HARD);
        quizHard3.setCurriculum(chap1);
        quizHard3.setText("hard3");

        giveHard.add(quizHard3);
        quizRepository.save(quizHard3);


        Quiz quizNormal1 = new Quiz();
        quizNormal1.setQuizType(QuizType.NUM);
        quizNormal1.setLevel(QuizLevel.NORMAL);
        quizNormal1.setCurriculum(chap1);
        quizNormal1.setText("normal1");

        giveNormal.add(quizNormal1);
        quizRepository.save(quizNormal1);

        Quiz quizNormal2 = new Quiz();
        quizNormal2.setQuizType(QuizType.NUM);
        quizNormal2.setLevel(QuizLevel.NORMAL);
        quizNormal2.setCurriculum(chap1);
        quizNormal2.setText("normal2");

        giveNormal.add(quizNormal2);
        quizRepository.save(quizNormal2);


        Quiz quizEasy1 = new Quiz();
        quizEasy1.setQuizType(QuizType.NUM);
        quizEasy1.setLevel(QuizLevel.EASY);
        quizEasy1.setCurriculum(chap1);
        quizEasy1.setText("easy1");

        giveEasy.add(quizEasy1);
        quizRepository.save(quizEasy1);

        Quiz quizEasy2 = new Quiz();
        quizEasy2.setQuizType(QuizType.NUM);
        quizEasy2.setLevel(QuizLevel.EASY);
        quizEasy2.setCurriculum(chap1);
        quizEasy2.setText("easy2");

        giveEasy.add(quizEasy2);
        quizRepository.save(quizEasy2);

        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        dto.setUserId(userA.getUserId());

        //when
        TestResponseDto result = testService.createNormalTest(dto);

        //then

        // 초기 테스트는 총 3개의 문제가 리턴되어야 한다.
        Assertions.assertThat(result.getTests().size()).isEqualTo(3);

        // result 에서 QuizLevel 순서는 EASY - NORMAL - HARD 순서여야 한다.
        Assertions.assertThat(result.getTests().get(0).getLevel()).isEqualTo(QuizLevel.EASY);
        Assertions.assertThat(result.getTests().get(1).getLevel()).isEqualTo(QuizLevel.NORMAL);
        Assertions.assertThat(result.getTests().get(2).getLevel()).isEqualTo(QuizLevel.HARD);
    }

    @Test
    @DisplayName("유효하지 않은 userId가 주어지면 Exception 발생")
    public void invalidUserIdTest() throws Exception {
        //given
        // DB에 존재하지 않는 user 가정
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userA.setUserId(100L);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);

        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        dto.setUserId(userA.getUserId());

        //  유효하지 않은 userId일 경우, Exception 발생
        try {
            //when
            testService.createInitTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }

        try {
            //when
            testService.createNormalTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }
    }

    @Test
    @DisplayName("유효하지 않은 curriculumId가 주어지면 Exception 발생")
    public void invalidCurriculumIdTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();

        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",false, 1, 0, 2, false, false);
        // DB에 존재하지 않는 curriculum 가정
//        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        Curriculums chap = new Curriculums();
        chap.setTestable(true);
        chap.setChapterNum(1);
        chap.setParent(root);
        chap.setCurriculumName("챕터1");
        chap.setCurriculumId(100L);


        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap.getCurriculumId());
        dto.setUserId(userA.getUserId());

        //  유효하지 않은 userId일 경우, Exception 발생
        try {
            //when
            testService.createInitTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 커리큘럼 id 입니다.");
        }

        try {
            //when
            testService.createNormalTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 커리큘럼 id 입니다.");
        }

    }

    @Test
    @DisplayName("testable = false 일 경우, Exception 발생")
    public void notTestableTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",false, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);

        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        dto.setUserId(userA.getUserId());

        // testable = false 일 경우, Exception 발생
        try {
            //when
            testService.createInitTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("테스트를 진행하지 않는 챕터입니다.");
        }

        try {
            //when
            testService.createNormalTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("테스트를 진행하지 않는 챕터입니다.");
        }

    }

    @Test
    @DisplayName("이미 initTest를 수행하였다면 initTest를 생성할 수 없다.")
    public void alreadyTestedTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", true);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);


        DataCallDto dto = new DataCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        dto.setUserId(userA.getUserId());

        // 유효하지 않은 userId일 경우, Exception 발생
        try {
            //when
            testService.createInitTest(dto);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 초기 테스트를 진행하였습니다.");
        }
    }

    @Test
    @DisplayName("submitAnswer 메서드 answer 정답 case 정상 작동 테스트")
    public void submitAnswerCorrectTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);


        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");
        quizHard1.setAnswer("정답");
        quizRepository.save(quizHard1);

        Tests test = new Tests();
        test.setUser(userA);
        test.setPassed(false);
        test.setTestType(TestType.INIT);
        test.setQuiz(quizHard1);
        test.setWrongCount(0);

        testRepository.save(test);

        TestAnswerCallDto dto = new TestAnswerCallDto();
        dto.setTestId(test.getTestId());
        dto.setUserId(userA.getUserId());
        dto.setQuizId(quizHard1.getQuizId());
        dto.setAnswer("정답");

        //when
        TestAnswerResponseDto result = testService.submitAnswerNum(dto);

        //then

        // 정답일 시 true를 return 한다.
        Assertions.assertThat(result.getPassed()).isEqualTo(true);

        // 정답일 시, passed = true 이다.
        Assertions.assertThat(test.isPassed()).isEqualTo(true);
    }

    @Test
    @DisplayName("submitAnswer 메서드 answer 오답 case 정상 작동 테스트")
    public void submitAnswerWrongTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);


        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");
        quizHard1.setAnswer("정답");
        quizRepository.save(quizHard1);

        Tests test = new Tests();
        test.setUser(userA);
        test.setPassed(false);
        test.setTestType(TestType.INIT);
        test.setQuiz(quizHard1);
        test.setWrongCount(0);

        testRepository.save(test);


        TestAnswerCallDto dto = new TestAnswerCallDto();
        dto.setTestId(test.getTestId());
        dto.setUserId(userA.getUserId());
        dto.setQuizId(quizHard1.getQuizId());
        dto.setAnswer("오답");

        //when
        TestAnswerResponseDto result = testService.submitAnswerNum(dto);

        //then

        // 오답일 시 false를 return 한다.
        Assertions.assertThat(result.getPassed()).isEqualTo(false);

        // 오답일 시, passed = false 이다.
        Assertions.assertThat(test.isPassed()).isEqualTo(false);

        // 오답일 시, wrongCount 가 1 증가한다.
        Assertions.assertThat(test.getWrongCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("유효하지 않은 userId일 시, Exception 발생")
    public void submitAnswerInvalidUserId() throws Exception {
        //given
        // DB에 존재하지 않는 User 가정
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userA.setUserId(100L);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);

        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");
        quizHard1.setAnswer("정답");
        quizRepository.save(quizHard1);

        Tests test = new Tests();
        test.setUser(userA);
        test.setPassed(false);
        test.setTestType(TestType.INIT);
        test.setQuiz(quizHard1);
        test.setWrongCount(0);

        testRepository.save(test);


        TestAnswerCallDto dto = new TestAnswerCallDto();
        dto.setTestId(test.getTestId());
        dto.setUserId(userA.getUserId());
        dto.setQuizId(quizHard1.getQuizId());
        dto.setAnswer("오답");

        try {
            testService.submitAnswerNum(dto);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }

    }

    @Test
    @DisplayName("유효하지 않은 quizId일 시, Exception 발생")
    public void submitAnswerInvalidQuizId() throws Exception {
        //given

        // DB에 존재하지 않는 User 가정
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);


        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");
        quizHard1.setAnswer("정답");

        // DB에 존재하지 않는 quiz 가정
        quizHard1.setQuizId(100L);

        Tests test = new Tests();
        test.setUser(userA);
        test.setPassed(false);
        test.setTestType(TestType.INIT);
        test.setQuiz(quizHard1);
        test.setWrongCount(0);

        testRepository.save(test);


        TestAnswerCallDto dto = new TestAnswerCallDto();
        dto.setTestId(test.getTestId());
        dto.setUserId(userA.getUserId());
        dto.setQuizId(quizHard1.getQuizId());
        dto.setAnswer("오답");

        try {
            testService.submitAnswerNum(dto);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 퀴즈 id 입니다.");
        }
    }

    @Test
    @DisplayName("유효하지 않은 testId일 시, Exception 발생")
    public void submitAnswerInvalidTestId() throws Exception {
        //given

        // DB에 존재하지 않는 User 가정
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
        curriculumRepository.saveAndFlush(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",true, 1, 0, 2, false, false);
        chapters.add(chap1);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", false, 1, 2, 1, true, false));
        curriculumRepository.saveAll(sub);


        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap1);
        quizHard1.setText("hard2");
        quizHard1.setAnswer("정답");

        quizRepository.save(quizHard1);

        Tests test = new Tests();
        test.setUser(userA);
        test.setPassed(false);
        test.setTestType(TestType.INIT);
        test.setQuiz(quizHard1);
        test.setWrongCount(0);

        // DB에 존재하지 않는 test 가정
        test.setTestId(100L);


        TestAnswerCallDto dto = new TestAnswerCallDto();
        dto.setTestId(test.getTestId());
        dto.setUserId(userA.getUserId());
        dto.setQuizId(quizHard1.getQuizId());
        dto.setAnswer("오답");

        try {
            testService.submitAnswerNum(dto);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 테스트 id 입니다.");
        }
    }

    @Test
    @DisplayName("completeInitTest 정상 작동 테스트")
    public void completeInitTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        //when
        String result = testService.completeInitTest(userA.getUserId());

        //then
        Assertions.assertThat(result).isEqualTo("초기 테스트 완료");
    }

    @Test
    @DisplayName("유효하지 않은 userId일 시, Exception 발생")
    public void invalidUserId() throws Exception {
        //given
        // DB에 존재하지 않는 user 가정
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);

        try {
            //when
            String result = testService.completeInitTest(userA.getUserId());
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 유저 테이블 id 입니다.");
        }
    }

    @Test
    @DisplayName("이미 initTest == true 일 경우, Exception 발생")
    public void alreadyCompletedInitTest() throws Exception {
        //given
        Users userA = Users.createUser("userA", "userA@aaa.aaa", "userAId", "1234", "10001101", false);
        userRepository.save(userA);

        try {
            //when
            String result = testService.completeInitTest(userA.getUserId());
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 초기 테스트 완료 처리 되었습니다.");
        }
    }
}