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

    // 루트 커리큘럼 진행 현황 리스트 반환
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

            // 마지막 챕터 대상 Study 데이터 내에서 passed 가 false 인 행이 존재할 경우 -> 커리큘럼 학습 미완료
            boolean isNotCompleted = allByUser.stream().filter(i -> i.getCurriculum().getChapterNum() == root.getTotalChapterCount()
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
        List<Curriculums> subChapters = child.stream().filter(i -> i.isLeafNode()).toList();
        Deque<Curriculums> deque = new ArrayDeque<>(subChapters);

        List<Studies> studies = new ArrayList<>();

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
    private boolean chapterPass(Users user, Curriculums parent) {

        Optional<Studies> chapterStudyOptional = studyRepository.findByUserAndCurriculum(user, parent);

        if (chapterStudyOptional.isEmpty()) {
            log.error("study가 존재하지 않습니다. user={}, parent={}", user, parent);
            throw new IllegalArgumentException("study가 존재하지 않습니다.");
        }

        Studies chapterStudy = chapterStudyOptional.get();
        List<Studies> subChapterStudies = studyRepository.findAllByUserAndParent(user, parent);

        if (subChapterStudies.isEmpty()) {
            log.error("서브 챕터가 존재하지 않습니다. user={}, parent={}", user, parent);
            throw new IllegalArgumentException("서브 챕터가 존재하지 않습니다.");
        }

        // 통과 여부 확인
        boolean flag = true;

        for (Studies subChapterStudy : subChapterStudies) {
            if (!subChapterStudy.isPassed()) flag = false;
        }

        // 모든 서브 챕터가 통과일 시 부모 챕터도 통과 처리
        if (flag) {
            chapterStudy.setPassed(true);
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

        // 특정 서브 챕터 완료 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndSubChapterName(subChapter.getParent(), subChapter)));

        // 커리큘럼 학습 완료 공통 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));

        // 부모인 챕터 Study 통과 처리 업데이트
        boolean flag = chapterPass(user, subChapter.getParent());

        // 챕터가 pass 처리 되었을 경우,
        if (flag)  {
            // 대상 챕터 미션 완료 처리
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(subChapter)));
        }

        return true;
    }

    // 유저의 커리큘럼 학습 내용 로드
    @Transactional
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

        // study 데이터에 완전 최초 점근할 경우 null로 설정 되어있음 --> level 설정 로직
        if (study.getLevel() == null) {
            study.setLevel(dto.getLevelType());
        }

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
