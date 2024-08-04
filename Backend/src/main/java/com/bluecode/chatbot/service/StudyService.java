package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import com.bluecode.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {

    private final ApiService apiService;
    private final StudyRepository studyRepository;
    private final CurriculumService curriculumService;
    private final CurriculumRepository curriculumRepository;
    private final UserRepository userRepository;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 루트 커리큘럼 리스트 반환
    public CurriculumRootResponseDto searchRootData(Long userId) {

        Optional<Users> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        Users user = userOptional.get();

        List<Studies> allByUser = studyRepository.findAllByUser(user);
        List<Curriculums> roots = curriculumRepository.findAllRootCurriculumList();

        if (roots.isEmpty()) {
            throw new IllegalArgumentException("루트 커리큘럼이 존재하지 않습니다.");
        }

        CurriculumRootResponseDto dto = new CurriculumRootResponseDto();
        List<CurriculumRootElementDto> list = new ArrayList<>();

        for (Curriculums root : roots) {

            CurriculumRootElementDto elementDto = new CurriculumRootElementDto();
            elementDto.setRootId(root.getCurriculumId());
            elementDto.setTitle(root.getCurriculumName());

            // 학습 중인지 확인하는 로직
            List<Studies> rootStudy = allByUser.stream().filter(i -> Objects.equals(i.getCurriculum().getParent().getCurriculumId(), root.getCurriculumId())).toList();

            // 마지막 챕터 대상 난이도가 easy 인 Study 데이터 내에서 passed 가 false 인 행이 존재할 경우 -> 커리큘럼 학습 미완료
            boolean isNotCompleted = allByUser.stream().filter(i -> i.getCurriculum().getChapterNum() == root.getTotalChapterCount()
                    && i.getLevel().equals(LevelType.EASY)
                    && !i.isPassed()).count() == 0;

            if (rootStudy.isEmpty()) {
                // root 대상 study 데이터가 존재하지 않는다면
                elementDto.setStatus(StudyStatus.INIT);
            } else if (isNotCompleted) {
                // 마지막 챕터 대상 EASY 난이도 Study 내 pass == false 일 경우 학습중이라 판단
                elementDto.setStatus(StudyStatus.STUDYING);
            } else {
                elementDto.setStatus(StudyStatus.COMPLETE);
            }
            list.add(elementDto);
        }

        dto.setList(list);
        return dto;
    }

    // 유저의 커리큘럼 학습 시작하기 위한 챕터 학습 데이터 생성
    @Transactional
    public CurriculumChapResponseDto createCurriculumStudyData(DataCallDto dto) {

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> rootOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (rootOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 루트 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums root = rootOptional.get();

        if (root.getParent() != null) {
            throw new IllegalArgumentException("루트 커리큘럼 id가 아닙니다.");
        }

        boolean validation = studyRepository.isExistByUserAndRoot(user, root);

        // 데이터 생성 여부 확인
        if (!validation) {
            log.error("이미 해당 유저의 학습 대상 커리큘럼 데이터가 생성되어있습니다. user={}, root={}", user, root);
            throw new IllegalArgumentException("이미 해당 유저의 학습 대상 커리큘럼 데이터가 생성되어있습니다.");
        }

        // root 기반 자식 노드 전체 검색
        List<Curriculums> child = curriculumRepository.findAllByRoot(root);

        List<Curriculums> subChapters = child.stream().filter(i -> i.isLeafNode()).toList();

        List<Studies> studies = new ArrayList<>();

        // testable 조건에 따른 study 데이터 생성
        for (Curriculums chapter : subChapters) {
            if (chapter.isTestable()) {
                studies.add(Studies.createStudy(user, chapter, false, null, null, null, LevelType.EASY));
                studies.add(Studies.createStudy(user, chapter, false, null, null, null, LevelType.NORMAL));
                studies.add(Studies.createStudy(user, chapter, false, null, null, null, LevelType.HARD));
            } else {
                studies.add(Studies.createStudy(user, chapter, false, null, null, null, LevelType.EASY));
            }
        }

        // 생성된 study 데이터 저장
        studyRepository.saveAll(studies);

        // 대상 커리큘럼의 챕터 정보를 리턴 로직
        CurriculumChapCallDto callDto = new CurriculumChapCallDto();
        callDto.setCurriculumId(dto.getCurriculumId());

        return curriculumService.getCurriculumChapters(callDto);
    }

    // 유저의 커리큘럼 진행 현황 로드
    public CurriculumPassedDto getCurriculumProgress(DataCallDto dto) {
        log.info("getCurriculumProgress called with dto: {}", dto);

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> rootOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (rootOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 루트 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums root = rootOptional.get();

        if (root.getParent() != null) {
            throw new IllegalArgumentException("루트 커리큘럼 id가 아닙니다.");
        }

        List<Studies> studies = studyRepository.findAllEasyStudiesByUserAndRoot(user, root);

        List<CurriculumPassedElementDto> progressList = studies.stream().map(study -> {
            CurriculumPassedElementDto elementDto = new CurriculumPassedElementDto();
            elementDto.setCurriculumId(study.getCurriculum().getCurriculumId());
            elementDto.setCurriculumName(study.getCurriculum().getCurriculumName());
            elementDto.setPassed(study.isPassed());
            return elementDto;
        }).toList();

        CurriculumPassedDto responseDto = new CurriculumPassedDto();
        responseDto.setList(progressList);

        log.info("getCurriculumProgress returning: {}", responseDto);
        return responseDto;
    }

    // 유저의 커리큘럼 학습 완료 처리
    public String chapterPass(CurriculumPassCallDto dto) {

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 루트 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums chapter = chapterOptional.get();

        if (chapter.isTestable()) {

            List<Studies> chapters = studyRepository.findAllByUserAndCurriculum(user, chapter);

            if (chapters.isEmpty() || chapters.size() < 3) {
                log.error("학습중인 챕터가 아닙니다. chapters={}", chapters);
                throw new IllegalArgumentException("학습중인 챕터가 아닙니다.");
            }

            Studies studyEasy = chapters.stream().filter(s -> s.getLevel().equals(LevelType.EASY)).findFirst().get();
            Studies studyNormal = chapters.stream().filter(s -> s.getLevel().equals(LevelType.NORMAL)).findFirst().get();
            Studies studyHard = chapters.stream().filter(s -> s.getLevel().equals(LevelType.HARD)).findFirst().get();

            if (dto.getCurrentLevel() == LevelType.HARD) {
                if (dto.getNextLevel() == LevelType.HARD) {
                    studyHard.setPassed(true);
                    studyRepository.save(studyHard);
                } else {
                    log.error("유효하지 않은 currentLevel 과 NextLevel 입니다. currentLevel: {}, nextLevel: {}", dto.getCurrentLevel(), dto.getNextLevel());
                    throw new IllegalArgumentException("유효하지 않은 currentLevel 과 NextLevel 입니다.");
                }

            } else if (dto.getCurrentLevel() == LevelType.NORMAL) {
                if (dto.getNextLevel() == LevelType.HARD) {
                    studyNormal.setPassed(true);
                    studyHard.setPassed(true);
                    studyRepository.save(studyNormal);
                    studyRepository.save(studyHard);
                } else if (dto.getNextLevel() == LevelType.NORMAL) {
                    studyNormal.setPassed(true);
                    studyRepository.save(studyNormal);
                } else {
                    log.error("유효하지 않은 currentLevel 과 NextLevel 입니다. currentLevel: {}, nextLevel: {}", dto.getCurrentLevel(), dto.getNextLevel());
                    throw new IllegalArgumentException("유효하지 않은 currentLevel 과 NextLevel 입니다.");
                }
            } else if (dto.getCurrentLevel() == LevelType.EASY) {
                if (dto.getNextLevel() == LevelType.HARD) {
                    studyEasy.setPassed(true);
                    studyNormal.setPassed(true);
                    studyHard.setPassed(true);
                    studyRepository.save(studyEasy);
                    studyRepository.save(studyNormal);
                    studyRepository.save(studyHard);
                } else if (dto.getNextLevel() == LevelType.NORMAL) {
                    studyEasy.setPassed(true);
                    studyNormal.setPassed(true);
                    studyRepository.save(studyEasy);
                    studyRepository.save(studyNormal);
                } else if (dto.getNextLevel() == LevelType.EASY) {
                    // 테스트 불합격(최소 NORMAL 까지는 pass 해야함)
                    return "챕터 학습 미완료";
                }
            }

        } else if (!chapter.isTestable()) {
            List<Studies> studyValid = studyRepository.findAllByUserAndCurriculum(user, chapter);

            if (studyValid.isEmpty()) {
                log.error("학습중인 챕터가 아닙니다. user={}, chapter={}", user, chapter);
                throw new IllegalArgumentException("학습중인 챕터가 아닙니다.");
            }

            // 통과 처리
            Studies study = studyValid.get(0);
            study.setPassed(true);

            studyRepository.save(study);
        }

        // 특정 챕터 완료 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(chapter.getParent(), chapter)));

        // 커리큘럼 학습 완료 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));

        return "챕터 학습 완료";
    }

    // 유저의 커리큘럼 학습 내용 로드
    @Transactional
    public StudyTextDto getCurriculumText(CurriculumTextCallDto dto) {
        log.info("getCurriculumText called with dto: {}", dto);

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            log.error("유효하지 않은 유저 테이블 id 입니다.");
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            log.error("유효하지 않은 커리큘럼 id 입니다.");
            throw new IllegalArgumentException("유효하지 않은 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums subChapter = chapterOptional.get();

        if (!subChapter.isLeafNode()) {
            log.error("서브챕터 커리큘럼이 아닙니다. subChapter={}", subChapter);
            throw new IllegalArgumentException("서브챕터 커리큘럼이 아닙니다.");
        }

        Optional<Studies> studyOptional = studyRepository.findByCurriculumAndUserAndLevel(subChapter, user, dto.getLevelType());

        if (studyOptional.isEmpty()) {
            log.error("유효하지 않은 study 입니다.");
            throw new IllegalArgumentException("유효하지 않은 study 입니다.");
        }

        Studies study = studyOptional.get();

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        String text = createTextByTextType(study, dto.getTextType());

        StudyTextDto responseDto = new StudyTextDto();
        responseDto.setText(text);

        log.info("getCurriculumText returning: {}", responseDto);
        return responseDto;
    }

    // textType에 따른 text 열 지정 및 생성
    private String createTextByTextType(Studies study, TextType textType) {

        if (textType == TextType.DEFS) {

            if (study.getTextDef() == null) {
                // TODO GPT API 로직 재구성 필요
//                String fullKeyword = chapter.getCurriculumName() + ": " + keyword;
//                String generatedResponse = requestGptText(fullKeyword, dto.getCurriculumId()); // 커리큘럼 ID로 루트 커리큘럼 이름을 조회
//                String generatedText = apiService.extractContentFromResponse(generatedResponse); // json 형식 응답을 text로 추출
//                study.setText(generatedText);
                study.setTextDef("GPT 생성 text: 이론");
            }
            return study.getTextDef();

        } else if (textType == TextType.CODE) {

            if (study.getTextCode() == null) {
                // TODO GPT API 로직 재구성 필요
                study.setTextCode("GPT 생성 text: 실습");
            }
            return study.getTextDef();

        } else if (textType == TextType.QUIZ) {

            if (study.getTextQuiz() == null) {
                // TODO GPT API 로직 재구성 필요
                study.setTextQuiz("GPT 생성 text: 연습문제");
            }
            return study.getTextQuiz();

        } else {
            log.error("유효하지 않은 textType={}" ,textType);
            throw new IllegalArgumentException("유효하지 않은 textType");
        }
    }

    // GPT API를 호출하여 학습 내용 생성
    private String requestGptText(String keyword, Long curriculumId) {
        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of("role", "user", "content", "다음 키워드에 대해 자세한 학습 내용을 생성해줘. (키워드: " + keyword + ")"));

        return apiService.sendPostRequest(messages, curriculumId);
    }
}