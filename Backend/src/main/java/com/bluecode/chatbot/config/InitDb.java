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
            List<Users> users = new ArrayList<>();
            users.add(createUser("testName", "testEmail", "testId", "1111", "11110033", false)); // 초기 테스트 미진행 유저
            users.add(createUser("testName2", "testEmail2", "testId2", "1111", "22223344", true)); // 초기 테스트 진행 유저 (3챕터에서 시작)
            userRepository.saveAll(users);
        }

        public void curriculumInit() {

            // 루트
            Curriculums root = Curriculums.createCurriculum(null, null, "파이썬", false, 0, 0, 2, false, true);
            curriculumRepository.save(root);

            // 챕터
            List<Curriculums> chapters = new ArrayList<>();
            Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",false, 1, 0, 2, false, false);
            Curriculums chap2 = Curriculums.createCurriculum(root, root, "챕터2",false, 2, 0,2, false, false);
            Curriculums chap3 = Curriculums.createCurriculum(root, root, "챕터3",false, 3, 0,2, false, false);
            chapters.add(chap1);
            chapters.add(chap2);
            chapters.add(chap3);
            curriculumRepository.saveAll(chapters);

            // 서브 챕터
            List<Curriculums> sub = new ArrayList<>();

            sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap2, root, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false));
            sub.add(Curriculums.createCurriculum(chap3, root, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false));

            curriculumRepository.saveAll(sub);
        }

        public void quizInit() {

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);
            List<Curriculums> lists = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);
            if (lists.size() < 6) {
                throw new NoSuchElementException("Not enough chapters found");
            }

            List<Quiz> quizList = new ArrayList<>();

            // 객관식
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 1번째 - 객관식", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "1", QuizLevel.HARD, "정답1", "오답2", "오답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 2번째 - 객관식", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "2", QuizLevel.HARD, "오답1", "정답2", "오답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d-서브챕터%d: 초급자 1번째 - 객관식", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "3", QuizLevel.NORMAL, "오답1", "오답2", "정답3", "오답4", "", "", 0));
                quizList.add(createQuiz(lists.get(i), QuizType.NUM, String.format("테스트 문제-챕터 %d-서브챕터%d: 입문자 1번째 - 객관식", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "4", QuizLevel.EASY, "오답1", "오답2", "오답3", "정답4", "", "", 0));
            }

            // 단답형
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 1번째 - 단답형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "정답", QuizLevel.HARD, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 2번째 - 단답형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "정답", QuizLevel.HARD, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d-서브챕터%d: 초급자 1번째 - 단답형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "정답", QuizLevel.NORMAL, "", "", "", "","","",2));
                quizList.add(createQuiz(lists.get(i), QuizType.WORD, String.format("테스트 문제-챕터 %d-서브챕터%d: 입문자 1번째 - 단답형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "정답", QuizLevel.EASY, "", "", "", "","","",2));
            }

            // 코드 작성형
            for (int i = 0; i < lists.size(); i++) {
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 1번째 - 코드작성형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d-서브챕터%d: 중급자 2번째 - 코드작성형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "", QuizLevel.HARD, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d-서브챕터%d: 초급자 1번째 - 코드작성형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "", QuizLevel.NORMAL, "", "", "", "","1\n2","3",0));
                quizList.add(createQuiz(lists.get(i), QuizType.CODE, String.format("테스트 문제-챕터 %d-서브챕터%d: 입문자 1번째 - 코드작성형", lists.get(i).getChapterNum(), lists.get(i).getSubChapterNum()), "", QuizLevel.EASY, "", "", "", "","1\n2","3",0));
            }

            quizRepository.saveAll(quizList);
        }

        public void testInit() {

            Users user2 = userRepository.findById(2L).get();
            Curriculums root = curriculumRepository.findAllRootCurriculumList().get(0);

            List<Curriculums> subChapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);
            int testedSubChap = 4;
            int current = 0;
            Curriculums curriculum = subChapters.get(0);

            for (int i = 0; i < subChapters.size(); i++) {

                curriculum = subChapters.get(i);

                if (current < testedSubChap) {
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

                        Studies studiesEasy = Studies.createStudy(user2, curriculum, true,
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()), LevelType.EASY);
                        Studies studiesNormal = Studies.createStudy(user2, curriculum, true,
                                null,
                                String.format("챕터 %d: " + LevelType.NORMAL + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.NORMAL + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()), LevelType.NORMAL);
                        Studies studiesHard = Studies.createStudy(user2, curriculum, true,
                                null,
                                null,
                                String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 합격", curriculum.getSubChapterNum()), LevelType.HARD);

                        studyRepository.save(studiesEasy);
                        studyRepository.save(studiesNormal);
                        studyRepository.save(studiesHard);
                    } else {
                        Studies studies = Studies.createStudy(user2, curriculum, true,
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()), LevelType.EASY);
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

                        Studies studiesEasy = Studies.createStudy(user2, curriculum, false,
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()), LevelType.EASY);
                        Studies studiesNormal = Studies.createStudy(user2, curriculum, false,
                                null,
                                String.format("챕터 %d: " + LevelType.NORMAL + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.NORMAL + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()), LevelType.NORMAL);
                        Studies studiesHard = Studies.createStudy(user2, curriculum, false,
                                null,
                                null,
                                String.format("챕터 %d: " + LevelType.HARD + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 불합격", curriculum.getSubChapterNum()), LevelType.HARD);

                        studyRepository.save(studiesEasy);
                        studyRepository.save(studiesNormal);
                        studyRepository.save(studiesHard);
                        break;
                    } else {
                        Studies studiesEasy = Studies.createStudy(user2, curriculum, false,
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()),
                                String.format("챕터 %d: " + LevelType.EASY + "학습자료: " + curriculum.getCurriculumName() + " 테스트 내용입니다. - 초기 시험 미대상", curriculum.getSubChapterNum()), LevelType.EASY);
                        studyRepository.save(studiesEasy);
                    }
                }
            }
            // 커리큘럼 생성 테스트용 데이터
//            Studies studies = createStudy(user2, subChapters.get(16), 60L, null, false, LevelType.HARD);
//            studyRepository.save(studies);
        }

        public void chatInit() {
            Users user = userRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("User not found with id 2"));

            List<Curriculums> rootCurriculums = curriculumRepository.findAllRootCurriculumList();
            if (rootCurriculums.isEmpty()) {
                throw new NoSuchElementException("No root curriculum found");
            }
            Curriculums root = rootCurriculums.get(0);

            List<Curriculums> chapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);
            if (chapters.size() < 6) {
                throw new NoSuchElementException("Not enough chapters found");
            }

            // 챕터 1 - 서브 챕터 1에서 질문 1개
            Chats chat = createChatWithTime(user, chapters.get(0), "챕터 1 - 서브챕터 1에서의 질문1: 개념질문", "챕터 4에서의 답변1: 단일 답변", QuestionType.DEF, LocalDateTime.now(), 1);
            chatRepository.save(chat);

            // 챕터 2 - 서브 챕터 1에서 질문 3개
            chat = createChatWithTime(user, chapters.get(2), "챕터 2 - 서브챕터 1에서의 질문1: 개념질문", "챕터 6에서의 답변1: 단일 답변", QuestionType.DEF, LocalDateTime.now().plusMinutes(1), 1);
            chatRepository.save(chat);
            chat = createChatWithTime(user, chapters.get(2), "챕터 2 - 서브챕터 1에서의 질문2: 코드질문(1단계 부터 시작)", "1단계: 코드 단계적 답변\n\n2단계: 코드 단계적 답변 진행\n\n3단계: 코드 단계적 답변 진행\n\n4단계: 코드 단계적 답변 진행", QuestionType.CODE, LocalDateTime.now().plusMinutes(2), 1);
            chatRepository.save(chat);
            chat = createChatWithTime(user, chapters.get(2), "챕터 2 - 서브챕터 1에서의 질문3: 에러질문(3단계 까지 진행)", "1단계: 에러 단계적 답변\n\n2단계: 에러 단계적 답변 진행\n\n3단계: 단계적 답변 진행\n\n4단계: 에러 단계적 답변 진행", QuestionType.ERRORS, LocalDateTime.now().plusMinutes(3), 3);
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

                List<Curriculums> chapters = curriculumRepository.findAllByRootAndLeafNodeOrderByChapterNumAndSubChapterNum(root, true);

                for (Curriculums chapter : chapters) {
                    missions.add(createMission(100, MissionType.CHALLENGE, ServiceType.STUDY, "STUDY_" + root.getCurriculumName().toUpperCase() + "_CHAP_" + chapter.getChapterNum() + "_SUB_" + chapter.getSubChapterNum() + "_COMPLETE", "\"" + root.getCurriculumName() + ": " + chapter.getCurriculumName() + "\" 학습 통과하기", 1));
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
