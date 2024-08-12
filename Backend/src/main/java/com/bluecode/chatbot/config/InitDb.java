package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.bluecode.chatbot.domain.Missions.createMission;
import static com.bluecode.chatbot.domain.Quiz.createQuiz;
import static com.bluecode.chatbot.domain.Tests.createTest;
import static com.bluecode.chatbot.domain.UserMissions.createUserMission;
import static com.bluecode.chatbot.domain.Users.createUser;

/**
 * 테스트용 데이터를 DB에 저장하는 class 입니다.
 */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.userInit();
        initService.curriculumInit();
        initService.quizInit();
        initService.studyInit();
        initService.testInit();
        initService.chatInit();
        initService.missionInit();
        initService.userMissionInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @Slf4j
    static class InitService {

        private final UserRepository userRepository;
        private final CurriculumRepository curriculumRepository;
        private final QuizRepository quizRepository;
        private final TestRepository testRepository;
        private final StudyRepository studyRepository;
        private final ChatRepository chatRepository;
        private final MissionRepository missionRepository;
        private final UserMissionRepository userMissionRepository;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        public void userInit() {
            String encodedPassword=bCryptPasswordEncoder.encode("1111");
            Users user1 = createUser("testName", "testEmail", "testId", encodedPassword, "11110033", false); // 초기 테스트 미진행 유저
            Users user2 = createUser("testName2", "testEmail2", "testId2", encodedPassword, "22223344", true); // 초기 테스트 진행 유저 (3챕터에서 시작)
            userRepository.save(user1);
            userRepository.save(user2);
            log.info("Users have been initialized");
        }

        public void curriculumInit() {

            // 루트
            Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 11, false, true);
            curriculumRepository.save(root);

            // 챕터
            Curriculums chap1 = Curriculums.createCurriculum(root, root, "파이썬 환경", false, 1, 0, 1, false, false);
            Curriculums chap2 = Curriculums.createCurriculum(root, root, "변수와 자료형", true, 2, 0, 13, false, false);
            Curriculums chap3 = Curriculums.createCurriculum(root, root, "문자열 처리", true, 3, 0, 9, false, false);
            Curriculums chap4 = Curriculums.createCurriculum(root, root, "조건문", true, 4, 0, 2, false, false);
            Curriculums chap5 = Curriculums.createCurriculum(root, root, "반복문", true, 5, 0, 5, false, false);
            Curriculums chap6 = Curriculums.createCurriculum(root, root, "함수", true, 6, 0, 12, false, false);
            Curriculums chap7 = Curriculums.createCurriculum(root, root, "자료구조", true, 7, 0, 6, false, false);
            Curriculums chap8 = Curriculums.createCurriculum(root, root, "파일 처리", true, 8, 0, 6, false, false);
            Curriculums chap9 = Curriculums.createCurriculum(root, root, "예외 처리", true, 9, 0, 4, false, false);
            Curriculums chap10 = Curriculums.createCurriculum(root, root, "클래스와 객체", true, 10, 0, 10, false, false);
            Curriculums chap11 = Curriculums.createCurriculum(root, root, "모듈과 패키지", false, 11, 0, 2, false, false);
            curriculumRepository.saveAll(Arrays.asList(chap1, chap2, chap3, chap4, chap5, chap6, chap7, chap8, chap9, chap10, chap11));

            // 서브 챕터
            List<Curriculums> sub = new ArrayList<>();

            sub.add(Curriculums.createCurriculum(chap1, root, "파이썬 설치", false, 1, 1, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap2, root, "변수 선언과 활용", false, 2, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "정수와 실수", false, 2, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "문자와 문자열", false, 2, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "불리언", false, 2, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "입력 input()과 출력 print()", false, 2, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "int(), float()", false, 2, 6, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "str(), bool()", false, 2, 7, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "list(), tuple()", false, 2, 8, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "set(), dict()", false, 2, 9, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "chr(), ord()", false, 2, 10, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "산술 연산자 - '+', '-', '*', '/', '//', '%', '**'", false, 2, 11, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "비교 연산자 - '==', '!=', '>', '<', '>=', '<='", false, 2, 12, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "논리 연산자 - 'and', 'or', 'not'", false, 2, 2, 13, true, false));

            sub.add(Curriculums.createCurriculum(chap3, root, "인덱싱 및 슬라이싱", false, 3, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "upper(), lower()", false, 3, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "strip(), lstrip(), rstrip()", false, 3, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "replace(), split(), join()", false, 3, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "find(), count()", false, 3, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "isalpha(), isdigit(), isalnum()", false, 3, 6, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "startswith(), endswith()", false, 3, 7, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "capitalize(), title(), swapcase()", false, 3, 8, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "포맷 - '%', format(), f-string, Template", false, 3, 9, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap4, root, "if, elif, else", false, 4, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap4, root, "조건문 중첩", false, 4, 2, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap5, root, "for", false, 5, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap5, root, "while", false, 5, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap5, root, "break, continue, else", false, 5, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap5, root, "반복문 중첩", false, 5, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap5, root, "리스트 컴프리헨션", false, 5, 5, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap6, root, "def", false, 6, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "매개변수와 반환값", false, 6, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "위치 인자와 키워드 인자", false, 6, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "가변 인자", false, 6, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "재귀 함수", false, 6, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "람다 함수", false, 6, 6, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "len(), sum()", false, 6, 7, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "min(), max()", false, 6, 8, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "sorted(), zip()", false, 6, 9, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "range(), enumerate()", false, 6, 10, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "abs(), round()", false, 6, 11, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap6, root, "map(), filter(), reduce()", false, 6, 12, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap7, root, "리스트와 리스트 메서드", false, 7, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap7, root, "튜플과 튜플 불변성", false, 7, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap7, root, "딕셔너리와 딕셔너리 메서드", false, 7, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap7, root, "집합과 집합 연산", false, 7, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap7, root, "큐와 스택", false, 7, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap7, root, "링크드 리스트", false, 7, 6, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap8, root, "파일 열기 모드('r', 'w', 'a', 'x', 바이너리, 추가 옵션)", false, 8, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap8, root, "파일 읽기 - read(), readline(), readlines()", false, 8, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap8, root, "파일 쓰기 - write(), writelines()", false, 8, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap8, root, "csv 파일 처리", false, 8, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap8, root, "json 파일 처리", false, 8, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap8, root, "json 데이터 파싱 및 생성", false, 8, 6, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap9, root, "예외 발생 원리", false, 9, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap9, root, "try, except, else, finally", false, 9, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap9, root, "다양한 종류의 예외", false, 9, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap9, root, "사용자 정의 예외", false, 9, 4, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap10, root, "객체 지향 프로그래밍의 개념", false, 10, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "클래스", false, 10, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "객체 생성", false, 10, 3, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "생성자(__init__)", false, 10, 4, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "소멸자(__del__)", false, 10, 5, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "상속", false, 10, 6, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "메서드 오버라이딩(오버로딩과의 차이점)", false, 10, 7, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "다형성", false, 10, 8, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "접근 제어자", false, 10, 9, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap10, root, "게터와 세터", false, 10, 10, 1, true, false));

            sub.add(Curriculums.createCurriculum(chap11, root, "모듈의 개념과 import", false, 11, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap11, root, "표준 라이브러리 - math, datetime, os", false, 11, 2, 1, true, false));

            curriculumRepository.saveAll(sub);
        }

        public void quizInit() {

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);
            List<Curriculums> lists = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, false);
            if (lists.size() < 3) {
                throw new NoSuchElementException("Not enough chapters found");
            }

            List<Quiz> quizList = new ArrayList<>();

            // 객관식
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 객관식", lists.get(i).getChapterNum()), "1", QuizLevel.HARD, "정답1", "오답2", "오답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 객관식", lists.get(i).getChapterNum()), "2", QuizLevel.HARD, "오답1", "정답2", "오답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 객관식", lists.get(i).getChapterNum()), "3", QuizLevel.NORMAL, "오답1", "오답2", "정답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 객관식", lists.get(i).getChapterNum()), "4", QuizLevel.EASY, "오답1", "오답2", "오답3", "정답4", "", "", 0));
            }

            // 단답형
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 단답형", lists.get(i).getChapterNum()), "정답", QuizLevel.HARD, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 단답형", lists.get(i).getChapterNum()), "정답", QuizLevel.HARD, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 단답형", lists.get(i).getChapterNum()), "정답", QuizLevel.NORMAL, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 단답형", lists.get(i).getChapterNum()), "정답", QuizLevel.EASY, "", "", "", "","","",2));
            }

            // 코드 작성형
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 중급자 1번째 - 코드작성형", lists.get(i).getChapterNum()), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 중급자 2번째 - 코드작성형", lists.get(i).getChapterNum()), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 초급자 1번째 - 코드작성형", lists.get(i).getChapterNum()), "", QuizLevel.NORMAL, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d: 입문자 1번째 - 코드작성형", lists.get(i).getChapterNum()), "", QuizLevel.EASY, "", "", "", "","1\n2","3",0));
            }

            quizRepository.saveAll(quizList);
        }

        public void studyInit() {

            Users user2 = userRepository.findById(2L).get();
            Curriculums root = curriculumRepository.findAllRootCurriculumList().get(0);
            List<Curriculums> child = curriculumRepository.findAllByRoot(root);

            List<Curriculums> chapter = child.stream().filter(i -> !i.isLeafNode()).toList();
            List<Curriculums> subChapter = child.stream().filter(Curriculums::isLeafNode).toList();

            List<Studies> studies = new ArrayList<>();

            studies.add(Studies.createStudy(user2, root, false, null, null, null, null));

            for (Curriculums curriculums : chapter) {
                studies.add(Studies.createStudy(user2, curriculums, false, null, null, null, null));
            }

            for (Curriculums curriculums : subChapter) {
//                studies.add(Studies.createStudy(user2, curriculums, false,
//                        String.format("챕터 %d - 서브챕터 %d: DEF 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
//                        String.format("챕터 %d - 서브챕터 %d: CODE 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()),
//                        String.format("챕터 %d - 서브챕터 %d: QUIZ 학습 자료", curriculums.getChapterNum(), curriculums.getSubChapterNum()), null));

                if (curriculums.getChapterNum() <= 2) {
                    if (curriculums.isTestable()) {
                        studies.add(Studies.createStudy(user2, curriculums, false,
                                null,
                                null,
                                null, LevelType.HARD));
                    } else {
                        studies.add(Studies.createStudy(user2, curriculums, false,
                                null,
                                null,
                                null, LevelType.EASY));
                    }

                } else {
                    studies.add(Studies.createStudy(user2, curriculums, false,
                            null,
                            null,
                            null, null));
                }
            }

            studyRepository.saveAll(studies);
        }

        public void testInit() {

            Users user2 = userRepository.findById(2L).get();
            Curriculums root = curriculumRepository.findAllRootCurriculumList().get(0);
            List<Curriculums> child = curriculumRepository.findAllByRoot(root);

            List<Curriculums> chapter = child.stream().filter(i -> !i.isLeafNode()).toList();
            List<Curriculums> subChapter = child.stream().filter(Curriculums::isLeafNode).toList();
            Deque<Curriculums> deque = new ArrayDeque<>(subChapter);

            List<Tests> tests = new ArrayList<>();

            // 챕터 1
            Curriculums chap1 = chapter.get(0);

            Optional<Studies> studyChap1 = studyRepository.findByUserAndCurriculum(user2, chap1);
            studyChap1.get().setPassed(true);
            studyChap1.get().setLevel(LevelType.EASY);
            studyRepository.save(studyChap1.get());

            List<Studies> subChaps1 = studyRepository.findAllByUserAndParent(user2, chap1);

            for (Studies studies : subChaps1) {
                studies.setPassed(true);
                studies.setLevel(LevelType.EASY);
            }

            studyRepository.saveAll(subChaps1);

            // 챕터 2
            Curriculums chap2 = chapter.get(1);
            List<Quiz> quizListHard = quizRepository.findAllByCurriculumIdAndLevel(chap2.getCurriculumId(), QuizLevel.HARD);
            List<Quiz> quizListNormal = quizRepository.findAllByCurriculumIdAndLevel(chap2.getCurriculumId(), QuizLevel.NORMAL);
            List<Quiz> quizListEasy = quizRepository.findAllByCurriculumIdAndLevel(chap2.getCurriculumId(), QuizLevel.EASY);

            tests.add(createTest(user2, quizListHard.get(0), 0, true, TestType.INIT));
            tests.add(createTest(user2, quizListHard.get(1), 0, false, TestType.INIT));
            tests.add(createTest(user2, quizListNormal.get(0), 0, true, TestType.INIT));
            tests.add(createTest(user2, quizListEasy.get(0), 0, true, TestType.INIT));

            Optional<Studies> studyChap2 = studyRepository.findByUserAndCurriculum(user2, chap2);
            studyChap2.get().setPassed(true);
            studyChap2.get().setLevel(LevelType.HARD);
            studyRepository.save(studyChap2.get());

            List<Studies> subChaps2 = studyRepository.findAllByUserAndParent(user2, chap2);

            for (Studies studies : subChaps2) {
                studies.setPassed(true);
                studies.setLevel(LevelType.HARD);
            }
            studyRepository.saveAll(subChaps2);

            // 챕터 3(테스트 불합격)
            Curriculums chap3 = chapter.get(2);
            quizListHard = quizRepository.findAllByCurriculumIdAndLevel(chap3.getCurriculumId(), QuizLevel.HARD);
            quizListNormal = quizRepository.findAllByCurriculumIdAndLevel(chap3.getCurriculumId(), QuizLevel.NORMAL);
            quizListEasy = quizRepository.findAllByCurriculumIdAndLevel(chap3.getCurriculumId(), QuizLevel.EASY);

            tests.add(createTest(user2, quizListHard.get(0), 1, false, TestType.INIT));
            tests.add(createTest(user2, quizListHard.get(1), 0, false, TestType.INIT));
            tests.add(createTest(user2, quizListNormal.get(0), 1, false, TestType.INIT));
            tests.add(createTest(user2, quizListEasy.get(0), 0, true, TestType.INIT));

            Optional<Studies> studyChap3 = studyRepository.findByUserAndCurriculum(user2, chap3);
            studyChap3.get().setLevel(LevelType.EASY);
            studyRepository.save(studyChap3.get());

            testRepository.saveAll(tests);
        }

        public void chatInit() {
            Users user = userRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("User not found with id 2"));

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);
            List<Curriculums> subChapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);
            List<Chats> chats = new ArrayList<>();
            int min = 0;

            for (Curriculums subChapter : subChapters) {
                // 각 서브 챕터마다 유형별 질문을 2개씩 생성
                for (int i = 0; i < 2; i++) {
                    chats.add(createChatWithTime(user, subChapter,
                            String.format("챕터 %d - 서브챕터 %d 에서의 질문 %d: DEF", subChapter.getChapterNum(), subChapter.getSubChapterNum(), i + 1),
                            String.format("챕터 %d - 서브챕터 %d 에서의 단일 답변", subChapter.getChapterNum(), subChapter.getSubChapterNum()),
                            QuestionType.DEF, LocalDateTime.now().plusMinutes(min++), 1));

                    chats.add(createChatWithTime(user, subChapter,
                            String.format("챕터 %d - 서브챕터 %d 에서의 질문 %d: CODE", subChapter.getChapterNum(), subChapter.getSubChapterNum(), i + 1),
                            "1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변",
                            QuestionType.CODE, LocalDateTime.now().plusMinutes(min++),3));

                    chats.add(createChatWithTime(user, subChapter,
                            String.format("챕터 %d - 서브챕터 %d 에서의 질문 %d: ERRORS", subChapter.getChapterNum(), subChapter.getSubChapterNum(), i + 1),
                            "1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변",
                            QuestionType.ERRORS, LocalDateTime.now().plusMinutes(min++), 3));
                }
            }

            chatRepository.saveAll(chats);
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

                missions.add(createMission(1000, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.createConstByRootName(root), "\"" + root.getCurriculumName() + "\" 내 모든 챕터 학습 완료하기", 1));

                List<Curriculums> chapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, false);

                for (Curriculums chapter : chapters) {
                    missions.add(createMission(1000, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.createConstByRootName(chapter), "\"" + chapter.getCurriculumName() + "\" 내 모든 서브 챕터 학습 완료하기", 1));
                }

                List<Curriculums> subChapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);

                for (Curriculums subChapter : subChapters) {
                    missions.add(createMission(100, MissionType.CHALLENGE, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter), "\"" + root.getCurriculumName() + ": " + subChapter.getCurriculumName() + "\" 학습 통과하기", 1));
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
