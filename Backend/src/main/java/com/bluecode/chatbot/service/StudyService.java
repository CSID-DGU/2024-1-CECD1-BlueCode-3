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
            chapterDto.setLevel(chapter.getLevel());
            chapterDto.setPassed(chapter.isPassed());

            List<CurriculumPassedElementDto> subChapterDtoList = new ArrayList<>();

            // 서브 챕터 데이터 구성
            while (!deque.isEmpty() && Objects.equals(deque.getFirst().getCurriculum().getParent().getCurriculumId(), chapter.getCurriculum().getCurriculumId())) {

                Studies subChapterStudy = deque.removeFirst();
                // 서브 챕터 dto 생성
                CurriculumPassedElementDto subChapterDto = new CurriculumPassedElementDto();
                subChapterDto.setCurriculumId(subChapterStudy.getCurriculum().getCurriculumId());
                subChapterDto.setCurriculumName(subChapterStudy.getCurriculum().getCurriculumName());
                subChapterDto.setLevel(subChapterStudy.getLevel());
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


    // 부모 커리큘럼의 pass 처리 판정 로직(서브 챕터 -> 챕터, 챕터 -> 루트)
    public boolean chapterPass(CurriculumPassCallDto dto) {

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums chapter = chapterOptional.get();
        Optional<Studies> chapterStudyOptional = studyRepository.findByUserAndCurriculum(user, chapter);

        if (chapterStudyOptional.isEmpty()) {
            log.error("study가 존재하지 않습니다. user={}, chapter={}", user, chapter);
            throw new IllegalArgumentException("study가 존재하지 않습니다.");
        }

        Studies chapterStudy = chapterStudyOptional.get();
        List<Studies> childStudyList = studyRepository.findAllByUserAndParent(user, chapter);

        // 대상이 root가 아니면 next 챕터의 next의 level을 dto에서 제공한 값으로 변경하는데 이용
        if (!chapterStudy.getCurriculum().isRootNode() && !chapterStudy.getCurriculum().isLeafNode() && dto.getLevelType() == null) {
            log.error("챕터 pass 처리는 LevelType이 필수입니다. dto: {}", dto);
            throw new IllegalArgumentException("챕터 pass 처리는 LevelType이 필수입니다.");
        }

        if (childStudyList.isEmpty()) {
            log.error("자식 study가 존재하지 않습니다. user={}, chapter={}", user, chapter);
            throw new IllegalArgumentException("자식 study가 존재하지 않습니다.");
        }

        //챕터: 서브 챕터(루트: 챕터)들 학습 완료 여부 확인
        boolean flag = true;

        for (Studies childStudy : childStudyList) {
            log.info("child 확인: {} flag: {}", childStudy.getCurriculum().getCurriculumName(), childStudy.isPassed());
            if (!childStudy.isPassed()) {
                flag = false;
                break;
            }
        }

        if (flag) {
            // 모든 자식 챕터가 통과일 시 부모 챕터도 통과 처리
            chapterStudy.setPassed(true);
            studyRepository.save(chapterStudy);

            // 챕터가 pass 처리 되었을 경우,대상 챕터 학습 미션 완료 처리
            log.info("MissionConst: {}", MissionConst.createConstByRootName(chapterStudy.getCurriculum()));
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(chapterStudy.getCurriculum())));

            Optional<Studies> next = studyRepository.findByUserAndCurriculum(user, chapter.getNext());

            // 다음 챕터에 대한 학습이 존재하면
            if (next.isPresent()) {
                // 다음 챕터 난이도는 dto의 level로 설정
                Studies nextChapterStudy = next.get();
                nextChapterStudy.setLevel(dto.getLevelType());
                studyRepository.save(nextChapterStudy);
            }

            // 현재 대상 study가 root가 아니면 root Study 통과 처리 업데이트
            if (!chapterStudy.getCurriculum().isRootNode()) {
                CurriculumPassCallDto rootDto = new CurriculumPassCallDto();
                rootDto.setUserId(user.getUserId());
                rootDto.setCurriculumId(chapter.getRoot().getCurriculumId());
                boolean flagRoot = chapterPass(rootDto);

                // 루트가 pass 처리 되었을 경우,
                if (flagRoot) {
                    // 대상 루트 학습 미션 완료 처리
                    eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(chapterStudy.getCurriculum().getRoot())));
                }
            }
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
        studyRepository.save(study);
        log.info("subChapter pass 처리 완료: {}, {}", study.getCurriculum().getCurriculumName(), study.isPassed());

        // 특정 서브 챕터 완료 미션 처리
        log.info("MissionConst: {}", MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter));
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter)));

        // 서브 챕터 학습 완료 공통 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));

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

        // study 데이터에 완전 최초 접근의 경우,level = null 로 설정 되어있음 --> parent와 같은 level로 설정 로직
        if (study.getLevel() == null) {
            Optional<Studies> parent = studyRepository.findByUserAndCurriculum(user, subChapter.getParent());
            Studies chapterStudy = parent.get();
            // 최초 접근에 대한 level 설정 로직
            study.setLevel(chapterStudy.getLevel());
        }

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        String text = createTextByTextType(study, dto.getTextType());
        studyRepository.save(study);

        StudyTextDto responseDto = new StudyTextDto();
        responseDto.setText(text);

        log.info("getCurriculumText returning: {}", responseDto);
        return responseDto;
    }


    // 해당 챕터의 서브 챕터 학습 완료 여부 확인(testService 내 활용)
    public boolean validateChapterStudy(Users user, Curriculums chapter) {

        List<Studies> subChapterStudies = studyRepository.findAllByUserAndParent(user, chapter);

        if (subChapterStudies.isEmpty()) {
            log.error("서브 챕터 커리큘럼 대상 Study가 존재하지 않습니다. user: {}, subChapter 대상 study 개수: {}", user.getUserId(), subChapterStudies.size());
            throw new IllegalArgumentException("서브 챕터 커리큘럼 대상 Study가 존재하지 않습니다.");
        }

        // 하나의 서브챕터라도 passed = false일 경우, false 리턴
        for (Studies subChapterStudy : subChapterStudies) {
            if (!subChapterStudy.isPassed()) {
                return false;
            }
        }

        // 모든 서브챕터의 passed = true일 경우, true 리턴
        return true;
    }

    // textType에 따른 text 열 지정 및 생성
    /**
     * 서브 챕터에서 text를 리턴하는 함수.
     * 접근 가능 text:
     * EASY(DEF, CODE, QUIZ),
     * NORMAL(CODE, QUIZ),
     * HARD(QUIZ)
     * (만약 대상 text가 null일 경우, gpt로 생성)
     *
     * @param study: 대상 study
     * @param textType: 대상 textType
     * @return study 내 존재하는 대상 text
     */
    private String createTextByTextType(Studies study, TextType textType) {

        if (textType == TextType.DEF) {

            if (study.getLevel() == LevelType.NORMAL || study.getLevel() == LevelType.HARD) {
                if (!study.isPassed()) {
                    log.error("{} 에서 DEF는 생략됩니다.", study.getLevel());
                    throw new IllegalArgumentException(study.getLevel() + " 에서 DEF는 생략됩니다.");
                }
            }

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

            if (study.getLevel() == LevelType.HARD) {
                if (!study.isPassed()) {
                    log.error("{} 에서 CODE는 생략됩니다.", study.getLevel());
                    throw new IllegalArgumentException(study.getLevel() + " 에서 CODE는 생략됩니다.");
                }
            }

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

        // 루트 커리큘럼 이름 조회
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
            case QUIZ -> "코딩 테스트 심화 기출 유형의 문제 소개와 자세한 설명 및 답안 코드(상세한 주석 포함) 제시, 총 1500자 이내";
        };
    }

    // 초기 테스트 진행 중, 대상 챕터의 테스트는 통과하여 해당 챕터와 서브챕터 대상 study의 passed = true, level을 설정하는 과정
    public initChapterPassResponseDto initPass(initChapterPassRequestDto dto) {

        log.info("initPass dto: {}", dto);

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            log.error("유효하지 않은 유저 테이블 id 입니다. dto: {}", dto);
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            log.error("유효하지 않은 챕터 커리큘럼 id 입니다. dto: {}", dto);
            throw new IllegalArgumentException("유효하지 않은 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums chapter = chapterOptional.get();

        // 검색한 curriculum이 chapter가 아닐 시, Exception 발생
        if (chapter.isRootNode() || chapter.isLeafNode()) {
            log.error("대상 커리큘럼은 챕터가 아닙니다. name: {}, isRoot: {}, isLeaf: {}", chapter.getCurriculumName(), chapter.isRootNode(), chapter.isLeafNode());
            throw new IllegalArgumentException("대상 커리큘럼은 챕터가 아닙니다.");
        }

        Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, chapter);

        if (studyOptional.isEmpty()) {
            log.error("유효하지 않은 study 입니다.");
            throw new IllegalArgumentException("유효하지 않은 study 입니다.");
        }

        List<Studies> totalStudy = new ArrayList<>();

        // 챕터 대상 study
        Studies chapterStudy = studyOptional.get();
        totalStudy.add(chapterStudy);

        // passed = true & dto에서 전달한 level로 저장(null --> dto.getLevel())
        chapterStudy.setLevel(dto.getLevel());
        chapterStudy.setPassed(true);



        // 대상 챕터 내 서브챕터 study 리스트에서 위와 동일한 로직 처리
        List<Studies> subChapterStudies = studyRepository.findAllByUserAndParent(user, chapter);
        for (Studies subChapterStudy : subChapterStudies) {
            subChapterStudy.setPassed(true);
            subChapterStudy.setLevel(dto.getLevel());
            totalStudy.add(subChapterStudy);

            // 특정 서브 챕터 완료 미션 처리
            log.info("MissionConst: {}", MissionConst.createConstByRootAndSubChapterName(subChapterStudy.getCurriculum().getParent(), subChapterStudy.getCurriculum()));
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapterStudy.getCurriculum().getParent(), subChapterStudy.getCurriculum())));

            // 학습 완료 공통 미션 처리
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));
        }

        studyRepository.saveAll(totalStudy);

        // 만약 이전 챕터가 testable = false일 경우, 연쇄적으로 pass 처리 필요
        if (chapterStudy.getCurriculum().getBefore()!=null && !chapterStudy.getCurriculum().getBefore().isTestable()) {
            initPassByBeforeChapter(user, chapterStudy.getCurriculum().getBefore());
        }

        // 완료 처리 dto 리턴
        initChapterPassResponseDto responseDto = new initChapterPassResponseDto();
        responseDto.setComplete(true);

        return responseDto;
    }

    // 대상 챕터의 이전 챕터가 testable = false일 경우, 연쇄적으로 pass 처리 로직
    private boolean initPassByBeforeChapter(Users user, Curriculums beforeChapter) {

        Curriculums recursionCurriculum = beforeChapter;

        // 이전 챕터의 testable = false 일 때 반복
        while (recursionCurriculum != null && !recursionCurriculum.isTestable()) {
            Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, recursionCurriculum);

            if (studyOptional.isEmpty()) {
                log.error("유효하지 않은 study 입니다.");
                throw new IllegalArgumentException("유효하지 않은 study 입니다.");
            }

            // 챕터 대상 study
            Studies chapterStudy = studyOptional.get();

            // passed = true & dto에서 전달한 level로 저장(null --> dto.getLevel())
            chapterStudy.setLevel(LevelType.EASY);
            chapterStudy.setPassed(true);
            studyRepository.save(chapterStudy);

            // 대상 챕터가 pass 처리 되었을 경우,대상 챕터 학습 미션 완료 처리
            log.info("MissionConst: {}", MissionConst.createConstByRootName(chapterStudy.getCurriculum()));
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(chapterStudy.getCurriculum())));

            // 대상 챕터 내 서브챕터 study 리스트에서 위와 동일한 로직 처리
            List<Studies> subChapterStudies = studyRepository.findAllByUserAndParent(user, recursionCurriculum);
            for (Studies subChapterStudy : subChapterStudies) {
                subChapterStudy.setPassed(true);
                subChapterStudy.setLevel(LevelType.EASY);
                studyRepository.save(subChapterStudy);

                // 특정 서브 챕터 완료 미션 처리
                log.info("MissionConst: {}", MissionConst.createConstByRootAndSubChapterName(subChapterStudy.getCurriculum().getParent(), subChapterStudy.getCurriculum()));
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapterStudy.getCurriculum().getParent(), subChapterStudy.getCurriculum())));

                // 챕터 학습 완료 공통 미션 처리
                eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));
            }

            // 이전 챕터로 계속 진행
            recursionCurriculum = recursionCurriculum.getBefore();
        }

        return true;
    }

    // 초기 테스트를 종료할 때, 종료 시점 챕터 대상 Study 내 level 설정 로직
    public initChapterPassResponseDto initComplete(initChapterPassRequestDto dto) {

        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (userOptional.isEmpty()) {
            log.error("유효하지 않은 유저 테이블 id 입니다. dto: {}", dto);
            throw new IllegalArgumentException("유효하지 않은 유저 테이블 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            log.error("유효하지 않은 챕터 커리큘럼 id 입니다. dto: {}", dto);
            throw new IllegalArgumentException("유효하지 않은 챕터 커리큘럼 id 입니다.");
        }

        Users user = userOptional.get();
        Curriculums chapter = chapterOptional.get();

        // 검색한 curriculum이 chapter가 아닐 시, Exception 발생
        if (chapter.isRootNode() || chapter.isLeafNode()) {
            log.error("대상 커리큘럼은 챕터가 아닙니다. name: {}, isRoot: {}, isLeaf: {}", chapter.getCurriculumName(), chapter.isRootNode(), chapter.isLeafNode());
            throw new IllegalArgumentException("대상 커리큘럼은 챕터가 아닙니다.");
        }

        Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, chapter);

        if (studyOptional.isEmpty()) {
            log.error("유효하지 않은 study 입니다.");
            throw new IllegalArgumentException("유효하지 않은 study 입니다.");
        }

        // 챕터 대상 study
        Studies chapterStudy = studyOptional.get();

        // 초기 테스트가 종료되었을 때, 현재 챕터보다 이전 챕터의 testable = false일 경우, testable = false가 최초로 나타나는 챕터가 시작점이 되어야 함.
        // 예시: 챕터1(testable = false) - 챕터2(testable = false) - 챕터3(testable = true) 이고, 챕터 3에서 해당 method 호출 시,
        // 결과: 챕터1(testable = false, passed = false, leve = EASY) - 챕터2(testable = false, passed = false, leve = null) - 챕터3(testable = false, passed = false, leve = null)
        // 즉, 1챕터의 level만 설정이 되어야 함.
        if (chapterStudy.getCurriculum().getBefore() != null && chapterStudy.getCurriculum().getBefore().isTestable() == false) {
            initCompleteForBefore(user, chapterStudy.getCurriculum().getBefore());
        } else {
            // passed = true & dto에서 전달한 level로 저장(null --> dto.getLevel())
            chapterStudy.setLevel(dto.getLevel());
        }

        // 완료 처리 dto 리턴
        initChapterPassResponseDto responseDto = new initChapterPassResponseDto();
        responseDto.setComplete(true);
        return responseDto;
    }

    // 초기 테스트가 종료되었을 때, 현재 챕터보다 이전 챕터의 testable = false일 경우, 해당 챕터가 시작점을 탐색하고 level을 설정하는 로직
    private boolean initCompleteForBefore(Users user, Curriculums beforeChapter) {
        Curriculums recursionCurriculum = beforeChapter;

        // 이전 챕터가 존재하고 testable = false 일 때 반복
        while (recursionCurriculum != null && !recursionCurriculum.isTestable()) {
            Optional<Studies> studyOptional = studyRepository.findByUserAndCurriculum(user, recursionCurriculum);

            if (studyOptional.isEmpty()) {
                log.error("유효하지 않은 study 입니다.");
                throw new IllegalArgumentException("유효하지 않은 study 입니다.");
            }

            if (recursionCurriculum.getBefore() != null) {
                if (recursionCurriculum.getBefore().isTestable()) {
                    // 이전 챕터의 testable = true일 경우, 현재 챕터가 시작 지점이 됨
                    // 챕터 대상 study
                    Studies chapterStudy = studyOptional.get();

                    // dto에서 전달한 level로 저장(null --> dto.getLevel())
                    chapterStudy.setLevel(LevelType.EASY);
                    studyRepository.save(chapterStudy);
                    return true;
                } else {
                    // 이전 챕터로 계속 진행
                    recursionCurriculum = recursionCurriculum.getBefore();
                }
            } else {
                // 이전챕터가 존재하지 않을 경우, 1챕터가 시작지점이 됨
                // 챕터 대상 study
                Studies chapterStudy = studyOptional.get();

                // dto에서 전달한 level로 저장(null --> dto.getLevel())
                chapterStudy.setLevel(LevelType.EASY);
                studyRepository.save(chapterStudy);
                return true;
            }
        }

        return false;
    }
}
