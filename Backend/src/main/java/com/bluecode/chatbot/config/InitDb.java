package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.repository.*;
import jakarta.annotation.PostConstruct;
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
        private final QuizCaseRepository quizCaseRepository;
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
            Curriculums root = Curriculums.createCurriculum(null, null, null, null, "python", false, 0, 0, 11, false, true, LangType.PYTHON);
            curriculumRepository.save(root);

            // 챕터
            Curriculums chap1 = Curriculums.createCurriculum(root, root, null, null, "파이썬 환경", false, 1, 0, 1, false, false, LangType.PYTHON);
            Curriculums chap2 = Curriculums.createCurriculum(root, root, null, null, "변수와 자료형", true, 2, 0, 13, false, false, LangType.PYTHON);
            Curriculums chap3 = Curriculums.createCurriculum(root, root, null, null, "문자열 처리", true, 3, 0, 9, false, false, LangType.PYTHON);
            Curriculums chap4 = Curriculums.createCurriculum(root, root, null, null, "조건문", true, 4, 0, 2, false, false, LangType.PYTHON);
            Curriculums chap5 = Curriculums.createCurriculum(root, root, null, null, "반복문", true, 5, 0, 5, false, false, LangType.PYTHON);
            Curriculums chap6 = Curriculums.createCurriculum(root, root, null, null, "함수", true, 6, 0, 12, false, false, LangType.PYTHON);
            Curriculums chap7 = Curriculums.createCurriculum(root, root, null, null, "자료구조", true, 7, 0, 6, false, false, LangType.PYTHON);
            Curriculums chap8 = Curriculums.createCurriculum(root, root, null, null, "파일 처리", false, 8, 0, 6, false, false, LangType.PYTHON);
            Curriculums chap9 = Curriculums.createCurriculum(root, root, null, null, "예외 처리", true, 9, 0, 4, false, false, LangType.PYTHON);
            Curriculums chap10 = Curriculums.createCurriculum(root, root, null, null, "클래스와 객체", false, 10, 0, 10, false, false, LangType.PYTHON);
            Curriculums chap11 = Curriculums.createCurriculum(root, root, null, null, "모듈과 패키지", false, 11, 0, 2, false, false, LangType.PYTHON);

            List<Curriculums> chapters = Arrays.asList(chap1, chap2, chap3, chap4, chap5, chap6, chap7, chap8, chap9, chap10, chap11);
            curriculumRepository.saveAll(chapters);

            // before 설정
            chap1.setBefore(null);
            chap2.setBefore(chap1);
            chap3.setBefore(chap2);
            chap4.setBefore(chap3);
            chap5.setBefore(chap4);
            chap6.setBefore(chap5);
            chap7.setBefore(chap6);
            chap8.setBefore(chap7);
            chap9.setBefore(chap8);
            chap10.setBefore(chap9);
            chap11.setBefore(chap10);

            // next 설정
            chap1.setNext(chap2);
            chap2.setNext(chap3);
            chap3.setNext(chap4);
            chap4.setNext(chap5);
            chap5.setNext(chap6);
            chap6.setNext(chap7);
            chap7.setNext(chap8);
            chap8.setNext(chap9);
            chap9.setNext(chap10);
            chap10.setNext(chap11);
            chap11.setNext(null);
            curriculumRepository.saveAll(chapters);

            // 서브 챕터
            List<Curriculums> sub = new ArrayList<>();

            sub.add(Curriculums.createCurriculum(chap1, root, null, null, "파이썬 설치", false, 1, 1, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "변수 선언과 활용", false, 2, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "정수와 실수", false, 2, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "문자와 문자열", false, 2, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "불리언", false, 2, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "입력 input()과 출력 print()", false, 2, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "int(), float()", false, 2, 6, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "str(), bool()", false, 2, 7, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "list(), tuple()", false, 2, 8, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "set(), dict()", false, 2, 9, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "chr(), ord()", false, 2, 10, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "산술 연산자 - '+', '-', '*', '/', '//', '%', '**'", false, 2, 11, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "비교 연산자 - '==', '!=', '>', '<', '>=', '<='", false, 2, 12, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap2, root, null, null, "논리 연산자 - 'and', 'or', 'not'", false, 2, 13, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "인덱싱 및 슬라이싱", false, 3, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "upper(), lower()", false, 3, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "strip(), lstrip(), rstrip()", false, 3, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "replace(), split(), join()", false, 3, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "find(), count()", false, 3, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "isalpha(), isdigit(), isalnum()", false, 3, 6, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "startswith(), endswith()", false, 3, 7, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "capitalize(), title(), swapcase()", false, 3, 8, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap3, root, null, null, "포맷 - '%', format(), f-string, Template", false, 3, 9, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap4, root, null, null, "if, elif, else", false, 4, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap4, root, null, null, "조건문 중첩", false, 4, 2, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap5, root, null, null, "for", false, 5, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap5, root, null, null, "while", false, 5, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap5, root, null, null, "break, continue, else", false, 5, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap5, root, null, null, "반복문 중첩", false, 5, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap5, root, null, null, "리스트 컴프리헨션", false, 5, 5, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "def", false, 6, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "매개변수와 반환값", false, 6, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "위치 인자와 키워드 인자", false, 6, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "가변 인자", false, 6, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "재귀 함수", false, 6, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "람다 함수", false, 6, 6, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "len(), sum()", false, 6, 7, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "min(), max()", false, 6, 8, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "sorted(), zip()", false, 6, 9, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "range(), enumerate()", false, 6, 10, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "abs(), round()", false, 6, 11, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap6, root, null, null, "map(), filter(), reduce()", false, 6, 12, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "리스트와 리스트 메서드", false, 7, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "튜플과 튜플 불변성", false, 7, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "딕셔너리와 딕셔너리 메서드", false, 7, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "집합과 집합 연산", false, 7, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "큐와 스택", false, 7, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap7, root, null, null, "링크드 리스트", false, 7, 6, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "파일 열기 모드('r', 'w', 'a', 'x', 바이너리, 추가 옵션)", false, 8, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "파일 읽기 - read(), readline(), readlines()", false, 8, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "파일 쓰기 - write(), writelines()", false, 8, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "csv 파일 처리", false, 8, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "json 파일 처리", false, 8, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap8, root, null, null, "json 데이터 파싱 및 생성", false, 8, 6, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap9, root, null, null, "예외 발생 원리", false, 9, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap9, root, null, null, "try, except, else, finally", false, 9, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap9, root, null, null, "다양한 종류의 예외", false, 9, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap9, root, null, null, "사용자 정의 예외", false, 9, 4, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "객체 지향 프로그래밍의 개념", false, 10, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "클래스", false, 10, 2, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "객체 생성", false, 10, 3, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "생성자(__init__)", false, 10, 4, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "소멸자(__del__)", false, 10, 5, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "상속", false, 10, 6, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "메서드 오버라이딩(오버로딩과의 차이점)", false, 10, 7, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "다형성", false, 10, 8, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "접근 제어자", false, 10, 9, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap10, root, null, null, "게터와 세터", false, 10, 10, 1, true, false, LangType.PYTHON));

            sub.add(Curriculums.createCurriculum(chap11, root, null, null, "모듈의 개념과 import", false, 11, 1, 1, true, false, LangType.PYTHON));
            sub.add(Curriculums.createCurriculum(chap11, root, null, null, "표준 라이브러리 - math, datetime, os", false, 11, 2, 1, true, false, LangType.PYTHON));

            curriculumRepository.saveAll(sub);
        }

        public void quizInit() {

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);
            List<Curriculums> chapter = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, false);

            List<Quiz> quizList = new ArrayList<>();

            for (Curriculums curriculums : chapter) {
                log.info("curriculums: {}", curriculums.getCurriculumName());
            }

            // 객관식
            // 챕터 2
            log.info("chap: {}", chapter.get(1).getCurriculumName());
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.NUM,
                    "다음 설명에 해당하는 자료형은 무엇일까요?\n\n- 순서가 보장되고, 중복되는 값을 허용하며, 요소를 추가, 삭제, 수정할 수 있는 자료형입니다.",
                    "튜플",
                    "리스트",
                    "딕셔너리",
                    "집합",
                    "리스트", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.NUM,
                    "```\nx = 10\ny = 3\nprint(x // y)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "3.333333333",
                    "3",
                    "1",
                    "오류 발생",
                    "3", QuizLevel.EASY, 0));
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.NUM,
                    "```\na = \"Hello\"\nb = \"World\"\nc = a + \" \" + b\nprint(c)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "HelloWorld",
                    "Hello World",
                    "\"Hello World\"",
                    "오류 발생",
                    "Hello World", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.NUM,
                    "다음 설명에 해당하는 자료형은 무엇일까요?\n\n- 변하지 않는 값들의 순서 있는 집합으로, 한 번 생성되면 요소를 추가하거나 삭제할 수 없습니다.",
                    "리스트",
                    "튜플",
                    "딕셔너리",
                    "집합",
                    "튜플", QuizLevel.NORMAL,0));

            // 챕터 3
            log.info("chap: {}", chapter.get(2).getCurriculumName());
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.NUM,
                    "다음 설명에 해당하는 문자열 메서드는 무엇일까요?\n\n- 주어진 문자열에서 특정 문자열이 처음으로 나타나는 인덱스를 반환합니다.\n- 만약 찾는 문자열이 없으면 -1을 반환합니다.",
                    "find()",
                    "index()",
                    "count()",
                    "replace()",
                    "find()", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.NUM,
                    "다음 중 문자열 포맷팅 방식이 아닌 것은?",
                    "% 포맷팅",
                    "format() 메서드",
                    "f-string",
                    "split() 메서드",
                    "split() 메서드", QuizLevel.EASY,0));

            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.NUM,
                    "```\ntext = \"  Hello, World!  \"\nresult = text.strip().split(\',\')\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "['Hello', 'World!']",
                    "['Hello', ' World!']",
                    "['Hello, World!']",
                    "오류 발생",
                    "['Hello', 'World!']", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.NUM,
                    "```\nname = \"홍길동\"\nage = 30\nresult = f\"안녕하세요, 제 이름은 {name}이고, 나이는 {age}살입니다.\"\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "안녕하세요, 제 이름은 {name}이고, 나이는 {age}살입니다.",
                    "안녕하세요, 제 이름은 홍길동이고, 나이는 30살입니다.",
                    "오류 발생",
                    "안녕하세요, 제 이름은 name이고, 나이는 age살입니다.",
                    "안녕하세요, 제 이름은 홍길동이고, 나이는 30살입니다.", QuizLevel.NORMAL,0));

            // 챕터 4
            log.info("chap: {}", chapter.get(3).getCurriculumName());
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.NUM,
                    "다음 중 Python의 조건문에서 if 문을 사용할 때 반드시 필요한 요소는 무엇입니까?",
                    "조건을 검사하기 위한 표현식",
                    "반복문",
                    "함수 호출",
                    "리스트 선언",
                    "조건을 검사하기 위한 표현식", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.NUM,
                    "다음 중 Python의 조건문에서 if 문과 elif 문을 사용할 때, 두 문장의 차이에 대한 설명으로 올바른 것은 무엇입니까?",
                    "if 문은 조건을 검사하고, elif 문은 항상 참(True)으로 간주된다.",
                    "if 문은 첫 번째 조건을 검사하고, elif 문은 이전 if 문이나 elif 문이 거짓(False)일 때 다음 조건을 검사한다.",
                    "elif 문은 if 문 다음에만 사용할 수 있으며, if 문은 여러 번 반복될 수 있다.",
                    "elif 문은 else 문 다음에 사용된다.",
                    "if 문은 첫 번째 조건을 검사하고, elif 문은 이전 if 문이나 elif 문이 거짓(False)일 때 다음 조건을 검사한다.", QuizLevel.EASY,0));

            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.NUM,
                    "```\ny = 15\nif y % 2 == 0:\n    print(\"Even\")\nelif y % 3 == 0:\n    print(\"Divisible by 3\")\nelif y % 5 == 0:\n    print(\"Divisible by 5\")\nelse:\n    print(\"Other\")\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "Even",
                    "Divisible by 3",
                    "Divisible by 5",
                    "Other",
                    "Divisible by 3", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.NUM,
                    "```\nx = 7\ny = 10\n\nif x > y:\n    if x > 5:\n        print(\"X is greater and large\")\n    else:\n        print(\"X is greater but small\")\nelif x == y:\n    print(\"X equals Y\")\nelse:\n    if y > 15:\n        print(\"Y is large\")\n    else:\n        print(\"Y is not large\")\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "X is greater and large",
                    "X equals Y",
                    "Y is large",
                    "Y is not large",
                    "Y is not large", QuizLevel.NORMAL,0));


            // 챕터 5
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.NUM,
                    "다음 중 Python에서 while 반복문이 종료되는 조건으로 올바른 것은 무엇입니까?",
                    "조건식이 거짓(False)이 될 때",
                    "break 문을 만나지 않을 때",
                    "반복 횟수가 10회에 도달할 때",
                    "continue 문을 사용할 때",
                    "조건식이 거짓(False)이 될 때", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.NUM,
                    "다음 중 Python의 for 반복문에 대한 설명으로 올바른 것은 무엇입니까?",
                    "for 반복문은 주어진 조건이 참일 때까지 계속 반복된다.",
                    "for 반복문은 주어진 시퀀스(예: 리스트, 문자열 등)의 각 항목을 순차적으로 순회하며 실행된다.",
                    "for 반복문은 조건 없이 무한히 반복된다.",
                    "for 반복문은 오직 숫자 시퀀스에 대해서만 사용할 수 있다.",
                    "for 반복문은 주어진 시퀀스(예: 리스트, 문자열 등)의 각 항목을 순차적으로 순회하며 실행된다.", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.NUM,
                    "```\nresult = []\nfor i in range(5):\n    if i % 2 == 0:\n        continue\n    result.append(i)\nelse:\n    result.append(\"Done\")\n\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "[1, 3, \"Done\"]",
                    "[0, 2, 4, \"Done\"]",
                    "[1, 3]",
                    "[1, 3, 5]",
                    "[1, 3, \"Done\"]", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.NUM,
                    "```\ncount = 0\nwhile count < 3:\n    print(\"Count is:\", count)\n    count += 1\nelse:\n    print(\"Loop ended.\")\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "Count is: 0\nCount is: 1\nCount is: 2",
                    "Count is: 0\nCount is: 1\nCount is: 2\nLoop ended.",
                    "Loop ended.\nCount is: 0\nCount is: 1\nCount is: 2",
                    "Count is: 1\nCount is: 2\nLoop ended.",
                    "Count is: 0\nCount is: 1\nCount is: 2\nLoop ended.", QuizLevel.NORMAL,0));

            // 챕터 6
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.NUM,
                    "다음 중 Python에서 함수의 기본 인자(Default Argument)에 대한 설명으로 올바른 것은 무엇입니까?",
                    "기본 인자는 함수 호출 시 반드시 전달해야 한다.",
                    "기본 인자는 항상 마지막 매개변수로만 설정할 수 있다.",
                    "기본 인자를 사용하는 경우, 해당 인자에 값을 전달하지 않으면 기본값이 사용된다.",
                    "기본 인자는 함수 내부에서만 사용할 수 있다.",
                    "기본 인자를 사용하는 경우, 해당 인자에 값을 전달하지 않으면 기본값이 사용된다.", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.NUM,
                    "다음 중 Python에서 람다 함수(lambda function) 에 대한 설명으로 올바른 것은 무엇입니까?",
                    "람다 함수는 def 키워드를 사용하여 정의된다.",
                    "람다 함수는 여러 줄의 코드를 포함할 수 있다.",
                    "람다 함수는 익명 함수로, 이름이 없으며 단일 표현식으로 정의된다.",
                    "람다 함수는 항상 기본 인자를 사용해야 한다.",
                    "람다 함수는 익명 함수로, 이름이 없으며 단일 표현식으로 정의된다.", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.NUM,
                    "```\ndef func(a, b=5, c=10):\n    return a + b + c\n\nresult = func(3, c=20)\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "18",
                    "28",
                    "38",
                    "오류 발생",
                    "28", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.NUM,
                    "```\ndef multiply(a, b=2):\n    return a * b\n\nresult = multiply(b=3, a=5)\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "6",
                    "10",
                    "15",
                    "오류 발생",
                    "15", QuizLevel.NORMAL,0));

            // 챕터 7
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.NUM,
                    "다음 설명에 해당하는 자료구조는 무엇일까요?\n\n- 순서가 보장되고, 중복된 값을 허용하며, 요소에 대한 접근과 수정이 용이합니다.\n- 리스트와 유사하지만, 일단 생성된 후에는 요소를 추가하거나 삭제할 수 없습니다.",
                    "딕셔너리",
                    "튜플",
                    "집합",
                    "큐",
                    "튜플", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.NUM,
                    "딕셔너리에서 키(key)는 어떤 특징을 가져야 할까요?",
                    "반드시 문자열 형태여야 한다.",
                    "중복될 수 있다.",
                    "변경 가능한 자료형이어야 한다.",
                    "해시 가능해야 한다.",
                    "해시 가능해야 한다.", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.NUM,
                    "```\nnumbers = [1, 2, 3, 4, 5]\nresult = [x**2 for x in numbers if x % 2 == 0]\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "[1, 4, 9, 16, 25]",
                    "[4, 16]",
                    "[2, 4]",
                    "오류 발생",
                    "[4, 16]", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.NUM,
                    "```\nmy_dict = {\'a\': 1, \'b\': 2, \'c\': 3}\nresult = list(map(lambda x: x[0] * x[1], my_dict.items()))\nprint(result)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "['a1', 'b2', 'c3']",
                    "[1, 2, 3]",
                    "['a', 'b', 'c']",
                    "[0, 2, 6]",
                    "[0, 2, 6]", QuizLevel.NORMAL,0));

            // 챕터 9
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.NUM,
                    "아래 try-except-else-finally의 역할을 바르게 나열한 것은?\n\n- 예외가 발생할 가능성이 있는 코드 블록\n- 예외가 발생했을 때 실행되는 코드 블록\n- 예외 없이 try 블록이 정상적으로 실행되었을 때 실행되는 코드 블록\n- 예외 발생 여부와 상관없이 항상 실행되는 코드 블록",
                    "(try, except, else, finally)",
                    "(except, try, else, finally)",
                    "(try, else, except, finally)",
                    "(try, finally, except, else)",
                    "(try, except, else, finally)", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.NUM,
                    "사용자 정의 예외를 만드는 주된 이유는 무엇일까요?",
                    "내장 예외를 사용하는 것보다 코드 실행 속도를 빠르게 하기 위해",
                    "특정 상황에서 발생하는 오류를 명확하게 구분하고 처리하기 위해",
                    "예외 처리를 생략하고 프로그램을 더 간결하게 만들기 위해",
                    "파이썬의 기본 예외 처리 메커니즘을 수정하기 위해",
                    "특정 상황에서 발생하는 오류를 명확하게 구분하고 처리하기 위해", QuizLevel.EASY,0));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.NUM,
                    "```\ntry:\n    result = 10 / 0\nexcept ZeroDivisionError:\n    print(\"0으로 나눌 수 없습니다.\")\nexcept TypeError:\n    print(\"타입 오류가 발생했습니다.\")\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "0으로 나눌 수 없습니다.",
                    "타입 오류가 발생했습니다.",
                    "예외가 발생하지 않았습니다.",
                    "프로그램이 종료됩니다.",
                    "0으로 나눌 수 없습니다.", QuizLevel.NORMAL,0));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.NUM,
                    "```\nclass CustomError(Exception):\n    pass\n\ntry:\n    raise CustomError(\"사용자 정의 예외 발생\")\nexcept CustomError as e:\n    print(e)\nfinally:\n    print(\"프로그램 종료\")\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
                    "사용자 정의 예외 발생",
                    "프로그램 종료",
                    "사용자 정의 예외 발생\n프로그램 종료",
                    "오류가 발생하여 프로그램이 종료됩니다.",
                    "사용자 정의 예외 발생\n프로그램 종료", QuizLevel.NORMAL,0));

            // 챕터 10
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.NUM,
//                    "다음 설명에 해당하는 객체지향 프로그래밍의 특징은 무엇일까요?\n\n- 서로 다른 클래스의 객체를 동일한 타입으로 취급하여 공통된 인터페이스를 통해 다양한 객체를 사용할 수 있습니다.\n- 예를 들어, 동물 클래스의 메서드를 호출하여 다양한 동물 객체의 울음소리를 들을 수 있습니다.",
//                    "상속",
//                    "다형성",
//                    "캡슐화",
//                    "추상화",
//                    "다형성", QuizLevel.EASY,0));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.NUM,
//                    "클래스의 생성자 (init) 메서드의 주된 역할은 무엇일까요?",
//                    "객체가 소멸될 때 자동으로 호출되어 자원을 해제하는 역할",
//                    "객체가 생성될 때 초기화 작업을 수행하는 역할",
//                    "클래스의 속성 값을 변경하는 역할",
//                    "클래스의 메서드를 호출하는 역할",
//                    "객체가 생성될 때 초기화 작업을 수행하는 역할", QuizLevel.EASY,0));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.NUM,
//                    "```\nclass Animal:\n    def __init__(self, name):\n        self.name = name\n\n    def sound(self):\n        print(\"동물의 소리\")\n\nclass Dog(Animal):\n    def sound(self):\n        print(\"멍멍\")\n\ndog = Dog(\"멍멍이\")\ndog.sound()\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
//                    "동물의 소리",
//                    "멍멍",
//                    "오류 발생",
//                    "아무것도 출력되지 않음",
//                    "멍멍", QuizLevel.NORMAL,0));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.NUM,
//                    "```\nclass Person:\n    def __init__(self, name, age):\n        self.__name = name\n        self.__age = age\n\n    def get_name(self):\n        return self.__name\n\nperson = Person(\"홍길동\", 30)\nprint(person.name)\n```\n\n위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.",
//                    "홍길동",
//                    "30",
//                    "오류 발생",
//                    "아무것도 출력되지 않음",
//                    "오류 발생", QuizLevel.NORMAL,0));

            // 답답형
            // 챕터 2
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 파이썬에서 변수의 자료형을 확인하기 위해 사용하는 함수는 (         )이다. 이 함수는 변수나 값을 입력받아 그 자료형을 반환한다.",
                    null,
                    null,
                    null,
                    null,
                    "type", QuizLevel.EASY,4));
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 정수형을 실수형으로 변환하기 위해서는 (          ) 함수를 사용한다. 이 함수는 입력된 정수를 실수로 변환하여 반환한다.",
                    null,
                    null,
                    null,
                    null,
                    "float", QuizLevel.EASY,5));
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.WORD,
                    "```\nx = 7\ny = 3\nresult = x // y + x % y\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "7", QuizLevel.NORMAL,1));
            quizList.add(Quiz.createQuiz(chapter.get(1), QuizType.WORD,
                    "```\na = \"15\"\nb = \"3.5\"\nresult = float(a) * float(b) + int(float(b))\nprint(result)n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\n",
                    null,
                    null,
                    null,
                    null,
                    "55.5", QuizLevel.NORMAL,4));

            // 챕터 3
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 문자열에서 특정 부분을 다른 문자열로 교체하기 위해 사용하는 메서드는 (         )이다. 이 메서드는 첫 번째 인수로 교체할 대상 문자열을, 두 번째 인수로 새로운 문자열을 입력받는다.\n",
                    null,
                    null,
                    null,
                    null,
                    "replace", QuizLevel.EASY,7));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 문자열의 양쪽 끝에서 특정 문자를 제거하기 위해 사용하는 메서드는 (           )이다. 이 메서드는 기본적으로 공백을 제거하지만, 특정 문자를 인수로 전달하면 그 문자를 제거할 수도 있다.",
                    null,
                    null,
                    null,
                    null,
                    "strip", QuizLevel.EASY,5));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.WORD,
                    "```\ns = \"Python Programming\"\nresult = s[7:18:2].upper()\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "RGAMN", QuizLevel.NORMAL,5));
            quizList.add(Quiz.createQuiz(chapter.get(2), QuizType.WORD,
                    "\"```\nsentence = \"Python is easy and powerful.\"\nwords = sentence.split(\" \")\nresult = \"-\".join([word.capitalize() for word in words if word.startswith(\"p\")])\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\"",
                    null,
                    null,
                    null,
                    null,
                    "Python-Powerful", QuizLevel.NORMAL,15));

            // 챕터 4
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- if 문에서 조건이 참일 때 수행할 코드 블록이 실행되지 않는 경우, 해당 조건의 반대되는 조건이 참일 때 실행할 코드를 지정하기 위해 (          ) 키워드를 사용한다.",
                    null,
                    null,
                    null,
                    null,
                    "else", QuizLevel.EASY,4));
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 여러 개의 조건을 순차적으로 검사하여 각각의 조건에 따라 다른 코드를 실행하고 싶을 때, if 문과 else 문 사이에 (           ) 키워드를 사용하여 조건을 추가할 수 있다.\n",
                    null,
                    null,
                    null,
                    null,
                    "elif", QuizLevel.EASY,4));
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.WORD,
                    "```\nx = 15\ny = 25\nif x > 10:\n    if y < 20:\n        result = \"A\"\n    elif y > 30:\n        result = \"B\"\n    else:\n        result = \"C\"\nelse:\n    result = \"D\"\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\t\n",
                    null,
                    null,
                    null,
                    null,
                    "C", QuizLevel.NORMAL,1));
            quizList.add(Quiz.createQuiz(chapter.get(3), QuizType.WORD,
                    "```\nx = 15\ny = 25\nif x > 10:\n    if y < 20:\n        result = \"A\"\n    elif y > 30:\n        result = \"B\"\n    else:\n        result = \"C\"\nelse:\n    result = \"D\"\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\t\n",
                    null,
                    null,
                    null,
                    null,
                    "Y", QuizLevel.NORMAL,1));

            // 챕터 5
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- while 문이나 for 문에서 반복을 중단하고 빠져나가기 위해 사용하는 키워드는 (           )이다. 이 키워드가 실행되면 반복문의 남은 부분이 실행되지 않고 반복문 밖으로 빠져나온다.\n",
                    null,
                    null,
                    null,
                    null,
                    "break", QuizLevel.EASY,5));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- for 문이나 while 문이 정상적으로 끝까지 실행되었을 때, 특정 코드를 실행하고 싶다면 반복문에 (           )절을 추가할 수 있다.\n",
                    null,
                    null,
                    null,
                    null,
                    "else", QuizLevel.EASY,4));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.WORD,
                    "```\nresult = 0\nfor i in range(10):\n    if i % 3 == 0:\n        continue\n    result += i\n    if i == 7:\n        break\nelse:\n    result += 10\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\n",
                    null,
                    null,
                    null,
                    null,
                    "15", QuizLevel.NORMAL,2));
            quizList.add(Quiz.createQuiz(chapter.get(4), QuizType.WORD,
                    "```\nlst = [2, 3, 5, 7, 11, 13]\nresult = [x**2 for x in lst if x % 2 == 1 and x > 5]\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.\n",
                    null,
                    null,
                    null,
                    null,
                    "[49, 121, 169]", QuizLevel.NORMAL,14));

            // 챕터 6
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 함수를 정의할 때 사용되는 키워드는 (           )이다. 이 키워드 뒤에 함수 이름과 매개변수를 작성한 후, 함수 본문을 작성하여 원하는 작업을 수행한다.",
                    null,
                    null,
                    null,
                    null,
                    "def", QuizLevel.EASY,3));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\\n\\n- (           ) 함수는 전달된 시퀀스(리스트, 튜플 등)의 길이를 반환한다. 이 함수는 문자열의 길이도 구할 수 있다.",
                    null,
                    null,
                    null,
                    null,
                    "len", QuizLevel.EASY,3));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.WORD,
                    "```\ndef recursive_sum(n):\n    if n == 1:\n        return 1\n    return n + recursive_sum(n - 1)\n\nresult = recursive_sum(5)\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "15", QuizLevel.NORMAL,2));
            quizList.add(Quiz.createQuiz(chapter.get(5), QuizType.WORD,
                    "```\nnums = [3, 7, 2, 8, 4]\nresult = list(map(lambda x: x**2, filter(lambda x: x % 2 == 0, nums)))\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "[4, 64, 16]", QuizLevel.NORMAL,11));

            // 챕터 7
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 리스트에 새로운 요소를 추가할 때 사용하는 메서드는 (           )이다. 이 메서드는 리스트의 끝에 요소를 하나 추가한다.",
                    null,
                    null,
                    null,
                    null,
                    "append", QuizLevel.EASY,6));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 튜플은 불변의 자료형으로, 생성된 이후에는 요소를 변경할 수 없다. 만약 튜플 내의 요소를 변경하려고 시도하면 (           )이라는 에러가 발생한다.",
                    null,
                    null,
                    null,
                    null,
                    "TypeError", QuizLevel.EASY,9));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.WORD,
                    "```\nstack = []\nstack.append(5)\nstack.append(3)\nstack.pop()\nstack.append(7)\nstack.append(8)\nstack.pop()\nprint(stack)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "[5, 7]", QuizLevel.NORMAL,6));
            quizList.add(Quiz.createQuiz(chapter.get(6), QuizType.WORD,
                    "```\na = {1, 2, 3, 4}\nb = {3, 4, 5, 6}\nresult = a.symmetric_difference(b)\nprint(sorted(result))\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "[1, 2, 5, 6]", QuizLevel.NORMAL,12));

            // 챕터 9
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 파이썬에서 코드 실행 중 오류가 발생하면 프로그램이 비정상 종료될 수 있다. 이를 방지하고자 오류를 처리하기 위해 사용하는 블록은 try이다. 이 블록 안에서 발생한 오류는 해당 블록에 이어지는 (            ) 블록에서 처리할 수 있다.",
                    null,
                    null,
                    null,
                    null,
                    "except", QuizLevel.EASY,6));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.WORD,
                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- (            ) 블록은 예외가 발생하든 발생하지 않든 상관없이 반드시 실행되어야 하는 코드를 포함할 때 사용된다. 이 블록은 자원을 정리하거나 파일을 닫는 작업 등을 수행하는 데 유용하다.",
                    null,
                    null,
                    null,
                    null,
                    "finally", QuizLevel.EASY,7));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.WORD,
                    "```\ntry:\n    x = 10 / 0\nexcept ZeroDivisionError:\n    result = \"Cannot divide by zero!\"\nexcept Exception as e:\n    result = f\"Error occurred: {e}\"\nelse:\n    result = \"Division successful!\"\nfinally:\n    result += \" Process completed.\"\n\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "Cannot divide by zero! Process completed.", QuizLevel.NORMAL,41));
            quizList.add(Quiz.createQuiz(chapter.get(8), QuizType.WORD,
                    "```\nclass CustomError(Exception):\n    pass\n\ntry:\n    raise CustomError(\"This is a custom error\")\nexcept CustomError as e:\n    result = str(e).upper()\nfinally:\n    result += \" - Error handled.\"\n\nprint(result)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
                    null,
                    null,
                    null,
                    null,
                    "THIS IS A CUSTOM ERROR - Error handled.", QuizLevel.NORMAL,39));

            // 챕터 10
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.WORD,
//                    "다음 괄호 안에 들어갈 알맞은 답을 적으세요.\n\n- 클래스 내에서 객체가 생성될 때 자동으로 호출되는 특별한 메서드로, 이 메서드는 객체의 초기화를 담당하며 ' __(        )__'으로 정의된다.",
//                    null,
//                    null,
//                    null,
//                    null,
//                    "init", QuizLevel.EASY,4));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.WORD,
//                    "",
//                    null,
//                    null,
//                    null,
//                    null,
//                    "", QuizLevel.EASY,0));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.WORD,
//                    "```\nclass MyClass:\n    def __init__(self, value):\n        self.__value = value\n\n    def get_value(self):\n        return self.__value\n\n    def set_value(self, value):\n        if value > 0:\n            self.__value = value\n        else:\n            raise ValueError(\"Value must be positive\")\n\nobj = MyClass(10)\nobj.set_value(20)\nprint(obj.get_value())\n\ntry:\n    obj.set_value(-5)\nexcept ValueError as e:\n    print(e)\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
//                    null,
//                    null,
//                    null,
//                    null,
//                    "20 Value must be positive", QuizLevel.NORMAL,25));
//            quizList.add(Quiz.createQuiz(chapter.get(9), QuizType.WORD,
//                    "```\nclass Animal:\n    def __init__(self, name):\n        self.name = name\n\n    def speak(self):\n        return f\"{self.name} makes a sound\"\n\nclass Dog(Animal):\n    def speak(self):\n        return f\"{self.name} barks\"\n\nanimals = [Animal(\"Generic Animal\"), Dog(\"Buddy\")]\n\nfor animal in animals:\n    print(animal.speak())\n```\n\n위 파이썬 코드의 실행 결과를 적으시오.",
//                    null,
//                    null,
//                    null,
//                    null,
//                    "Generic Animal makes a sound Buddy barks", QuizLevel.NORMAL,41));

            quizRepository.saveAll(quizList);


            // 코드작성형
            List<Quiz> quizListCode = new ArrayList<>();

            // 챕터 2
            quizListCode.add(Quiz.createQuiz(chapter.get(1), QuizType.CODE,
                    "간단한 계산기\n\n사용자가 두 개의 정수를 입력하면, 다음의 연산을 수행하는 간단한 계산기 프로그램을 작성하시오.\n\n1. 두 정수의 합\n2. 두 정수의 차\n3. 두 정수의 곱\n4. 두 정수의 몫과 나머지\n\n입력:\n- 두 개의 정수를 각각 한 줄에 입력받습니다.\n\n출력:\n- 입력된 두 정수의 합, 차, 곱, 몫과 나머지를 순서대로 출력합니다.\n\n예제 입력 1:\n```\n10\n3\n```\n\n예제 출력 1:\n```\n합: 13\n차: 7\n곱: 30\n몫: 3, 나머지: 1\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(1), QuizType.CODE,
                    "논리 연산자와 비교 연산자\n\n사용자가 입력한 두 개의 값을 비교하고, 다음의 논리 연산을 수행하는 프로그램을 작성하시오.\n\n1. 첫 번째 값이 두 번째 값보다 큰지 여부를 출력합니다.\n2. 첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은지 여부를 출력합니다.\n3. 두 값이 같지 않은지 여부를 출력합니다.\n\n입력:\n- 두 개의 값을 각각 한 줄에 입력받습니다.\n\n출력:\n- 첫 번째 값이 두 번째 값보다 큰지 여부 (True 또는 False)\n- 첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은지 여부 (True 또는 False)\n- 두 값이 같지 않은지 여부 (True 또는 False)\n\n예제 입력 1:\n```\n5\n-3\n```\n\n예제 출력 1:\n```\n첫 번째 값이 두 번째 값보다 큰가? True\n첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? True\n두 값이 같지 않은가? True\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 3
            quizListCode.add(Quiz.createQuiz(chapter.get(2), QuizType.CODE,
                    "문자열 조작 및 데이터 형 변환\n\n사용자에게 영문 문자열과 숫자를 입력받아 다음과 같은 작업을 수행하는 프로그램을 작성하세요.\n\n1. 입력받은 문자열의 첫 글자를 대문자로 변경하고, 나머지 글자는 소문자로 변경합니다.\n2. 입력받은 숫자에 3을 더한 후, 실수로 변환하여 소수점 아래 2자리까지 출력합니다.\n3. 변경된 문자열과 실수를 이용하여 다음과 같은 형식의 문자열을 만들어 출력합니다.\n\n입력:\n- 하나의 문자열\n- 하나의 숫자\n\n출력:\n- \"변경된 문자열은 {변경된 문자열}이고, 숫자는 {실수}입니다.\"\n단, f-string을 사용하여 문자열을 포맷팅\n\n예제 입력 1:\n```\n문자열: python\n숫자: 5\n```\n\n예제 출력 1:\n```\n변경된 문자열은 Python이고, 숫자는 8.00입니다.\n```\n\n힌트:\n- capitalize() 함수를 사용하여 문자열의 첫 글자를 대문자로 변경할 수 있습니다.\n- float() 함수를 사용하여 정수를 실수로 변환할 수 있습니다.\n- f-string을 사용하여 문자열 안에 변수의 값을 삽입할 수 있습니다.",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(2), QuizType.CODE,
                    "대소문자 변환\n\n사용자에게 영문 문자열을 입럭받아, \'a\'를 \'4\'로 바꾸고, \'e\'를 \'3\'으로 바꾸는 프로그램을 작성하시오.\n\n입력:\n- 사용자에게 영문 문자열을 입력받습니다.\n\n출력:\n- 입력받은 문자열의 모든 문자를 대문자로 변환하여 출력합니다.\n- 이어서, 모든 문자를 소문자로 변환하여 출력합니다.\n\n예제 입력 1:\n```\nHello, World!\n```\n\n예제 출력 1:\n```\nHELLO, WORLD! hello, world!\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 4
            quizListCode.add(Quiz.createQuiz(chapter.get(3), QuizType.CODE,
                    "점수에 따른 학점 계산기\n\n사용자가 입력한 점수에 따라 학점을 출력하는 프로그램을 작성하세요. 점수는 0에서 100 사이의 정수로 입력됩니다. 다음 규칙에 따라 학점을 출력하세요.\n\n1. 90점 이상: A\n2. 80점 이상 90점 미만: B\n3. 70점 이상 80점 미만: C\n4. 60점 이상 70점 미만: D\n5. 60점 미만: F\n6. 또한, 점수가 0 미만이거나 100을 초과하면 \"잘못된 점수입니다.\"를 출력\n\n입력 예제 1:\n```\n85\n```\n\n출력 예제 1:\n```\nB\n```\n\n입력 예제 2:\n```\n105\n```\n\n출력 예제 2:\n```\n잘못된 점수입니다.\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(3), QuizType.CODE,
                    "세 수 중 가장 큰 수 찾기\n\n사용자로부터 세 개의 정수를 입력받아, 이 중 가장 큰 수를 출력하는 프로그램을 작성하세요.\n\n입력 예제 1:\n```\n입력된 세 정수: 10 20 15\n```\n\n출력 예제 1:\n```\n가장 큰 수: 20\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 5
            quizListCode.add(Quiz.createQuiz(chapter.get(4), QuizType.CODE,
                    "숫자 패턴 생성기\n\n사용자가 입력한 정수 n에 따라 다음과 같은 패턴을 출력하는 함수를 작성하시오. 이 패턴은 숫자가 좌우 대칭을 이루며 출력됩니다.\n예를 들어, n = 5일 때 다음과 같은 패턴이 출력되어야 합니다:\n\n```\n1\n121\n12321\n1234321\n123454321\n```\n\n입력:\n- 하나의 정수 n을 입력받습니다. (1 ≤ n ≤ 9)\n\n출력:\n- n 줄에 걸쳐 위의 패턴을 출력합니다.\n\n예제 입력 1:\n```\n5\n```\n\n예제 출력 1:\n```\n1\n121\n12321\n1234321\n123454321\n```\n\n예제 입력 2:\n```\n3\n```\n\n예제 출력 2:\n```\n1\n121\n12321\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(4), QuizType.CODE,
                    "특정 숫자 제거하기\n\n정수 리스트와 제거할 숫자를 입력받아, 입력받은 숫자를 리스트에서 모두 제거하고 남은 숫자를 오름차순으로 정렬하여 출력하는 프로그램을 작성하세요.\n\n입력:\n- 첫 번째 줄에 정수 리스트의 요소 개수 N이 주어진다.\n두 번째 줄에 N개의 정수가 공백으로 구분되어 주어진다.\n세 번째 줄에 제거할 숫자 X가 주어진다.\n\n출력:\n- 입력받은 숫자를 제거하고 정렬된 결과를 한 줄에 공백으로 구분하여 출력한다.\n\n예제 입력 1:\n```\n5\n10 20 30 40 50\n30\n```\n\n예제 출력 1:\n```\n10 20 40 50\n```\n\n예제 입력 2:\n```\n4\n1 2 3 4\n5\n```\n\n예제 출력 2:\n```\n1 2 3 4\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 6
            quizListCode.add(Quiz.createQuiz(chapter.get(5), QuizType.CODE,
                    "문자열 암호화/복호화 함수 만들기\n\n사용자로부터 문자열과 암호화 키를 입력받아, 각 문자를 키만큼 오른쪽으로 이동시켜 암호화하고, 다시 복호화하는 프로그램을 작성하세요.\n\n1. 암호화: 각 문자의 아스키 코드 값에 키 값을 더하고, 255를 초과하면 255를 뺀 나머지 값으로 변환합니다.\n2. 복호화: 암호화된 문자의 아스키 코드 값에서 키 값을 빼고, 0 미만이면 255를 더한 값으로 변환합니다.\n3. 영문 대소문자와 공백만 처리합니다.\n\n입력:\n- 문자열: 암호화할 문자열\n- 암호화 키: 정수 (1 이상)\n\n출력:\n- 암호화된 문자열\n- 복호화된 문자열\n\n입력 예제 1:\n```\nHello, World!\n3\n```\n\n출력 예제 1:\n```\n암호화된 문자열: Khoor, Zruog!\n복호화된 문자열: Hello, World!\n```\n\n힌트:\n- ord() 함수를 사용하여 문자를 아스키 코드 값으로 변환합니다.\n- chr() 함수를 사용하여 아스키 코드 값을 문자로 변환합니다.\n- for 문을 사용하여 문자열의 각 문자를 순회하며 암호화/복호화합니다.",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(5), QuizType.CODE,
                    "문자열 조작 함수 만들기\n\n사용자가 입력한 문자열에서 가장 자주 등장하는 문자와 그 빈도를 출력하는 함수를 작성하시오. 이때, 공백은 무시하고, 대소문자는 구분하지 않습니다.\n\n입력:\n- 하나의 문자열을 입력받습니다.\n\n출력:\n- 가장 자주 등장하는 문자와 그 빈도를 출력합니다.\n\n제한 사항:\n- 함수 내에서 문자열의 모든 문자에 대해 빈도를 계산할 것.\n- 가장 자주 등장하는 문자가 여러 개일 경우, 알파벳 순서로 가장 앞에 있는 문자를 출력할 것.\n\n입력 예제 1:\n```\nHello World\n```\n\n출력 예제 1:\n```\n가장 자주 등장하는 문자: l\n빈도: 3\n```\n\n입력 예제 2:\n```\nThis is a Test\n```\n\n출력 예제 2:\n```\n가장 자주 등장하는 문자: s\n빈도: 3\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 7
            quizListCode.add(Quiz.createQuiz(chapter.get(6), QuizType.CODE,
                    "큐와 스택을 이용한 문자열 처리\n\n입력된 문자열에서 중복된 문자를 제거한 후, 남은 문자를 큐와 스택에 각각 넣고, 큐와 스택의 최종 상태를 반환하는 함수를 작성하시오. 큐는 처음부터 끝까지, 스택은 끝부터 처음까지 순서대로 문자를 넣습니다.\n\n입력:\n- 하나의 문자열 s를 입력 (1 ≤ 문자열 길이 ≤ 100)\n\n출력:\n- 중복된 문자를 제거한 후, 큐와 스택의 최종 상태를 리스트로 반환\n\n입력 예제 1:\n```\nabacabad\n```\n\n출력 예제 1:\n```\n큐: [\'a\', \'b\', \'c\', \'d\']\n스택: [\'d\', \'c\', \'b\', \'a\']\n```\n\n입력 예제 2:\n```\nabcabcabc\n```\n\n출력 예제 2:\n```\n큐: [\'a\', \'b\', \'c\']\n스택: [\'c\', \'b\', \'a\']\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(6), QuizType.CODE,
                    "튜플과 딕셔너리를 이용한 단어 카운트\n\n입력된 문자열에서 각 단어의 빈도를 계산하고, 이를 (단어, 빈도) 형식의 튜플로 저장한 뒤, 단어의 빈도가 높은 순서대로 출력하는 프로그램을 작성하시오. 단, 빈도가 같은 단어는 알파벳 순으로 정렬합니다.\n\n입력:\n- 하나의 문자열 s를 입력 (1 ≤ 문자열 길이 ≤ 200)\n\n출력:\n- (단어, 빈도) 형식의 튜플을 빈도가 높은 순서대로 출력\n\n입력 예제 1:\n```\nThe quick brown fox jumps over the lazy dog The dog\n```\n\n출력 예제 1:\n```\n[(\'the\', 3), (\'dog\', 2), (\'brown\', 1), (\'fox\', 1), (\'jumps\', 1), (\'lazy\', 1), (\'over\', 1), (\'quick\', 1)]\n```\n\n입력 예제 2:\n```\nPython is great and Python is fun\n```\n\n출력 예제 2:\n```\n[(\'python\', 2), (\'is\', 2), (\'and\', 1), (\'fun\', 1), (\'great\', 1)]\n```\n\n제한 사항:\n- 단어의 구분은 공백을 기준으로 합니다.\n- 대소문자는 구분하지 않으며, 출력할 때는 모두 소문자로 변환합니다.",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 9
            quizListCode.add(Quiz.createQuiz(chapter.get(8), QuizType.CODE,
                    "사용자 정의 예외 처리\n\n온라인 쇼핑몰의 결제 시스템을 개발하는 중입니다. 결제 금액을 입력받아 결제를 처리하는 프로그램을 작성하세요. 프로그램은 다음 조건을 만족해야 합니다.\n\n1. 결제 금액이 0 이하일 경우 \"결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요.\"라는 메시지를 출력하고 프로그램을 종료합니다.\n2. 입력된 금액이 숫자가 아닌 경우 ValueError를 처리하여 \"잘못된 입력입니다. 숫자를 입력하세요.\"라는 메시지를 출력합니다.\n3. 결제 금액이 올바르면 \"결제가 완료되었습니다.\"와 남은 잔액을 출력합니다.\n4. 모든 결제 과정이 끝나면 \"결제 시스템을 종료합니다.\"라는 메시지를 출력합니다.\n5. 초기 금액은 10000으로 설정합니다.\n\n입력 예제 1:\n```\n1000\n```\n\n출력 예제 1:\n```\n결제가 완료되었습니다.\n남은 잔액은 9000원입니다.\n결제 시스템을 종료합니다.\n```\n\n입력 예제 2:\n```\n-500\n```\n\n출력 예제 2:\n```\n결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요.\n결제 시스템을 종료합니다.\n```\n\n입력 예제 3:\n```\nABC\n```\n\n출력 예제 3:\n```\n잘못된 입력입니다. 숫자를 입력하세요.\n결제 시스템을 종료합니다.\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));
            quizListCode.add(Quiz.createQuiz(chapter.get(8), QuizType.CODE,
                    "예외 처리와 사용자 정의 예외 구현하기\n\n당신은 예외 처리를 이용해 안정적인 프로그램을 작성해야 하는 개발자입니다. 주어진 함수 \'process_data\'는 두 개의 정수를 입력받아 다음의 규칙에 따라 처리합니다:\n\n1. 첫 번째 정수를 두 번째 정수로 나누어 그 결과를 반환합니다.\n2. 두 번째 정수가 0일 경우, \'ZeroDivisionError\' 예외를 발생시킵니다.\n3. 두 번째 정수가 음수일 경우, \'NegativeValueError\'라는 사용자 정의 예외를 발생시킵니다.\n4. 이 외의 예외가 발생하면, 이를 처리하여 \"\"Error occurred\"\" 메시지를 출력합니다.\n5. 나눗셈이 성공적으로 완료되면, 결과를 출력하고, 마지막으로 \"\"Process complete\"\"라는 메시지를 출력합니다.\n\n사용자 정의 예외 \'NegativeValueError\'를 직접 구현하고, 위의 요구 사항을 만족하는 \'process_data\' 함수를 작성하세요.\n\n입력:\n\n- 두 개의 정수 \'a\'와 \'b\'가 공백으로 구분되어 한 줄로 주어집니다.\n\n출력:\n\n- 나눗셈 결과가 성공적으로 계산되면 그 결과를 출력합니다.\n- 예외가 발생할 경우, 해당 예외에 맞는 메시지를 출력합니다.\n- 항상 마지막에는 \"\"Process complete\"\" 메시지를 출력합니다.\n\n입력 예제 1:\n```\n10 2\n```\n\n출력 예제 1:\n```\n5.0\nProcess complete\n```\n\n입력 예제 2:\n```\n10 0\n```\n\n출력 예제 2:\n```\ndivision by zero\nProcess complete\n```",
                    null, null, null, null, null, QuizLevel.HARD,0));

            // 챕터 10
//            quizListCode.add(Quiz.createQuiz(chapter.get(9), QuizType.CODE,
//                    "차량 관리 시스템\n\n자동차를 관리하는 프로그램을 작성하세요. 자동차는 Car라는 클래스로 표현되며, 각 자동차는 브랜드(brand), 모델(model), 연식(year)을 속성으로 가집니다. 이때, 다음 요구사항을 충족해야 합니다.\n\n1. Car 클래스는 __init__ 생성자를 통해 객체를 초기화할 수 있어야 합니다.\n2. 모든 자동차 객체는 기본적으로 연료(fuel)가 100으로 시작하며, fuel 속성은 외부에서 직접 접근할 수 없고, get_fuel() 메서드를 통해 접근할 수 있습니다.\n3. 연료를 감소시키는 drive() 메서드를 구현하세요. 이 메서드는 주행 거리를 입력받아 그에 비례하여 연료를 감소시킵니다. 연료는 10km당 1씩 감소합니다. 연료가 부족할 경우, 주행을 중단하고 \"연료가 부족합니다.\"라는 메시지를 출력합니다.\n4. 연료를 충전하는 refuel() 메서드를 구현하세요. 이 메서드는 연료를 입력받아 현재 연료에 더해줍니다. 단, 연료는 100을 초과할 수 없습니다.\n5. Car 클래스를 상속받은 ElectricCar 클래스를 구현하세요. 이 클래스는 Car 클래스와 동일한 속성을 가지되, 연료 대신 배터리(battery) 속성을 가지며, 배터리도 외부에서 직접 접근할 수 없고, get_battery() 메서드를 통해 접근할 수 있습니다.\n6. ElectricCar 클래스의 drive() 메서드는 배터리를 1km당 2씩 감소시키며, 배터리가 부족할 경우 \"배터리가 부족합니다.\"라는 메시지를 출력합니다.\n\n입력 예제 1:\n```\nmy_car = Car(\"Toyota\", \"Camry\", 2022)\nmy_car.drive(50)\nmy_car.get_fuel()\nmy_car.refuel(30)\nmy_car.get_fuel()\n\nmy_electric_car = ElectricCar(\"Tesla\", \"Model S\", 2022)\nmy_electric_car.drive(30)\nmy_electric_car.get_battery()\nmy_electric_car.refuel(50)\nmy_electric_car.get_battery()\n```\n\n출력 예제 1:\n```\n연료가 부족합니다.\n50\n80\n배터리가 부족합니다.\n40\n```",
//                    null, null, null, null, null, QuizLevel.HARD,0));
//            quizListCode.add(Quiz.createQuiz(chapter.get(9), QuizType.CODE,
//                    "도형 클래스 설계\n\n다양한 도형을 표현할 수 있는 클래스를 설계하세요. 각 도형은 Shape라는 기본 클래스로부터 상속받으며, 다음과 같은 요구사항을 만족해야 합니다.\n\n1. Shape 클래스는 면적(area)을 계산하는 calculate_area() 메서드를 포함하며, 기본적으로 \"Not implemented\" 메시지를 출력하는 메서드입니다.\n2. Circle, Rectangle, Triangle 클래스는 각각 Shape 클래스를 상속받으며, 자신의 도형에 맞는 면적 계산 메서드를 오버라이딩합니다.\n3. 각 클래스는 생성자에서 도형의 특성을 초기화하며, 예를 들어 Circle은 반지름, Rectangle은 가로와 세로, Triangle은 밑변과 높이를 초기화해야 합니다.\n4. 각각의 클래스에서 면적을 계산하는 메서드 calculate_area()를 구현하세요.\n- 원의 면적: 𝜋 × 반지름**2 \n- 사각형의 면적: 가로 × 세로\n- 삼각형의 면적: ½ × 밑변 × 높이\n\n입력 예제 1:\n```\ncircle = Circle(5)\nrectangle = Rectangle(4, 6)\ntriangle = Triangle(3, 4)\n\nprint(circle.calculate_area())\nprint(rectangle.calculate_area())\nprint(triangle.calculate_area())\n```\n\n출력 예제 1:\n```\n78.5\n24\n6.0\n```",
//                    null, null, null, null, null, QuizLevel.HARD,0));


            quizRepository.saveAll(quizListCode);

            // 코드작성형 Case
            List<QuizCase> quizCases = new ArrayList<>();
            int idx = 0;

            // 챕터 2
            Quiz quizCode = quizListCode.get(idx++);

            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "-5\n2\n",
                    "합: -3\n차: -7\n곱: -10\n몫: -2, 나머지: -1"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "100\n0\n",
                    "합: 100\n차: 100\n곱: 0\n몫: 오류: 0으로 나눌 수 없습니다.\n나머지: 오류: 0으로 나눌 수 없습니다."));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "0\n10\n",
                    "첫 번째 값이 두 번째 값보다 큰가? False\n첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? False\n두 값이 같지 않은가? True"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "5\n5\n",
                    "첫 번째 값이 두 번째 값보다 큰가? False\n첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? False\n두 값이 같지 않은가? False"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "HELLO WORLD\n-2\n",
                    "변경된 문자열은 Hello world이고, 숫자는 1.00입니다."));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "123abc\n0\n",
                    "변경된 문자열은 123abc이고, 숫자는 3.00입니다."));


            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "Programming is fun!\n",
                    "PROGRAMMING IS FUN! programming is fun!\n"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "a quick brown fox jumps over the lazy dog\n",
                    "A QUICK BROWN FOX JUMPS OVER THE LAZY DOG a quick brown fox jumps over the lazy dog"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "59\n",
                    "F"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "-10\n",
                    "잘못된 점수입니다."));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "5 5 10\n",
                    "가장 큰 수: 10"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "-3 -7 -2\n",
                    "가장 큰 수: -2\n"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "2\n",
                    "1\n121\n"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "9\n",
                    "1\n121\n12321\n1234321\n123454321\n12345654321\n1234567654321\n123456787654321\n12345678987654321\n"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "3\n10 20 10\n10\n",
                    "20"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "7\n3 1 4 1 5 9 2\n1\n",
                    "2 3 4 5 9\n"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "This is a secret message.\n13\n",
                    "Guvf vf n frperg zrnag.\n복호화된 문자열: This is a secret message."));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "A-Z a-z 0123456789\n1\n",
                    "B-A b-y 1234567890\n복호화된 문자열:  A-Z a-z 0123456789"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "aabbccddeeff\n",
                    "가장 자주 등장하는 문자: a, 빈도: 2"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "Mississipi\n",
                    "가장 자주 등장하는 문자: i, 빈도: 4"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "google",
                    "큐: [\'g\', \'o\', \'l\', \'e\']\n스택: [\'e\', \'l\', \'o\', \'g\']"));

            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "mississippi",
                    "큐: [\'m\', \'i\', \'s\', \'p\']\n스택: [\'p\', \'s\', \'i\', \'m\']"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "Python is a programming language. Python is fun! Isn\'t it?",
                    "[(\'python\', 2), (\'is\', 3), (\'a\', 1), (\'programming\', 1), (\'language\', 1), (\'fun\', 1), (\'isn\', 1), (\'it\', 1)]"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "To be or not to be, that is the question. Whether \'tis nobler in the mind to suffer The slings and arrows of outrageous fortune, Or to take arms against a sea of troubles, And by opposing end them? To die: to sleep; No more; and by a sleep to say we end The heart-ache and the thousand natural shocks That flesh is heir to, \'tis a consummation Devoutly to be wish\'d. To die, to sleep; To sleep: perchance to dream: ay, there\'s the rub; For in that sleep of death what dreams may come When we have shuffled off this mortal coil, Must give us pause: there\'s the respect That makes calamity of so long life",
                    "[(\'to\', 6), (\'the\', 3), (\'and\', 3), (\'be\', 3), (\'of\', 3), (\'a\', 2), (\'in\', 2), (\'sleep\', 2), (\'that\', 2), (\'is\', 2), (\'or\', 2), (\'for\', 1), (\'not\', 1), (\'whether\', 1), (\'tis\', 1), (\'nobler\', 1), (\'mind\', 1), (\'suffer\', 1), (\'slings\', 1), (\'arrows\', 1), (\'outrageous\', 1), (\'fortune\', 1), (\'take\', 1), (\'arms\', 1), (\'against\', 1), (\'sea\', 1), (\'troubles\', 1), (\'by\', 1), (\'opposing\', 1), (\'end\', 1), (\'them\', 1), (\'die\', 2), (\'more\', 1), (\'say\', 1), (\'we\', 1), (\'heart\', 1), (\'ache\', 1), (\'thousand\', 1), (\'natural\', 1), (\'shocks\', 1), (\'flesh\', 1), (\'heir\', 1), (\'consummation\', 1), (\'devoutly\', 1), (\'wishd\', 1), (\'perchance\', 1), (\'dream\', 1), (\'ay\', 1), (\'theres\', 2), (\'rub\', 1), (\'when\', 1), (\'have\', 1), (\'shuffled\', 1), (\'off\', 1), (\'this\', 1), (\'mortal\', 1), (\'coil\', 1), (\'must\', 1), (\'give\', 1), (\'us\', 1), (\'pause\', 1), (\'respect\', 1), (\'makes\', 1), (\'calamity\', 1), (\'so\', 1), (\'long\', 1), (\'life\', 1)]"));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "15000\n",
                    "잔액이 부족합니다.\n결제 시스템을 종료합니다."));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "0\n",
                    "결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요.\n결제 시스템을 종료합니다."));

            quizCode = quizListCode.get(idx++);
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "10 -5\n",
                    "Negative value encountered\nProcess complete"));
            quizCases.add(QuizCase.createQuizCase(quizCode,
                    "ten 5\n",
                    "Error occurred\nProcess complete"));

            quizCaseRepository.saveAll(quizCases);
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
