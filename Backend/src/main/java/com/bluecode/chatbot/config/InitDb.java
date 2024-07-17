package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        }

        public void test() {

            Users user = userRepository.findByUserId(2L).get();
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

            Curriculums root = createCurriculum(null, "파이썬", "", "", "", false, 0);
            em.persist(root);
            em.flush();

            Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false, 1);
            Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false, 2);
            Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false, 3);
            Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true, 4);
            Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true, 5);
            Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true, 6);
            Curriculums chap7 = createCurriculum(root, "7. 주석,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", "", "", "주석 사용 방법(한줄, 여러줄), 주석의 역할 및 용도,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", false, 7);
            Curriculums chap8 = createCurriculum(root, "8. 함수", "함수란?, 파이썬 내장 함수,함수의 매개 변수", "전역변수와 지역 변수, 사용자 정의 함수, 함수 리턴값과 None", "함수의 메모리 주소, 함수 호출 스택 구조", true, 8);
            Curriculums chap9 = createCurriculum(root, "9. 문자열", "문자열 선언 방법", "문자열 슬라이싱, 서식 지정자, f-string, format, escape 문자, 문자열 출력, 문자열 입력", "다행 문자열", true, 9);
            Curriculums chap10 = createCurriculum(root, "10. 조건문", "", "", "bool값, if-else문, 중첩 조건문과 삼항 연산자", false, 10);
            Curriculums chap11 = createCurriculum(root, "11. 모듈화", "", "", "모듈 import, 사용자 정의 모듈", false, 11);
            Curriculums chap12 = createCurriculum(root, "12. 메서드", "클래스, 메서드, 메서드 호출", "문자열 메서드", "매직 메서드", true, 12);
            Curriculums chap13 = createCurriculum(root, "13. 리스트", "리스트 데이터 저장, 리스트 타입 표기, 리스트 수정", "리스트 연산, 리스트 슬라이싱", "리스트 메서드", true, 13);
            Curriculums chap14 = createCurriculum(root, "14. 반복문", "for 문, while 문, 리스트를 활용한 반복문, 수 범위 순회, 인덱스 사용 리스트 처리, 중첩 반복문, 조건 반복문, 무한루프, break와 continue", "문자열 내 문자 처리", "사용자 입력에 따른 반복", true, 14);
            Curriculums chap15 = createCurriculum(root, "15. 파일 처리", "", "", "with 문, 파일 정리, 특정 파일 명시, 파일 읽기, 파일 쓰기, StringIO, 다수행 레코드, 미리보기", false, 15);
            Curriculums chap16 = createCurriculum(root, "16. 컬렉션", "세트,튜플,딕셔너리,컬렉션이란", "세트 연산,딕셔너리 순회,연산,도치, 컬렉션 in 연산자,컬렉션 비교", "“:”를 활용한 파이썬에서 타입 명시 방법", true, 16);
            Curriculums chap17 = createCurriculum(root, "17. 객체 지향 프로그래밍", "객체지향 프로그래밍이란, 클래스란", "클래스의 생성자의 사용법", "클래스 상속, 오버라이딩,오버로딩", true, 17);

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

            Curriculums root = curriculumRepository.findById(1L).get();
            List<Curriculums> lists = curriculumRepository.findAllByParentOrderByChapterNum(root);

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
                        Tests testHard2 = createTest(user2, quizListHard.get(1), 0, true, TestType.INIT);
                        Tests testNormal1 = createTest(user2, quizListNormal.get(0), 0, true, TestType.INIT);
                        Tests testEasy1 = createTest(user2, quizListEasy.get(0), 0, true, TestType.INIT);

                        testRepository.save(testHard1);
                        testRepository.save(testHard2);
                        testRepository.save(testNormal1);
                        testRepository.save(testEasy1);

                        Studies studies = createStudy(user2, curriculum, 60L + i + 1, String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", i + 1), true, LevelType.HARD);
                        studyRepository.save(studies);
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
        }

        public void chatInit() throws InterruptedException {

            Users user = userRepository.findById(2L).get();

            Curriculums root = curriculumRepository.findAllRootCurriculumList().get(0);
            List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(root);

            // 챕터 4에서 질문 1개
            Chats chat = createChat(user, chapters.get(3), "챕터 4에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, 1);
            chatRepository.save(chat);
            Thread.sleep(1000); // 생성 시간 구분을 위한 1초 대기

            // 챕터 6에서 질문 3개
            chat = createChat(user, chapters.get(5), "챕터 6에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, 1);
            chatRepository.save(chat);
            Thread.sleep(1000);
            chat = createChat(user, chapters.get(5), "챕터 6에서의 질문2: 코드질문(1단계 부터 시작)", "단계적 답변 진행도 1단계, 단계적 답변 진행도 2단계, 단계적 답변 진행도 3단계, 단계적 답변 진행도 4단계", QuestionType.CODE, 1);
            chatRepository.save(chat);
            Thread.sleep(1000);
            chat = createChat(user, chapters.get(5), "챕터 6에서의 질문3: 에러질문(3단계 까지 진행)", "에러 단계적 답변 진행도 1단계, 에러 단계적 답변 진행도 2단계, 에러 단계적 답변 진행도 3단계, 에러 단계적 답변 진행도 4단계", QuestionType.ERRORS, 3);
            chatRepository.save(chat);
        }

        public void missionInit() {

            List<Missions> missions = new ArrayList<>();

            Missions dailyMission1 = createMission(10, MissionType.DAILY, ServiceType.CHAT, "CHAT_SUBMIT", "chat 일일 미션: 1회 달성 목표", 1);
            Missions dailyMission2 = createMission(11, MissionType.DAILY, ServiceType.STUDY, "STUDY_COMPLETE", "study 일일 미션: 2회 달성 목표", 2);
            Missions dailyMission3 = createMission(12, MissionType.DAILY, ServiceType.TEST, "TEST_SUBMIT", "test 일일 미션: 3회 제출 목표", 3);
            Missions dailyMission4 = createMission(13, MissionType.DAILY, ServiceType.TEST, "TEST_SUBMIT", "test 일일 미션: 1회 제출 목표", 1);
            Missions dailyMission5 = createMission(14, MissionType.DAILY, ServiceType.TEST, "TEST_PASS", "test 일일 미션: 2회 정답 제출 목표", 2);
            Missions dailyMission6 = createMission(15, MissionType.DAILY, ServiceType.TEST, "TEST_FAIL", "test 일일 미션: 1회 오답 제출 목표", 1);

            missions.add(dailyMission1);
            missions.add(dailyMission2);
            missions.add(dailyMission3);
            missions.add(dailyMission4);
            missions.add(dailyMission5);
            missions.add(dailyMission6);

            Missions weeklyMission1 = createMission(20, MissionType.WEEKLY, ServiceType.CHAT, "CHAT_SUBMIT", "chat 주간 미션: 1회 달성 목표", 1);
            Missions weeklyMission2 = createMission(21, MissionType.WEEKLY, ServiceType.STUDY, "STUDY_COMPLETE", "study 주간 미션: 2회 달성 목표", 2);
            Missions weeklyMission3 = createMission(22, MissionType.WEEKLY, ServiceType.TEST, "TEST_SUBMIT", "test 주간 미션: 3회 제출 목표", 3);
            Missions weeklyMission4 = createMission(23, MissionType.WEEKLY, ServiceType.TEST, "TEST_SUBMIT", "test 주간 미션: 1회 제출 목표", 1);
            Missions weeklyMission5 = createMission(24, MissionType.WEEKLY, ServiceType.TEST, "TEST_PASS", "test 주간 미션: 2회 정답 제출 목표", 2);
            Missions weeklyMission6 = createMission(25, MissionType.WEEKLY, ServiceType.TEST, "TEST_FAIL", "test 주간 미션: 1회 오답 제출 목표", 1);

            missions.add(weeklyMission1);
            missions.add(weeklyMission2);
            missions.add(weeklyMission3);
            missions.add(weeklyMission4);
            missions.add(weeklyMission5);
            missions.add(weeklyMission6);

            Missions challengeMission1 = createMission(20, MissionType.CHALLENGE, ServiceType.CHAT, "CHAT_SUBMIT", "chat 도전 과제 미션: 1회 달성 목표", 1);
            Missions challengeMission2 = createMission(21, MissionType.CHALLENGE, ServiceType.STUDY, "STUDY_COMPLETE", "study 도전 과제 미션: 2회 달성 목표", 2);
            Missions challengeMission3 = createMission(22, MissionType.CHALLENGE, ServiceType.TEST, "TEST_SUBMIT", "test 도전 과제 미션: 3회 제출 목표", 3);
            Missions challengeMission4 = createMission(23, MissionType.CHALLENGE, ServiceType.TEST, "TEST_SUBMIT", "test 도전 과제 미션: 1회 제출 목표", 1);
            Missions challengeMission5 = createMission(24, MissionType.CHALLENGE, ServiceType.TEST, "TEST_PASS", "test 도전 과제 미션: 2회 정답 제출 목표", 2);
            Missions challengeMission6 = createMission(25, MissionType.CHALLENGE, ServiceType.TEST, "TEST_FAIL", "test 도전 과제 미션: 1회 오답 제출 목표", 1);

            missions.add(challengeMission1);
            missions.add(challengeMission2);
            missions.add(challengeMission3);
            missions.add(challengeMission4);
            missions.add(challengeMission5);
            missions.add(challengeMission6);

            missionRepository.saveAll(missions);
        }

        public void userMissionInit() {

            Users user = userRepository.findById(2L).get();

            List<Missions> dailyMission = missionRepository.findAllByMissionType(MissionType.DAILY);
            List<Missions> weeklyMission = missionRepository.findAllByMissionType(MissionType.WEEKLY);
            List<Missions> challengeMission = missionRepository.findAllByMissionType(MissionType.CHALLENGE);

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
    }
}
