package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class QuizRepositoryTest {

    @Autowired private QuizRepository quizRepository;
    @Autowired private CurriculumRepository curriculumRepository;

    @Test
    @DisplayName("findAllByCurriculumId 쿼리 테스트")
    public void findAllByCurriculumIdAndChapterNum() throws Exception {
        //given
        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        int chapNum = 2;

        List<Curriculums> chaps = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Quiz> result = quizRepository.findAllByCurriculumId(chaps.get(chapNum - 1).getCurriculumId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(4); // 챕터 2인 문제는 총 4문제 존재

    }

    @Test
    @DisplayName("findByCurriculumIdAndLevel 쿼리 테스트")
    public void findByCurriculumIdAndLevel() throws Exception {
        //given
        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        int chapNum = 2;
        QuizLevel quizLevel = QuizLevel.HARD;

        List<Curriculums> chaps = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Quiz> result = quizRepository.findByCurriculumIdAndLevel(chaps.get(chapNum - 1).getCurriculumId(), quizLevel);

        //then
        Assertions.assertThat(result.size()).isEqualTo(2); // 챕터 2인 HARD 문제는 총 2문제 존재
    }

    @Test
    @DisplayName("findAllByCurriculumIdAndQuizType 쿼리 테스트")
    public void findAllByCurriculumIdAndQuizType() throws Exception {
        //given
        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        int chapNum = 2;
        QuizType quizType = QuizType.NUM;

        List<Curriculums> chaps = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Quiz> result = quizRepository.findAllByCurriculumIdAndQuizType(chaps.get(chapNum - 1).getCurriculumId(), QuizType.NUM);
        List<Quiz> result2 = quizRepository.findAllByCurriculumIdAndQuizType(chaps.get(chapNum - 1).getCurriculumId(), QuizType.CODE);

        //then
        Assertions.assertThat(result.size()).isEqualTo(4); // 챕터 2인 객관식 문제는 총 4문제 존재
        Assertions.assertThat(result2.size()).isEqualTo(0); // 챕터 2인 코드작성식 문제는 총 0문제 존재

    }

    @Test
    @DisplayName("findAllByCurriculumIdAndQuizTypeAndLevel 쿼리 테스트")
    public void findAllByCurriculumIdAndQuizTypeAndLevel() throws Exception {
        //given
        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        int chapNum = 2;

        List<Curriculums> chaps = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Quiz> result = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(chaps.get(chapNum - 1).getCurriculumId(), QuizType.NUM, QuizLevel.HARD);
        List<Quiz> result2 = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(chaps.get(chapNum - 1).getCurriculumId(), QuizType.NUM, QuizLevel.NORMAL);
        List<Quiz> result3 = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(chaps.get(chapNum - 1).getCurriculumId(), QuizType.CODE, QuizLevel.HARD);

        //then
        Assertions.assertThat(result.size()).isEqualTo(2); // 챕터 2인 객관식 Hard 문제는 총 2문제 존재
        Assertions.assertThat(result2.size()).isEqualTo(1); // 챕터 2인 객관식 Normal 문제는 총 1문제 존재
        Assertions.assertThat(result3.size()).isEqualTo(0); // 챕터 2인 코드작성식 Normal 문제는 총 0문제 존재

    }
}