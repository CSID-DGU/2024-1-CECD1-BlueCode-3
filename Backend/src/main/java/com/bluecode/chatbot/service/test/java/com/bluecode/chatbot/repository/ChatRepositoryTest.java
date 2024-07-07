package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.QuestionType;
import com.bluecode.chatbot.domain.Users;
import jakarta.persistence.EntityManager;
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
class ChatRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CurriculumRepository curriculumRepository;
    @Autowired private ChatRepository chatRepository;
    @Autowired private EntityManager em;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        curriculumRepository.deleteAll();
        chatRepository.deleteAll();
        em.clear();
    }

    @Test
    @DisplayName("findAllByUserIdAndChapterIdOrderByChatDate 쿼리 테스트")
    public void findAllByUserIdAndChapterIdOrderByChatDate() throws Exception {
        //given

        // 유저
        Users user = createUser("testName2", "testEmail2", "testId2", "1111", "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작)
        userRepository.save(user);

        // 루트 커리큘럼
        Curriculums root = createCurriculum(null, "파이썬", "", "", "", false, 0);
        em.persist(root);
        em.flush();

        // 챕터 커리큘럼
        Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false, 1);
        Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false, 2);
        Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false, 3);
        Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true, 4);
        Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true, 5);
        Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true, 6);

        em.persist(chap1);
        em.persist(chap2);
        em.persist(chap3);
        em.persist(chap4);
        em.persist(chap5);
        em.persist(chap6);

        // 질문
        // 챕터 4에서 질문 1개
        List<Chats> giveChap4 = new ArrayList<>();

        Chats chatChap4 = createChat(user, chap4, "챕터 4에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        giveChap4.add(chatChap4);
        chatRepository.save(chatChap4);
        Thread.sleep(1000); // 생성 시간 구분을 위한 1초 대기

        // 챕터 6에서 질문 3개
        List<Chats> giveChap6 = new ArrayList<>();

        Chats chatChap6Def = createChat(user, chap6, "챕터 6에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        giveChap6.add(chatChap6Def);
        chatRepository.save(chatChap6Def);
        Thread.sleep(1000);
        Chats chatChap6Code = createChat(user, chap6, "챕터 6에서의 질문2: 코드질문(1단계 부터 시작)", "단계적 답변 진행도 1단계, 단계적 답변 진행도 2단계, 단계적 답변 진행도 3단계, 단계적 답변 진행도 4단계", QuestionType.CODE, 1);
        giveChap6.add(chatChap6Code);
        chatRepository.save(chatChap6Code);
        Thread.sleep(1000);
        Chats chatChap6Error = createChat(user, chap6, "챕터 6에서의 질문3: 에러질문(3단계 까지 진행)", "에러 단계적 답변 진행도 1단계, 에러 단계적 답변 진행도 2단계, 에러 단계적 답변 진행도 3단계, 에러 단계적 답변 진행도 4단계", QuestionType.ERRORS, 3);
        giveChap6.add(chatChap6Error);
        chatRepository.save(chatChap6Error);

        //when
        List<Chats> resChap1 = chatRepository.findAllByUserIdAndChapterIdOrderByChatDate(user.getUserId(), chap1.getCurriculumId());
        List<Chats> resChap4 = chatRepository.findAllByUserIdAndChapterIdOrderByChatDate(user.getUserId(), chap4.getCurriculumId());
        List<Chats> resChap6 = chatRepository.findAllByUserIdAndChapterIdOrderByChatDate(user.getUserId(), chap6.getCurriculumId());

        //then

        // 챕터1에서의 chat은 없어야 한다.
        Assertions.assertThat(resChap1.size()).isEqualTo(0);

        // 챕터4에서의 chat은 1개 존재한다.
        Assertions.assertThat(resChap4.size()).isEqualTo(1);
        for (int i = 0; i < resChap4.size(); i++) {
            Assertions.assertThat(resChap4.get(i)).isEqualTo(giveChap4.get(i));
        }

        // 챕터6에서의 chat은 3개 존재한다.
        Assertions.assertThat(resChap6.size()).isEqualTo(3);
        for (int i = 0; i < resChap6.size(); i++) {
            Assertions.assertThat(resChap6.get(i)).isEqualTo(giveChap6.get(i));
        }
    }

    @Test
    @DisplayName("findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate 쿼리 테스트")
    public void findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate() throws Exception {
        //given

        // 유저
        Users user = createUser("testName2", "testEmail2", "testId2", "1111", "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작)
        userRepository.save(user);

        // 루트 커리큘럼
        Curriculums root = createCurriculum(null, "파이썬", "", "", "", false, 0);
        em.persist(root);
        em.flush();

        // 챕터 커리큘럼
        Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false, 1);
        Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false, 2);
        Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false, 3);
        Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true, 4);
        Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true, 5);
        Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true, 6);

        em.persist(chap1);
        em.persist(chap2);
        em.persist(chap3);
        em.persist(chap4);
        em.persist(chap5);
        em.persist(chap6);

        // 질문
        // 챕터 4에서 질문 1개
        List<Chats> giveChap4 = new ArrayList<>();
        Chats chatChap4Def = createChat(user, chap4, "챕터 4에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        giveChap4.add(chatChap4Def);
        chatRepository.save(chatChap4Def);
        Thread.sleep(1000); // 생성 시간 구분을 위한 1초 대기

        // 챕터 6에서 질문 3개
        List<Chats> giveChap6Def = new ArrayList<>();
        List<Chats> giveChap6Code = new ArrayList<>();
        List<Chats> giveChap6Error = new ArrayList<>();

        Chats chatChap6Def = createChat(user, chap6, "챕터 6에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        giveChap6Def.add(chatChap6Def);
        chatRepository.save(chatChap6Def);
        Thread.sleep(1000);
        Chats chatChap6Code = createChat(user, chap6, "챕터 6에서의 질문2: 코드질문(1단계 부터 시작)", "단계적 답변 진행도 1단계, 단계적 답변 진행도 2단계, 단계적 답변 진행도 3단계, 단계적 답변 진행도 4단계", QuestionType.CODE, 1);
        giveChap6Code.add(chatChap6Code);
        chatRepository.save(chatChap6Code);
        Thread.sleep(1000);
        Chats chatChap6Error = createChat(user, chap6, "챕터 6에서의 질문3: 에러질문(3단계 까지 진행)", "에러 단계적 답변 진행도 1단계, 에러 단계적 답변 진행도 2단계, 에러 단계적 답변 진행도 3단계, 에러 단계적 답변 진행도 4단계", QuestionType.ERRORS, 3);
        giveChap6Error.add(chatChap6Error);
        chatRepository.save(chatChap6Error);

        //when
        // 1챕터
        List<Chats> resChap1Def = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap1.getCurriculumId(), QuestionType.DEF);

        // 4챕터
        List<Chats> resChap4Def = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap4.getCurriculumId(), QuestionType.DEF);
        List<Chats> resChap4Code = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap4.getCurriculumId(), QuestionType.CODE);
        List<Chats> resChap4Error = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap4.getCurriculumId(), QuestionType.ERRORS);

        // 6챕터
        List<Chats> resChap6Def = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap6.getCurriculumId(), QuestionType.DEF);
        List<Chats> resChap6Code = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap6.getCurriculumId(), QuestionType.CODE);
        List<Chats> resChap6Error = chatRepository.findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(user.getUserId(), chap6.getCurriculumId(), QuestionType.ERRORS);

        //then
        // 1챕터에서 Def 인 chat은 없다
        Assertions.assertThat(resChap1Def.size()).isEqualTo(0);

        // 4챕터에서 Def인 chat만 존재한다.
        Assertions.assertThat(resChap4Def.size()).isEqualTo(1);
        for (int i = 0; i < resChap4Def.size(); i++) {
            Assertions.assertThat(resChap4Def.get(i)).isEqualTo(giveChap4.get(i));
        }
        Assertions.assertThat(resChap4Code.size()).isEqualTo(0);
        Assertions.assertThat(resChap4Error.size()).isEqualTo(0);

        // 6챕터에서 각 유형별 chat이 1개씩 존재한다.
        Assertions.assertThat(resChap6Def.size()).isEqualTo(1);
        Assertions.assertThat(resChap6Code.size()).isEqualTo(1);
        Assertions.assertThat(resChap6Error.size()).isEqualTo(1);

        for (int i = 0; i < resChap6Def.size(); i++) {
            Assertions.assertThat(resChap6Def.get(i)).isEqualTo(giveChap6Def.get(i));
        }

        for (int i = 0; i < resChap6Code.size(); i++) {
            Assertions.assertThat(resChap6Code.get(i)).isEqualTo(giveChap6Code.get(i));
        }

        for (int i = 0; i < resChap6Error.size(); i++) {
            Assertions.assertThat(resChap6Error.get(i)).isEqualTo(giveChap6Error.get(i));
        }
    }

    @Test
    @DisplayName("findAllByUserIdAndParentIdOrderByChapterNumAndChatDate 쿼리 테스트")
    public void findAllByUserIdAndParentIdOrderByChapterNumAndChatDate() throws Exception {
        //given

        // 유저
        Users user = createUser("testName2", "testEmail2", "testId2", "1111", "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작)
        userRepository.save(user);

        // 루트 커리큘럼
        Curriculums root = createCurriculum(null, "파이썬", "", "", "", false, 0);
        em.persist(root);
        em.flush();

        // 챕터 커리큘럼
        Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false, 1);
        Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false, 2);
        Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false, 3);
        Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true, 4);
        Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true, 5);
        Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true, 6);

        em.persist(chap1);
        em.persist(chap2);
        em.persist(chap3);
        em.persist(chap4);
        em.persist(chap5);
        em.persist(chap6);

        // 질문
        List<Chats> give = new ArrayList<>();
        // 챕터 4에서 질문 1개

        Chats chatChap4Def = createChat(user, chap4, "챕터 4에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        give.add(chatChap4Def);
        chatRepository.save(chatChap4Def);
        Thread.sleep(1000); // 생성 시간 구분을 위한 1초 대기

        // 챕터 6에서 질문 3개

        Chats chatChap6Def = createChat(user, chap6, "챕터 6에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, 1);
        give.add(chatChap6Def);
        chatRepository.save(chatChap6Def);
        Thread.sleep(1000);
        Chats chatChap6Code = createChat(user, chap6, "챕터 6에서의 질문2: 코드질문(1단계 부터 시작)", "단계적 답변 진행도 1단계, 단계적 답변 진행도 2단계, 단계적 답변 진행도 3단계, 단계적 답변 진행도 4단계", QuestionType.CODE, 1);
        give.add(chatChap6Code);
        chatRepository.save(chatChap6Code);
        Thread.sleep(1000);
        Chats chatChap6Error = createChat(user, chap6, "챕터 6에서의 질문3: 에러질문(3단계 까지 진행)", "에러 단계적 답변 진행도 1단계, 에러 단계적 답변 진행도 2단계, 에러 단계적 답변 진행도 3단계, 에러 단계적 답변 진행도 4단계", QuestionType.ERRORS, 3);
        give.add(chatChap6Error);
        chatRepository.save(chatChap6Error);

        //when
        List<Chats> resAll = chatRepository.findAllByUserIdAndParentIdOrderByChapterNumAndChatDate(user.getUserId(), root.getCurriculumId());

        //then
        // 촐 chat의 수는 4개이다.
        Assertions.assertThat(resAll.size()).isEqualTo(4);

        for (int i = 0; i < resAll.size(); i++) {
            Assertions.assertThat(resAll.get(i)).isEqualTo(give.get(i));
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

    private Users createUser(
            String username,
            String email,
            String id,
            String password,
            String birth,
            boolean initTest) {

        Users user = new Users();
        user.setUsername(username);
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirth(birth);
        user.setInitTest(initTest);

        return user;
    }

    private Chats createChat(Users user,
                             Curriculums curriculum,
                             String question,
                             String answer,
                             QuestionType questionType,
                             int level) {
        Chats chat = new Chats();
        chat.setUser(user);
        chat.setCurriculum(curriculum);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chat.setQuestionType(questionType);
        chat.setLevel(level);

        return chat;
    }
}