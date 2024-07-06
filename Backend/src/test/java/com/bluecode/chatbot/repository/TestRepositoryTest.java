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
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class TestRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private QuizRepository quizRepository;
    @Autowired private CurriculumRepository curriculumRepository;
    @Autowired private TestRepository testRepository;

    @Test
    @DisplayName("findByUserIdAndQuizId 쿼리 테스트")
    public void findByUserIdAndQuizId() throws Exception {
        //given
        Users user = userRepository.findById(2L).get();
        Users user2 = Users.createUser("userX", "userX@email.com", "userXid", "1234", "22221133", true);
        userRepository.save(user2);

        List<Curriculums> root = curriculumRepository.findAllRootCurriculumList();
        List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(root.get(0));
        int chapNum = 4;

        List<Quiz> quiz = quizRepository.findByCurriculumIdAndLevel(chapters.get(chapNum - 1).getCurriculumId(), QuizLevel.HARD);

        //when
        Optional<Tests> result = testRepository.findByUserIdAndQuizId(user.getUserId(), quiz.get(0).getQuizId());
        Optional<Tests> result2 = testRepository.findByUserIdAndQuizId(user2.getUserId(), quiz.get(0).getQuizId());
        
        //then
        Assertions.assertThat(result.isPresent()).isEqualTo(true);
        Assertions.assertThat(result2.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("findAllByUserId 쿼리 테스트")
    public void findAllByUserId() throws Exception {
        //given
        Optional<Users> user = userRepository.findByUserId(2L);
        Users user2 = Users.createUser("userX", "userX@email.com", "userXid", "1234", "22221133", true);
        userRepository.save(user2);

        //when
        List<Tests> result = testRepository.findAllByUserId(user.get().getUserId());
        List<Tests> result2 = testRepository.findAllByUserId(user2.getUserId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(12);
        Assertions.assertThat(result2.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findAllByUserIdAndTestType 쿼리 테스트")
    public void findAllByUserIdAndTestType() throws Exception {
        //given
        Optional<Users> user = userRepository.findByUserId(2L);

        //when
        List<Tests> initTest = testRepository.findAllByUserIdAndTestType(user.get().getUserId(), TestType.INIT);
        List<Tests> normalTest = testRepository.findAllByUserIdAndTestType(user.get().getUserId(), TestType.NORMAL);

        //then
        Assertions.assertThat(initTest.size()).isEqualTo(12);
        Assertions.assertThat(normalTest.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findAllByUserIdAndCurriculumId 쿼리 테스트")
    public void findAllByUserIdAndCurriculumId() throws Exception {
        //given
        Optional<Users> user = userRepository.findByUserId(2L);
        Users user2 = Users.createUser("userX", "userX@email.com", "userXid", "1234", "22221133", true);
        userRepository.save(user2);

        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Tests> result = testRepository.findAllByUserIdAndCurriculumId(user.get().getUserId(), chapters.get(4).getCurriculumId());
        List<Tests> result2 = testRepository.findAllByUserIdAndCurriculumId(user.get().getUserId(), chapters.get(8).getCurriculumId());
        List<Tests> result3 = testRepository.findAllByUserIdAndCurriculumId(user2.getUserId(), chapters.get(4).getCurriculumId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(4);
        Assertions.assertThat(result2.size()).isEqualTo(0);
        Assertions.assertThat(result3.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("findByUserIdAndCurriculumIdAndQuizTypeAndLevel 쿼리 테스트")
    public void findByUserIdAndCurriculumIdAndQuizTypeAndLevel() throws Exception {
        //given
        Optional<Users> user = userRepository.findByUserId(2L);
        Users user2 = Users.createUser("userX", "userX@email.com", "userXid", "1234", "22221133", true);
        userRepository.save(user2);

        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();
        List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(roots.get(0));

        //when
        List<Tests> result = testRepository.findByUserIdAndCurriculumIdAndQuizTypeAndLevel(user.get().getUserId(), chapters.get(4).getCurriculumId(), QuizType.NUM, QuizLevel.HARD);
        List<Tests> result2 = testRepository.findByUserIdAndCurriculumIdAndQuizTypeAndLevel(user.get().getUserId(), chapters.get(4).getCurriculumId(), QuizType.CODE, QuizLevel.HARD);
        List<Tests> result3 = testRepository.findByUserIdAndCurriculumIdAndQuizTypeAndLevel(user.get().getUserId(), chapters.get(8).getCurriculumId(), QuizType.NUM, QuizLevel.HARD);

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result2.size()).isEqualTo(0);
        Assertions.assertThat(result3.size()).isEqualTo(0);
    }
}