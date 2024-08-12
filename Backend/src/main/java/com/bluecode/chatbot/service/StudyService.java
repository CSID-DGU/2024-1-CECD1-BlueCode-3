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
@Transactional
public class StudyService {

    private final ApiService apiService;
    private final StudyRepository studyRepository;
    private final CurriculumService curriculumService;
    private final CurriculumRepository curriculumRepository;
    private final UserRepository userRepository;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 루트 커리큘럼 진행 현황 리스트 반환
    public CurriculumRootResponseDto searchRootData(Long userId) {

        Optional<Users> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        Users user = userOptional.get();

        List<Studies> userRootStudy = studyRepository.findAllRootByUser(user);
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
            Optional<Studies> rootStudy = userRootStudy.stream().filter(i -> Objects.equals(i.getCurriculum().getCurriculumId(), root.getCurriculumId())).findFirst();

            // root 대상 study 데이터가 존재하지 않는다면
            if (rootStudy.isEmpty()) {
                elementDto.setStatus(StudyStatus.INIT);
                list.add(elementDto);
                continue;
            }

            // root의 학습 pass 여부 확인
            boolean passed = rootStudy.get().isPassed();

            if (passed) {
                // 대상 root의 pass == true 일 경우 학습 완료라 판단
                elementDto.setStatus(StudyStatus.COMPLETE);
            } else {
                // 대상 root의 pass == false 일 경우 학습중이라 판단
                elementDto.setStatus(StudyStatus.STUDYING);
            }
            list.add(elementDto);
        }

        dto.setList(list);
        return dto;
    }

    // 유저의 커리큘럼 학습 시작하기 위한 챕터 학습 데이터 생성
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

        if (!root.isRootNode()) {
            throw new IllegalArgumentException("루트 커리큘럼 id가 아닙니다.");
        }

        boolean validation = studyRepository.isExistByUserAndRoot(user, root);

        // 데이터 생성 여부 확인(true일 시 데이터 존재함을 의미)
        if (validation) {
            log.error("이미 해당 유저의 학습 대상 커리큘럼 데이터가 생성되어있습니다. user={}, root={}", user, root);
            throw new IllegalArgumentException("이미 해당 유저의 학습 대상 커리큘럼 데이터가 생성되어있습니다.");
        }

        // root 기반 자식 노드 전체 검색
        List<Curriculums> child = curriculumRepository.findAllByRoot(root);

        List<Curriculums> chapters = child.stream().filter(i -> !i.isRootNode() && !i.isLeafNode()).toList();
        List<Curriculums> subChapters = new ArrayList<>(child.stream().filter(i -> i.isLeafNode()).toList());
        subChapters.sort(null);
        Deque<Curriculums> deque = new ArrayDeque<>(subChapters);

        List<Studies> studies = new ArrayList<>();

        // 루트 학습 현황
        studies.add(Studies.createStudy(user, root, false, null, null, null, null));

        for (Curriculums chapter : chapters) {
            // 챕터 학습 현황
            studies.add(Studies.createStudy(user, chapter, false, null, null, null, null));

            // 챕터에 속하는 서브챕터 학습 현황
            while (!deque.isEmpty() && Objects.equals(deque.getFirst().getParent().getCurriculumId(), chapter.getCurriculumId())) {
                Curriculums subChapter = deque.removeFirst();
                studies.add(Studies.createStudy(user, subChapter, false, null, null, null, null));
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

        List<Studies> studies = studyRepository.findAllByUserAndRoot(user, root);

        // 챕터 커리큘럼 대상 리스트
        List<Studies> chapters = studies.stream().filter(i -> !i.getCurriculum().isLeafNode()).toList();

        // 서브 챕터 커리큘럼 대상 리스트
        List<Studies> subChapters = studies.stream().filter(i -> i.getCurriculum().isLeafNode()).toList();
        Deque<Studies> deque = new ArrayDeque<>(subChapters);

        log.info("chapters={}, subChapters={}", chapters, subChapters);
        log.info("deque={}", deque.size());

        List<CurriculumPassedElementDto> list = new ArrayList<>();

        for (Studies chapter : chapters) {
            CurriculumPassedElementDto chapterDto = new CurriculumPassedElementDto();

            // 챕터 레벨 데이터 구성
            chapterDto.setCurriculumId(chapter.getCurriculum().getCurriculumId());
            chapterDto.setCurriculumName(chapter.getCurriculum().getCurriculumName());
            chapterDto.setPassed(chapter.isPassed());

            List<CurriculumPassedElementDto> subChapterDtoList = new ArrayList<>();

            // 서브 챕터 데이터 구성
            while (!deque.isEmpty() && Objects.equals(deque.getFirst().getCurriculum().getParent().getCurriculumId(), chapter.getCurriculum().getCurriculumId())) {

                Studies subChapterStudy = deque.removeFirst();
                // 서브 챕터 dto 생성
                CurriculumPassedElementDto subChapterDto = new CurriculumPassedElementDto();
                subChapterDto.setCurriculumId(subChapterStudy.getCurriculum().getCurriculumId());
                subChapterDto.setCurriculumName(subChapterStudy.getCurriculum().getCurriculumName());
                subChapterDto.setPassed(subChapterStudy.isPassed());

                // 서브 챕터 dto 리스트에 추가
                subChapterDtoList.add(subChapterDto);
            }

            // 서브 챕터 dto 리스트 설정
            chapterDto.setSubChapters(subChapterDtoList);
            // list에 chapterDto 추가
            list.add(chapterDto);
        }

        CurriculumPassedDto responseDto = new CurriculumPassedDto();
        responseDto.setList(list);

        log.info("getCurriculumProgress returning: {}", responseDto);
        return responseDto;
    }

    // 부모 커리큘럼의 pass 처리 판정 로직(서브 챕터 -> 챕터)
    public boolean parentPass(Users user, Curriculums parent) {

        Optional<Studies> parentStudyOptional = studyRepository.findByUserAndCurriculum(user, parent);

        if (parentStudyOptional.isEmpty()) {
            log.error("study가 존재하지 않습니다. user={}, parent={}", user, parent);
            throw new IllegalArgumentException("study가 존재하지 않습니다.");
        }

        Studies parentStudy = parentStudyOptional.get();
        List<Studies> childStudyList = studyRepository.findAllByUserAndParent(user, parent);

        if (childStudyList.isEmpty()) {
            log.error("자식 study가 존재하지 않습니다. user={}, parent={}", user, parent);
            throw new IllegalArgumentException("자식 study가 존재하지 않습니다.");
        }

        // 통과 여부 확인
        boolean flag = true;

        for (Studies childStudy : childStudyList) {
            log.info("child 확인: {} flag: {}", childStudy.getCurriculum().getCurriculumName(), childStudy.isPassed());
            if (!childStudy.isPassed()) {
                flag = false;
                break;
            }
        }

        // 모든 자식 챕터가 통과일 시 부모 챕터도 통과 처리
        if (flag) {
            parentStudy.setPassed(true);
            studyRepository.saveAndFlush(parentStudy);
        }

        return flag;
    }

    // 유저의 서브 챕터 커리큘럼 학습 완료 처리
    public boolean subChapterPass(CurriculumPassCallDto dto) {

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> subChapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (subChapterOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 서브 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums subChapter = subChapterOptional.get();

        Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, subChapter);

        // study 데이터가 존재하지 않거나 level == null(../curriculums/text 를 선행하지 않은 경우)
        if (studyOptional.isEmpty() || studyOptional.get().getLevel() == null) {
            log.error("학습중이 아닌 서브 챕터입니다. user= {}, subChapter= {}", user.getUserId(), subChapter.getCurriculumId());
            throw new IllegalArgumentException("학습중이 아닌 서브 챕터입니다.");
        }

        Studies study = studyOptional.get();

        if (study.isPassed()) {
            log.error("이미 통과한 서브챕터 입니다. dto: {}", dto);
            throw new IllegalArgumentException("이미 통과한 서브챕터 입니다.");
        }

        // 해당 서브챕터 pass 처리
        study.setPassed(true);
        studyRepository.saveAndFlush(study);
        log.info("subChapter pass 처리 완료: {}, {}", study.getCurriculum().getCurriculumName(), study.isPassed());

        // 특정 서브 챕터 완료 미션 처리
        log.info("MissionConst: {}", MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter));
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter)));

        // 커리큘럼 학습 완료 공통 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));

        // 부모인 챕터 Study 통과 처리 업데이트
        boolean flag = parentPass(user, subChapter.getParent());

        // 챕터가 pass 처리 되었을 경우,
        if (flag)  {
            // 대상 챕터 학습 미션 완료 처리
            log.info("MissionConst: {}", MissionConst.createConstByRootName(subChapter.getParent()));
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(subChapter.getParent())));

            // 루트 Study 통과 처리 업데이트
            boolean flagRoot = parentPass(user, subChapter.getRoot());

            // 루트가 pass 처리 되었을 경우,
            if (flagRoot) {
                // 대상 루트 학습 미션 완료 처리
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(subChapter.getRoot())));
            }
        }

        return true;
    }

    // 유저의 커리큘럼 학습 내용 로드
    public StudyTextDto getCurriculumText(CurriculumTextCallDto dto) {
        log.info("getCurriculumText called with dto: {}", dto);

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> subChapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            log.error("유효하지 않은 유저 테이블 id 입니다.");
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (subChapterOptional.isEmpty()) {
            log.error("유효하지 않은 서브 챕터 커리큘럼 id 입니다.");
            throw new IllegalArgumentException("유효하지 않은 서브 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums subChapter = subChapterOptional.get();

        if (!subChapter.isLeafNode()) {
            log.error("서브챕터 커리큘럼이 아닙니다. subChapter={}", subChapter);
            throw new IllegalArgumentException("서브챕터 커리큘럼이 아닙니다.");
        }

        Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, subChapter);

        if (studyOptional.isEmpty()) {
            log.error("유효하지 않은 study 입니다.");
            throw new IllegalArgumentException("유효하지 않은 study 입니다.");
        }

        Studies study = studyOptional.get();

        // study 데이터에 완전 최초 접근의 경우,level = null 로 설정 되어있음 --> level 설정 로직
        setStudyLevel(study, dto.getTextType());

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        String text = createTextByTextType(study, dto.getTextType());
        studyRepository.save(study);

        StudyTextDto responseDto = new StudyTextDto();
        responseDto.setText(text);

        log.info("getCurriculumText returning: {}", responseDto);
        return responseDto;
    }

    // 최초 접근에 대한 level 설정 로직
    private void setStudyLevel(Studies study, TextType type) {
        if (study.getLevel() == null) {
            if (type == TextType.DEF) {
                study.setLevel(LevelType.EASY);
            } else if (type == TextType.CODE) {
                study.setLevel(LevelType.NORMAL);
            } else if (type == TextType.QUIZ) {
                study.setLevel(LevelType.HARD);
            } else {
                log.error("유효하지 않은 TextType 입니다. type: {}", type);
                throw new IllegalArgumentException("유효하지 않은 TextType 입니다. " + type);
            }
        }
    }


    // textType에 따른 text 열 지정 및 생성
    private String createTextByTextType(Studies study, TextType textType) {

        if (textType == TextType.DEF) {

            if (study.getTextDef() == null) {
                String subChapterKeyword = study.getCurriculum().getCurriculumName();
                log.info("subChapterKeyword: {}", subChapterKeyword);
                String generatedResponse = requestGptText(subChapterKeyword, study.getCurriculum().getCurriculumId(), textType); // 커리큘럼 ID로 루트 커리큘럼 이름을 조회
                log.info("generatedResponse: {}", generatedResponse);
                String generatedText = apiService.extractContentFromResponse(generatedResponse); // json 형식 응답을 text로 추출
                log.info("generatedText: {}", generatedText);
                study.setTextDef(generatedText);
            }
            return study.getTextDef();

        } else if (textType == TextType.CODE) {

            if (study.getTextCode() == null) {
                String subChapterKeyword = study.getCurriculum().getCurriculumName();
                log.info("subChapterKeyword: {}", subChapterKeyword);
                String generatedResponse = requestGptText(subChapterKeyword, study.getCurriculum().getCurriculumId(), textType); // 커리큘럼 ID로 루트 커리큘럼 이름을 조회
                log.info("generatedResponse: {}", generatedResponse);
                String generatedText = apiService.extractContentFromResponse(generatedResponse); // json 형식 응답을 text로 추출
                log.info("generatedText: {}", generatedText);
                study.setTextCode(generatedText);
            }
            return study.getTextCode();

        } else if (textType == TextType.QUIZ) {

            if (study.getTextQuiz() == null) {
                String subChapterKeyword = study.getCurriculum().getCurriculumName();
                log.info("subChapterKeyword: {}", subChapterKeyword);
                String generatedResponse = requestGptText(subChapterKeyword, study.getCurriculum().getCurriculumId(), textType); // 커리큘럼 ID로 루트 커리큘럼 이름을 조회
                log.info("generatedResponse: {}", generatedResponse);
                String generatedText = apiService.extractContentFromResponse(generatedResponse); // json 형식 응답을 text로 추출
                log.info("generatedText: {}", generatedText);
                study.setTextQuiz(generatedText);
            }
            return study.getTextQuiz();

        } else {
            log.error("유효하지 않은 textType={}" ,textType);
            throw new IllegalArgumentException("유효하지 않은 textType");
        }
    }

    // GPT API를 호출하여 학습 내용 생성
    private String requestGptText(String keyword, Long curriculumId, TextType textType) {
        Curriculums currentCurriculum = curriculumRepository.findById(curriculumId).orElse(null);

        // 루트 커리큘럼 정보 조회 (pLang 추출)
        String pLang = currentCurriculum != null ? currentCurriculum.getRoot().getCurriculumName() : "알 수 없음";
        log.info("pLang: {}", pLang);

        // 현재 커리큘럼 이름 조회
        String chapName = currentCurriculum != null ? currentCurriculum.getParent().getCurriculumName() : "알 수 없음";
        log.info("chapName: {}", chapName);

        // 프롬프트 조립
        String prompt = String.format(
                "%s의 %s 챕터의 키워드 '%s'에 관한 %s",
                pLang,
                chapName,
                keyword,
                getPromptByType(textType)
        );

        // API 호출
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));
        log.info("studyTextPrompt: {}", prompt);
        return apiService.sendPostRequest(messages, curriculumId);
    }

    private String getPromptByType(TextType textType) {
        return switch (textType) {
            case DEF -> "모든 이론에 대한 최대한 많은 정보를 서술, 총 1500자 이내";
            case CODE -> "간단한 예제 코드를 5개 이상 서술, 총 1500자 이내";
            case QUIZ -> "코딩 테스트 심화 기출 유형의 문제 소개와 자세한 설명 및 답안 코드(상세한 주석이 포함) 제시, 총 1500자 이내";
        };
    }
}
