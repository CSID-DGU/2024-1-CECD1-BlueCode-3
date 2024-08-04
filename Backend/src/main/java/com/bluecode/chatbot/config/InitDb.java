package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 테스트용 데이터를 DB에 저장하는 class 입니다.
 */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws InterruptedException {
        initService.userInit();
        initService.curriculumInit();
        initService.quizInit();
        initService.testInit();
        initService.test();
        initService.chatInit();
        initService.missionInit();
        initService.userMissionInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @Slf4j
    static class InitService {

        private final EntityManager em;
        private final UserRepository userRepository;
        private final CurriculumRepository curriculumRepository;
        private final QuizRepository quizRepository;
        private final TestRepository testRepository;
        private final StudyRepository studyRepository;
        private final ChatRepository chatRepository;
        private final MissionRepository missionRepository;
        private final UserMissionRepository userMissionRepository;

        public void userInit() {
            Users user1 = createUser("testName", "testEmail", "testId", "1111", "11110033", false); // 초기 테스트 미진행 유저
            Users user2 = createUser("testName2", "testEmail2", "testId2", "1111", "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작)
            em.persist(user1);
            em.persist(user2);
            em.flush();
            log.info("Users have been initialized");
        }

        public void test() {
            Users user = userRepository.findByUserId(2L).orElseThrow(() -> new NoSuchElementException("User not found with id 2"));
            Curriculums root = curriculumRepository.findById(1L).get();
            List<Curriculums> lists = curriculumRepository.findAllByParentOrderByChapterNum(root);

            List<Studies> result = studyRepository.findAllByCurriculumIdAndUserId(lists.get(3).getCurriculumId(), user.getUserId());

            for (Studies study : result) {
                log.info(study.getText());
            }

            Optional<Curriculums> res = curriculumRepository.findByRootIdAndChapterNum(root.getCurriculumId(), 7);
            log.info(res.get().getCurriculumName());
        }

        public void curriculumInit() {

            Curriculums root = createCurriculum(null, "파이썬", "", "", "", false, 0, 17);
            em.persist(root);
            em.flush();

            Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false, 1, 1);
            Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false, 2, 1);
            Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false, 3, 1);
            Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true, 4, 1);
            Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true, 5, 1);
            Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true, 6, 1);
            Curriculums chap7 = createCurriculum(root, "7. 주석,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", "", "", "주석 사용 방법(한줄, 여러줄), 주석의 역할 및 용도,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", false, 7, 1);
            Curriculums chap8 = createCurriculum(root, "8. 함수", "함수란?, 파이썬 내장 함수,함수의 매개 변수", "전역변수와 지역 변수, 사용자 정의 함수, 함수 리턴값과 None", "함수의 메모리 주소, 함수 호출 스택 구조", true, 8, 1);
            Curriculums chap9 = createCurriculum(root, "9. 문자열", "문자열 선언 방법", "문자열 슬라이싱, 서식 지정자, f-string, format, escape 문자, 문자열 출력, 문자열 입력", "다행 문자열", true, 9, 1);
            Curriculums chap10 = createCurriculum(root, "10. 조건문", "", "", "bool값, if-else문, 중첩 조건문과 삼항 연산자", false, 10, 1);
            Curriculums chap11 = createCurriculum(root, "11. 모듈화", "", "", "모듈 import, 사용자 정의 모듈", false, 11, 1);
            Curriculums chap12 = createCurriculum(root, "12. 메서드", "클래스, 메서드, 메서드 호출", "문자열 메서드", "매직 메서드", true, 12, 1);
            Curriculums chap13 = createCurriculum(root, "13. 리스트", "리스트 데이터 저장, 리스트 타입 표기, 리스트 수정", "리스트 연산, 리스트 슬라이싱", "리스트 메서드", true, 13, 1);
            Curriculums chap14 = createCurriculum(root, "14. 반복문", "for 문, while 문, 리스트를 활용한 반복문, 수 범위 순회, 인덱스 사용 리스트 처리, 중첩 반복문, 조건 반복문, 무한루프, break와 continue", "문자열 내 문자 처리", "사용자 입력에 따른 반복", true, 14, 1);
            Curriculums chap15 = createCurriculum(root, "15. 파일 처리", "", "", "with 문, 파일 정리, 특정 파일 명시, 파일 읽기, 파일 쓰기, StringIO, 다수행 레코드, 미리보기", false, 15, 1);
            Curriculums chap16 = createCurriculum(root, "16. 컬렉션", "세트,튜플,딕셔너리,컬렉션이란", "세트 연산,딕셔너리 순회,연산,도치, 컬렉션 in 연산자,컬렉션 비교", "“:”를 활용한 파이썬에서 타입 명시 방법", true, 16, 1);
            Curriculums chap17 = createCurriculum(root, "17. 객체 지향 프로그래밍", "객체지향 프로그래밍이란, 클래스란", "클래스의 생성자의 사용법", "클래스 상속, 오버라이딩,오버로딩", true, 17, 1);

            em.persist(chap1);
            em.persist(chap2);
            em.persist(chap3);
            em.persist(chap4);
            em.persist(chap5);
            em.persist(chap6);
            em.persist(chap7);
            em.persist(chap8);
            em.persist(chap9);
            em.persist(chap10);
            em.persist(chap11);
            em.persist(chap12);
            em.persist(chap13);
            em.persist(chap14);
            em.persist(chap15);
            em.persist(chap16);
            em.persist(chap17);
        }

        public void quizInit() {

            Users user = userRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("User not found with id 2"));
            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);
            List<Curriculums> lists = curriculumRepository.findAllByParentOrderByChapterNum(root);
            if (lists.size() < 6) {
                throw new NoSuchElementException("Not enough chapters found");
            }

            // 객관식
            for (int i = 0; i < lists.size(); i++) {
                Quiz quizHard1 = createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 객관식", i + 1), "1", QuizLevel.HARD, "정답1", "오답2", "오답3", "오답4", "", "", 0);
                Quiz quizHard2 = createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 객관식", i + 1), "2", QuizLevel.HARD, "오답1", "정답2", "오답3", "오답4", "", "", 0);
                Quiz quizNormal1 = createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 객관식", i + 1), "3", QuizLevel.NORMAL, "오답1", "오답2", "정답3", "오답4", "", "", 0);
                Quiz quizEasy1 = createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 객관식", i + 1), "4", QuizLevel.EASY, "오답1", "오답2", "오답3", "정답4", "", "", 0);

                em.persist(quizHard1);
                em.persist(quizHard2);
                em.persist(quizNormal1);
                em.persist(quizEasy1);
            }

            // 단답형
            for (int i = 0; i < lists.size(); i++) {
                Quiz quizHard1 = createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 단답형", i + 1), "정답", QuizLevel.HARD, "", "", "", "","","",2);
                Quiz quizHard2 = createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 단답형", i + 1), "정답", QuizLevel.HARD, "", "", "", "","","",2);
                Quiz quizNormal1 = createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 단답형", i + 1), "정답", QuizLevel.NORMAL, "", "", "", "","","",2);
                Quiz quizEasy1 = createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 단답형", i + 1), "정답", QuizLevel.EASY, "", "", "", "","","",2);

                em.persist(quizHard1);
                em.persist(quizHard2);
                em.persist(quizNormal1);
                em.persist(quizEasy1);
            }

            // 코드 작성형
            for (int i = 0; i < lists.size(); i++) {
                Quiz quizHard1 = createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 코드작성형", i + 1), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0);
                Quiz quizHard2 = createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 코드작성형", i + 1), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0);
                Quiz quizNormal1 = createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 코드작성형", i + 1), "", QuizLevel.NORMAL, "", "", "", "","1\n2","3",0);
                Quiz quizEasy1 = createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 코드작성형", i + 1), "", QuizLevel.EASY, "", "", "", "","1\n2","3",0);

                em.persist(quizHard1);
                em.persist(quizHard2);
                em.persist(quizNormal1);
                em.persist(quizEasy1);
            }
        }

        public void testInit() {

            Users user2 = userRepository.findById(2L).get();
            Curriculums root = curriculumRepository.findAllRootCurriculumList().get(0);

            List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(root);
            int testedChap = 2;
            int current = 0;
            Curriculums curriculum = chapters.get(0);

            for (int i = 0; i < chapters.size(); i++) {

                curriculum = chapters.get(i);

                if (current < testedChap) {
                    if (curriculum.isTestable()) {
                        current++;
                        List<Quiz> quizListHard = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.HARD);
                        List<Quiz> quizListNormal = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.NORMAL);
                        List<Quiz> quizListEasy = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.EASY);

                        Tests testHard1 = createTest(user2, quizListHard.get(0), 0, true, TestType.INIT);
                        Tests testHard2 = createTest(user2, quizListHard.get(1), 0, false, TestType.INIT);
                        Tests testNormal1 = createTest(user2, quizListNormal.get(0), 0, true, TestType.INIT);
                        Tests testEasy1 = createTest(user2, quizListEasy.get(0), 0, true, TestType.INIT);

                        testRepository.save(testHard1);
                        testRepository.save(testHard2);
                        testRepository.save(testNormal1);
                        testRepository.save(testEasy1);

                        Studies studiesEasy = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", i + 1), true, LevelType.EASY);
                        Studies studiesNormal = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.NORMAL + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", i + 1), true, LevelType.NORMAL);
                        Studies studiesHard = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", i + 1), true, LevelType.HARD);

                        studyRepository.save(studiesEasy);
                        studyRepository.save(studiesNormal);
                        studyRepository.save(studiesHard);
                    } else {
                        Studies studies = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", i + 1), true, LevelType.HARD);
                        studyRepository.save(studies);
                    }
                } else {
                    if (curriculum.isTestable()) {
                        List<Quiz> quizListHard = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.HARD);
                        List<Quiz> quizListNormal = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.NORMAL);
                        List<Quiz> quizListEasy = quizRepository.findAllByCurriculumIdAndQuizTypeAndLevel(curriculum.getCurriculumId(), QuizType.NUM, QuizLevel.EASY);

                        Tests testHard1 = createTest(user2, quizListHard.get(0), 2, false, TestType.INIT);
                        Tests testHard2 = createTest(user2, quizListHard.get(1), 0, false, TestType.INIT);
                        Tests testNormal1 = createTest(user2, quizListNormal.get(0), 2, false, TestType.INIT);
                        Tests testEasy1 = createTest(user2, quizListEasy.get(0), 0, true, TestType.INIT);

                        testRepository.save(testHard1);
                        testRepository.save(testHard2);
                        testRepository.save(testNormal1);
                        testRepository.save(testEasy1);

                        Studies studies = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", i + 1), false, LevelType.HARD);
                        studyRepository.save(studies);
                        break;
                    } else {
                        Studies studies = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", i + 1), true, LevelType.HARD);
                        studyRepository.save(studies);
                    }
                }
            }
            // 커리큘럼 생성 테스트용 데이터
//            Studies studies = createStudy(user2, chapters.get(16), 60L, null, false, LevelType.HARD);
//            studyRepository.save(studies);
        }

        public void chatInit() throws InterruptedException {
            Users user = userRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("User not found with id 2"));

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);

            List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(root);
            if (chapters.size() < 6) {
                throw new NoSuchElementException("Not enough chapters found");
            }

            // 챕터 4에서 질문 1개
            Chats chat = createChatWithTime(user, chapters.get(3), "챕터 4에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, LocalDateTime.now(), 1);
            chatRepository.save(chat);

            // 챕터 6에서 질문 3개
            chat = createChatWithTime(user, chapters.get(5), "챕터 6에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, LocalDateTime.now().plusMinutes(1), 1);
            chatRepository.save(chat);
            chat = createChatWithTime(user, chapters.get(5), "챕터 6에서의 질문2: 코드질문(1단계 부터 시작)", "1단계: 코드 단계적 답변\n\n2단계: 코드 단계적 답변 진행\n\n3단계: 코드 단계적 답변 진행\n\n4단계: 코드 단계적 답변 진행", QuestionType.CODE, LocalDateTime.now().plusMinutes(2), 1);
            chatRepository.save(chat);
            chat = createChatWithTime(user, chapters.get(5), "챕터 6에서의 질문3: 에러질문(3단계 까지 진행)", "1단계: 에러 단계적 답변\n\n2단계: 에러 단계적 답변 진행\n\n3단계: 단계적 답변 진행\n\n4단계: 에러 단계적 답변 진행", QuestionType.ERRORS, LocalDateTime.now().plusMinutes(3), 3);
            chatRepository.save(chat);
        }

        public void missionInit() {

            List<Missions> missions = new ArrayList<>();

            // test 일일 미션
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 1회 제출하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 2회 제출하기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 3회 제출하기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 1회 맞추기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 2회 맞추기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 3회 맞추기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 1회 맞추기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 1회 맞추기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 1회 맞추기", 1));

            // test 주간 미션
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 5회 제출하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 6회 제출하기", 6));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 7회 제출하기", 7));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 8회 제출하기", 8));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 9회 제출하기", 9));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 10회 제출하기", 10));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 5회 맞추기", 5));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 6회 맞추기", 6));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 7회 맞추기", 7));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 8회 맞추기", 8));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 9회 맞추기", 9));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 10회 맞추기", 10));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 5회 맞추기", 5));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 5회 맞추기", 5));
            missions.add(createMission(10, MissionType.WEEKLY, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 5회 맞추기", 5));

            // test 도전과제 미션
            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 1회 제출하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 2회 제출하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 4회 제출하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 8회 제출하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 16회 제출하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 32회 제출하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 64회 제출하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 128회 제출하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 256회 제출하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_SUBMIT, "아무 난이도 문제 답안 512회 제출하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 1회 제출하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 2회 제출하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 4회 제출하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 8회 제출하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 16회 제출하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 32회 제출하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 64회 제출하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 128회 제출하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 256회 제출하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_SUBMIT, "입문자 난이도 문제 답안 512회 제출하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 1회 제출하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 2회 제출하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 4회 제출하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 8회 제출하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 16회 제출하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 32회 제출하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 64회 제출하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 128회 제출하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 256회 제출하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_SUBMIT, "초급자 난이도 문제 답안 512회 제출하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 1회 제출하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 2회 제출하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 4회 제출하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 8회 제출하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 16회 제출하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 32회 제출하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 64회 제출하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 128회 제출하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 256회 제출하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_SUBMIT, "중급자 난이도 문제 답안 512회 제출하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 1회 맞추기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 2회 맞추기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 4회 맞추기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 8회 맞추기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 16회 맞추기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 32회 맞추기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 64회 맞추기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 128회 맞추기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 256회 맞추기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_PASS, "아무 난이도 문제 답안 512회 맞추기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 1회 맞추기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 2회 맞추기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 4회 맞추기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 8회 맞추기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 16회 맞추기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 32회 맞추기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 64회 맞추기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 128회 맞추기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 256회 맞추기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_EASY_PASS, "입문자 난이도 문제 답안 512회 맞추기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 1회 맞추기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 2회 맞추기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 4회 맞추기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 8회 맞추기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 16회 맞추기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 32회 맞추기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 64회 맞추기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 128회 맞추기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 256회 맞추기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_NORMAL_PASS, "초급자 난이도 문제 답안 512회 맞추기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 1회 맞추기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 2회 맞추기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 4회 맞추기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 8회 맞추기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 16회 맞추기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 32회 맞추기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 64회 맞추기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 128회 맞추기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 256회 맞추기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_HARD_PASS, "중급자 난이도 문제 답안 512회 맞추기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 1회 틀리기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 2회 틀리기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 4회 틀리기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 8회 틀리기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 16회 틀리기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 32회 틀리기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 64회 틀리기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 128회 틀리기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 256회 틀리기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_FAIL, "아무 난이도 문제 답안 512회 틀리기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE, "초기 테스트 1회 수행 완료하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE, "초기 테스트 2회 수행 완료하기", 2));
            missions.add(createMission(30, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE, "초기 테스트 3회 수행 완료하기", 3));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE, "초기 테스트 4회 수행 완료하기", 4));
            missions.add(createMission(50, MissionType.CHALLENGE, ServiceType.TEST, MissionConst.TEST_INIT_COMPLETE, "초기 테스트 5회 수행 완료하기", 5));

            // study 일일 미션
            missions.add(createMission(10, MissionType.DAILY, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 1회 학습 완료하기", 1));

            // study 주간 미션
            missions.add(createMission(50, MissionType.WEEKLY, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 5회 학습 완료하기", 5));

            // study 도전과제 미션
            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 1회 학습 완료하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 2회 학습 완료하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 4회 학습 완료하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 8회 학습 완료하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 16회 학습 완료하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 32회 학습 완료하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 64회 학습 완료하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 128회 학습 완료하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 256회 학습 완료하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.STUDY_COMPLETE, "커리큘럼 챕터 512회 학습 완료하기", 512));

            List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();

            for (Curriculums root : roots) {

                missions.add(createMission(1000, MissionType.CHALLENGE, ServiceType.STUDY, "STUDY_" + root.getCurriculumName().toUpperCase() +"_COMPLETE", "\"" + root.getCurriculumName() + "\" 내 모든 챕터 학습 완료하기", 1));

                List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(root);

                for (Curriculums chapter : chapters) {
                    missions.add(createMission(100, MissionType.CHALLENGE, ServiceType.STUDY, "STUDY_" + root.getCurriculumName().toUpperCase() + "_" + chapter.getChapterNum() + "_COMPLETE", "\"" + root.getCurriculumName() + ": " + chapter.getCurriculumName() + "\" 학습 통과하기", 1));
                }
            }



            // User 일일 미션
            missions.add(createMission(10, MissionType.DAILY, ServiceType.USER, MissionConst.USER_LOGIN, "1일 로그인하기", 1));

            // User 주간 미션
            missions.add(createMission(50, MissionType.WEEKLY, ServiceType.USER, MissionConst.USER_LOGIN, "7일 로그인하기", 7));

            // User 도전과제 미션
            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "1일 연속 로그인하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "2일 연속 로그인하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "4일 연속 로그인하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "8일 연속 로그인하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "16일 연속 로그인하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "32일 연속 로그인하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "64일 연속 로그인하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "128일 연속 로그인하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "256일 연속 로그인하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.USER, MissionConst.USER_STREAK, "512일 연속 로그인하기", 512));

            // mission 도전과제 미션
            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 1회 완료하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 2회 완료하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 4회 완료하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 8회 완료하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 16회 완료하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 32회 완료하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 64회 완료하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 128회 완료하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 256회 완료하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_DAILY_COMPLETE, "일일 미션 전체 512회 완료하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 1회 완료하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 2회 완료하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 4회 완료하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 8회 완료하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 16회 완료하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 32회 완료하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 64회 완료하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 128회 완료하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 256회 완료하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.MISSION, MissionConst.MISSION_WEEKLY_COMPLETE, "주간 미션 전체 512회 완료하기", 512));

            // chat 일일 미션
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 1회 수행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 2회 수행하기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 3회 수행하기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 1회 수행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 2회 수행하기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 3회 수행하기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 1회 수행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 2회 수행하기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 3회 수행하기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 1회 수행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 2회 수행하기", 2));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 3회 수행하기", 3));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 1회 진행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 1회 진행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 1회 진행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 1회 진행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 1회 진행하기", 1));
            missions.add(createMission(10, MissionType.DAILY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 1회 진행하기", 1));


            // chat 주간 미션
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 1회 수행하기", 1));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 2회 수행하기", 2));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 3회 수행하기", 3));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 4회 수행하기", 4));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 5회 수행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 5회 수행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 6회 수행하기", 6));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 7회 수행하기", 7));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 8회 수행하기", 8));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 9회 수행하기", 9));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 10회 수행하기", 10));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 5회 수행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 6회 수행하기", 6));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 7회 수행하기", 7));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 8회 수행하기", 8));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 9회 수행하기", 9));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 10회 수행하기", 10));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 5회 수행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 6회 수행하기", 6));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 7회 수행하기", 7));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 8회 수행하기", 8));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 9회 수행하기", 9));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 10회 수행하기", 10));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 5회 진행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 5회 진행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 5회 진행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 5회 진행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 5회 진행하기", 5));
            missions.add(createMission(100, MissionType.WEEKLY, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 5회 진행하기", 5));

            // chat 도전과제
            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 1회 수행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 2회 수행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 4회 수행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 8회 수행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 16회 수행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 32회 수행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 64회 수행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 128회 수행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 256회 수행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_QUESTION, "질문 512회 수행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 1회 수행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 2회 수행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 4회 수행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 8회 수행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 16회 수행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 32회 수행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 64회 수행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 128회 수행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 256회 수행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_DEF_QUESTION, "개념 태그 질문 512회 수행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 1회 수행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 2회 수행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 4회 수행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 8회 수행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 16회 수행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 32회 수행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 64회 수행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 128회 수행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 256회 수행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_CODE_QUESTION, "코드 태그 질문 512회 수행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 1회 수행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 2회 수행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 4회 수행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 8회 수행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 16회 수행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 32회 수행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 64회 수행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 128회 수행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 256회 수행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.CHAT_ERRORS_QUESTION, "오류 태그 질문 512회 수행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 5회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 5회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 2), "코드 태그 질문 2단계까지 512회 진행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 5회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 5회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 3), "코드 태그 질문 3단계까지 512회 진행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 5회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 5회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.CODE, 4), "코드 태그 질문 4단계까지 512회 진행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 2회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 4회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 2), "오류 태그 질문 2단계까지 512회 진행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 2회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 4회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 3), "오류 태그 질문 3단계까지 512회 진행하기", 512));

            missions.add(createMission(10, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 1회 진행하기", 1));
            missions.add(createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 2회 진행하기", 2));
            missions.add(createMission(40, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 4회 진행하기", 4));
            missions.add(createMission(80, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 8회 진행하기", 8));
            missions.add(createMission(160, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 16회 진행하기", 16));
            missions.add(createMission(320, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 32회 진행하기", 32));
            missions.add(createMission(640, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 64회 진행하기", 64));
            missions.add(createMission(1280, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 128회 진행하기", 128));
            missions.add(createMission(2560, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 256회 진행하기", 256));
            missions.add(createMission(5120, MissionType.CHALLENGE, ServiceType.CHAT, MissionConst.createConstByQuestionTypeAndLevel(QuestionType.ERRORS, 4), "오류 태그 질문 4단계까지 512회 진행하기", 512));

            missionRepository.saveAll(missions);
        }

        public void userMissionInit() {

            Users user = userRepository.findById(2L).get();

            List<Missions> dailyMission = missionRepository.findAllByMissionType(MissionType.DAILY);
            List<Missions> weeklyMission = missionRepository.findAllByMissionType(MissionType.WEEKLY);
            List<Missions> challengeMission = missionRepository.findAllByMissionType(MissionType.CHALLENGE);

            Collections.shuffle(dailyMission, new SecureRandom());
            Collections.shuffle(weeklyMission, new SecureRandom());

            for (int i = 0; i < 3; i++) {
                UserMissions userMission = createUserMission(
                                                user,
                                                LocalDate.now(),
                                                LocalDate.now(),
                                                dailyMission.get(i),
                                                0,
                                                MissionStatus.PROGRESS);
                userMissionRepository.save(userMission);
            }

            for (int i = 0; i < 3; i++) {
                UserMissions userMission = createUserMission(
                        user,
                        LocalDate.now(),
                        LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)),
                        weeklyMission.get(i),
                        0,
                        MissionStatus.PROGRESS);
                userMissionRepository.save(userMission);
            }

            for (Missions missions : challengeMission) {
                UserMissions userMission = createUserMission(
                        user,
                        LocalDate.now(),
                        null,
                        missions,
                        0,
                        MissionStatus.PROGRESS);
                userMissionRepository.save(userMission);
            }
        }

        private Missions createMission(
                int exp,
                MissionType missionType,
                ServiceType serviceType,
                String actionType,
                String text,
                int missionCount
        ) {
            Missions mission = new Missions();
            mission.setExp(exp);
            mission.setMissionType(missionType);
            mission.setServiceType(serviceType);
            mission.setActionType(actionType);
            mission.setText(text);
            mission.setMissionCount(missionCount);

            return mission;
        }

        private UserMissions createUserMission(
                Users user,
                LocalDate startDate,
                LocalDate endDate,
                Missions mission,
                int currentCount,
                MissionStatus missionStatus
        ) {
            UserMissions userMission = new UserMissions();
            userMission.setUser(user);
            userMission.setStartDate(startDate);
            userMission.setEndDate(endDate);
            userMission.setMission(mission);
            userMission.setCurrentCount(currentCount);
            userMission.setMissionStatus(missionStatus);

            return userMission;
        }

        private Curriculums createCurriculum(
                Curriculums parent,
                String curriculumName,
                String keywordEasy,
                String keywordNormal,
                String keywordHard,
                boolean testable,
                int chapterNum,
                int totalChapterCount
        ) {
            Curriculums curriculums = new Curriculums();
            curriculums.setCurriculumName(curriculumName);
            curriculums.setParent(parent);
            curriculums.setKeywordEasy(keywordEasy);
            curriculums.setKeywordNormal(keywordNormal);
            curriculums.setKeywordHard(keywordHard);
            curriculums.setTestable(testable);
            curriculums.setChapterNum(chapterNum);
            curriculums.setTotalChapterCount(totalChapterCount);

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

        private Quiz createQuiz(
                Curriculums curriculum,
                QuizType quizType,
                String text,
                String answer,
                QuizLevel level,
                String q1,
                String q2,
                String q3,
                String q4,
                String inputs,
                String outputs,
                int wordCount
        ) {
            Quiz quiz = new Quiz();
            quiz.setCurriculum(curriculum);
            quiz.setQuizType(quizType);
            quiz.setText(text);
            quiz.setAnswer(answer);
            quiz.setLevel(level);
            quiz.setQ1(q1);
            quiz.setQ2(q2);
            quiz.setQ3(q3);
            quiz.setQ4(q4);
            quiz.setInputs(inputs);
            quiz.setOutputs(outputs);
            quiz.setWordCount(wordCount);

            return quiz;
        }

        private Tests createTest(
                Users user,
                Quiz quiz,
                int wrongCount,
                boolean passed,
                TestType testType
        ) {
            Tests test = new Tests();
            test.setUser(user);
            test.setQuiz(quiz);
            test.setWrongCount(wrongCount);
            test.setPassed(passed);
            test.setTestType(testType);

            return test;
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

        private Chats createChatWithTime(Users user,
                                 Curriculums curriculum,
                                 String question,
                                 String answer,
                                 QuestionType questionType,
                                 LocalDateTime time,
                                 int level) {
            Chats chat = new Chats();
            chat.setUser(user);
            chat.setCurriculum(curriculum);
            chat.setQuestion(question);
            chat.setAnswer(answer);
            chat.setQuestionType(questionType);
            chat.setLevel(level);
            chat.setChatDate(time);

            return chat;
        }
    }
}
