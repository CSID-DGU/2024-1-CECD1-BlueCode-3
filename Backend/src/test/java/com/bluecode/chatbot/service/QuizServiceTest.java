package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Quiz;
import com.bluecode.chatbot.domain.QuizLevel;
import com.bluecode.chatbot.domain.QuizType;
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
class QuizServiceTest {

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
    private QuizService quizService;

    @BeforeEach
    void beforeEach() {
        curriculumRepository.deleteAll();
        studyRepository.deleteAll();
        chatRepository.deleteAll();
        testRepository.deleteAll();
        quizRepository.deleteAll();
    }

    @Test
    @DisplayName("getRandomQuizzesByTypeAndLevel 메서드 정상 작동 테스트")
    void getRandomQuizzesByTypeAndLevel() {

        //give
        Curriculums root = new Curriculums();
        root.setChapterNum(0);
        root.setParent(null);
        root.setCurriculumName("루트 커리큘럼");
        root.setTestable(false);

        curriculumRepository.save(root);

        Curriculums chap4 = new Curriculums();
        chap4.setTestable(true);
        chap4.setChapterNum(4);
        chap4.setParent(root);

        curriculumRepository.save(chap4);

        List<Quiz> give = new ArrayList<>();

        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap4);

        give.add(quizHard1);
        quizRepository.save(quizHard1);

        Quiz quizHard2 = new Quiz();
        quizHard2.setQuizType(QuizType.NUM);
        quizHard2.setLevel(QuizLevel.HARD);
        quizHard2.setCurriculum(chap4);

        give.add(quizHard2);
        quizRepository.save(quizHard2);

        Quiz quizHard3Code = new Quiz();
        quizHard3Code.setQuizType(QuizType.CODE);
        quizHard3Code.setLevel(QuizLevel.HARD);
        quizHard3Code.setCurriculum(chap4);

        quizRepository.save(quizHard3Code);

        QuizType type = QuizType.NUM; // 객관식
        QuizLevel quizLevel = QuizLevel.HARD; // 중급자
        int count = 2;  // 선택할 퀴즈 수

        //when
        List<Quiz> result = quizService.getRandomQuizzesByTypeAndLevel(chap4, type, quizLevel, count);

        //then
        Assertions.assertThat(result.size()).isEqualTo(count);

        // 객관식 문제가 2개 들어있어야 한다.
        for (Quiz quiz : give) {
            Assertions.assertThat(quiz).isIn(result);
        }

        // 코드 작성식 문제는 포함하지 않는다.
        Assertions.assertThat(quizHard3Code).isNotIn(result);
    }

    @Test
    @DisplayName("getRandomQuizzesByLevel 메서드 정상 작동 테스트")
    void getRandomQuizzesByLevel() {
        //given
        Curriculums root = new Curriculums();
        root.setChapterNum(0);
        root.setParent(null);
        root.setCurriculumName("루트 커리큘럼");
        root.setTestable(false);

        curriculumRepository.save(root);

        Curriculums chap4 = new Curriculums();
        chap4.setTestable(true);
        chap4.setChapterNum(4);
        chap4.setParent(root);

        curriculumRepository.save(chap4);

        List<Quiz> give = new ArrayList<>();

        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap4);

        give.add(quizHard1);
        quizRepository.save(quizHard1);

        Quiz quizHard2 = new Quiz();
        quizHard2.setQuizType(QuizType.NUM);
        quizHard2.setLevel(QuizLevel.HARD);
        quizHard2.setCurriculum(chap4);

        give.add(quizHard2);
        quizRepository.save(quizHard2);

        Quiz quizHard3 = new Quiz();
        quizHard3.setQuizType(QuizType.CODE);
        quizHard3.setLevel(QuizLevel.HARD);
        quizHard3.setCurriculum(chap4);

        give.add(quizHard3);
        quizRepository.save(quizHard3);

        Quiz quizNormal = new Quiz();
        quizNormal.setQuizType(QuizType.CODE);
        quizNormal.setLevel(QuizLevel.NORMAL);
        quizNormal.setCurriculum(chap4);

        give.add(quizNormal);
        quizRepository.save(quizNormal);

        QuizLevel quizLevel = QuizLevel.HARD; // 중급자
        int count = 2;  // 선택할 퀴즈 수

        //when
        List<Quiz> result = quizService.getRandomQuizzesByLevel(chap4, quizLevel, count);

        //then

        // 문제는 2개여야 한다.
        Assertions.assertThat(result.size()).isEqualTo(2);
        

        // result 의 각 문제는 DB에 존재하는 문제여야 한다.
        for (Quiz quiz : result) {
            Assertions.assertThat(quiz).isIn(give);
        }

        // quizNormal은 포함되지 않아야 한다.
        Assertions.assertThat(quizNormal).isNotIn(result);

    }

    @Test
    @DisplayName("root 커리큘럼을 대상으로 할 시, Exception 이 발생한다.")
    void rootCurriculumExceptionTest() {
        //given
        Curriculums root = new Curriculums();
        root.setChapterNum(0);
        root.setParent(null);
        root.setCurriculumName("루트 커리큘럼");
        root.setTestable(false);

        int count = 100;
        QuizType type = QuizType.NUM;
        QuizLevel quizLevel = QuizLevel.HARD;

        //when

        //then
        // Quiz 가 존재하지 않으므로 각 method 에서 Exception 이 발생해야 한다.
        Assertions.assertThatThrownBy(() ->  quizService.getRandomQuizzesByTypeAndLevel(root, type, quizLevel, count))
                .isInstanceOf(Exception.class);

        Assertions.assertThatThrownBy(() ->  quizService.getRandomQuizzesByLevel(root, quizLevel, count))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("전체 문제 수가 요청한 문제 수보다 적을 경우, 존재하는 문제 수 만큼만 제공한다.")
    void limitOfQuizCountTest() {

        //given
        Curriculums root = new Curriculums();
        root.setChapterNum(0);
        root.setParent(null);
        root.setCurriculumName("루트 커리큘럼");
        root.setTestable(false);

        curriculumRepository.save(root);

        Curriculums chap4 = new Curriculums();
        chap4.setTestable(true);
        chap4.setChapterNum(4);
        chap4.setParent(root);

        curriculumRepository.save(chap4);

        List<Quiz> give = new ArrayList<>();

        Quiz quizHard1 = new Quiz();
        quizHard1.setQuizType(QuizType.NUM);
        quizHard1.setLevel(QuizLevel.HARD);
        quizHard1.setCurriculum(chap4);

        give.add(quizHard1);
        quizRepository.save(quizHard1);

        Quiz quizHard2 = new Quiz();
        quizHard2.setQuizType(QuizType.NUM);
        quizHard2.setLevel(QuizLevel.HARD);
        quizHard2.setCurriculum(chap4);

        give.add(quizHard2);
        quizRepository.save(quizHard2);

        Quiz quizHard3Code = new Quiz();
        quizHard3Code.setQuizType(QuizType.CODE);
        quizHard3Code.setLevel(QuizLevel.HARD);
        quizHard3Code.setCurriculum(chap4);

        quizRepository.save(quizHard3Code);

        int count = 100;
        QuizType type = QuizType.NUM;
        QuizLevel quizLevel = QuizLevel.HARD;

        //when
        List<Quiz> result1 = quizService.getRandomQuizzesByTypeAndLevel(chap4, type, quizLevel, count);
        List<Quiz> result2 = quizService.getRandomQuizzesByLevel(chap4, quizLevel, count);

        //then

        // 결과는 2개 존재해야 한다.
        Assertions.assertThat(result1.size()).isEqualTo(2);
        Assertions.assertThat(result2.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("챕터 대상 문제가 존재하지 않을 경우, Exception 발생")
    void notExistQuizOfCurriculum() {

        //given
        Curriculums root = new Curriculums();
        root.setChapterNum(0);
        root.setParent(null);
        root.setCurriculumName("루트 커리큘럼");
        root.setTestable(false);
        curriculumRepository.save(root);

        Curriculums chap100 = new Curriculums();
        chap100.setCurriculumName("챕터 100");
        chap100.setTestable(true);
        chap100.setParent(root);
        chap100.setChapterNum(100);
        curriculumRepository.save(chap100);

        int count = 100;
        QuizType type = QuizType.NUM;
        QuizLevel quizLevel = QuizLevel.HARD;

        // Quiz 가 존재하지 않으므로 각 method 에서 Exception 이 발생해야 한다.
        try {
            //when
            quizService.getRandomQuizzesByTypeAndLevel(chap100, type, quizLevel, count);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("대상 커리큘럼에 해당하는 문제가 존재하지 않습니다.");
        }

        try {
            //when
            quizService.getRandomQuizzesByLevel(chap100, quizLevel, count);
        } catch (Exception e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("대상 커리큘럼에 해당하는 문제가 존재하지 않습니다.");
        }
    }

}