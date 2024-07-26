package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.*;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import com.bluecode.chatbot.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {

    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final CurriculumRepository curriculumRepository;
    private final CurriculumService curriculumService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.key}")
    private String apiKey;

    // 미션 처리를 위한 클래스
    private final ApplicationEventPublisher eventPublisher;

    // 유저의 커리큘럼 진행 현황 로드
    public CurriculumPassedDto getCurriculumProgress(DataCallDto dto) {
        log.info("getCurriculumProgress called with dto: {}", dto);


        //List<Studies> studies = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());

        List<Studies> studies=studyRepository.findAllByUserId(dto.getUserId());

        List<CurriculumPassedElementDto> progressList = studies.stream().map(study -> {
            CurriculumPassedElementDto elementDto = new CurriculumPassedElementDto();
            elementDto.setCurriculumId(study.getCurriculum().getCurriculumId());
            elementDto.setCurriculumName(study.getCurriculum().getCurriculumName());
            elementDto.setPassed(study.isPassed());
            elementDto.setLevelType(study.getLevel());
            return elementDto;
        }).collect(Collectors.toList());

        CurriculumPassedDto responseDto = new CurriculumPassedDto();
        responseDto.setList(progressList);

        log.info("getCurriculumProgress returning: {}", responseDto);
        return responseDto;
    }

    // 유저의 커리큘럼 학습 결과 통과 처리 및 추가 난이도 Study 데이터 생성
    public void curriculumPass(CurriculumPassCallDto dto) {

        // hard 가 아니면 같은 level 을 설정하면 안됨
        if (dto.getCurrentLevel().equals(dto.getNextLevel()) && dto.getCurrentLevel() != LevelType.HARD) {
            throw new IllegalArgumentException(String.format("동일한 level 설정은 불가합니다. dto: {}", dto));
        }

        Optional<Users> usersOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저 id 입니다.");
        }

        if (chapterOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 커리큘럼 id 입니다.");
        }

        Users user = usersOptional.get();
        Curriculums chapter = chapterOptional.get();

        Optional<Studies> studyOptional = studyRepository.findByCurriculumAndUserAndAndLevel(chapter, user, dto.getCurrentLevel());

        if (studyOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format("진행중인 학습이 존재하지 않습니다. user: {}, chapter: {}, level: {}", user.getUsername(), chapter.getCurriculumName(), dto.getCurrentLevel()));
        }

        Studies study = studyOptional.get();

        // 현재 study 학습 통과 처리
        study.setPassed(true);
        studyRepository.save(study);

        // 추가 난이도 학습 데이터 생성
        if (dto.getCurrentLevel() == LevelType.EASY) {
            if (dto.getNextLevel() == LevelType.HARD) {
                List<Studies> studies = new ArrayList<>();
                // 중간 단계 생성
                studies.add(Studies.createStudy(user, chapter, true, null, LevelType.NORMAL));
                studies.add(Studies.createStudy(user, chapter, true, null, LevelType.HARD));

                studyRepository.saveAll(studies);
            } else if (dto.getNextLevel() == LevelType.NORMAL) {
                Studies studyNext = Studies.createStudy(user, chapter, true, null, LevelType.HARD);

                studyRepository.save(studyNext);
            }
        } else if (dto.getCurrentLevel() == LevelType.NORMAL) {
            Studies studyNext = Studies.createStudy(user, chapter, true, null, LevelType.HARD);
            studyRepository.save(studyNext);
        }

        // 아무 챕터 학습 미션 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.STUDY_COMPLETE));

        // 특정 커리큘럼 내 챕터 학습 완료 처리
        eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootAndChapterName(chapter.getParent(), chapter)));


        log.info("chapter.getParent().getTotalChapterCount() = {}, chapter.getChapterNum() = {}", chapter.getParent().getTotalChapterCount(), chapter.getChapterNum());
        // 커리큘럼 내 모든 챕터 학습 완료 시
        if (chapter.getParent().getTotalChapterCount() == chapter.getChapterNum()) {
            eventPublisher.publishEvent(new UserActionEvent(this, user, ServiceType.STUDY, MissionConst.createConstByRootName(chapter.getParent())));
        }

        log.info(String.format("curriculumPass 처리 완료. user: %s, chapter: %s, currentLevel: %s, nextLevel: %s", user.getUsername(), chapter.getCurriculumName(), dto.getCurrentLevel(), dto.getNextLevel()));
    }

    // 유저의 커리큘럼 학습 내용 로드
    @Transactional
    public StudyTextDto getCurriculumText(DataCallDto dto, LevelType levelType) {
        log.info("getCurriculumText called with dto: {}, levelType: {}", dto, levelType);

        Optional<Users> usersOptional = userRepository.findByUserId(dto.getUserId());
        Optional<Curriculums> chapterOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저 id 입니다.");
        }

        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 커리큘럼 id 입니다.");
        }

        Users user = usersOptional.get();
        Curriculums chapter = chapterOptional.get();

//        List<Studies> studiesList = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());
//        Studies study = studiesList.isEmpty() ? null : studiesList.get(0);

        Optional<Studies> studyOptional = studyRepository.findByCurriculumAndUserAndAndLevel(chapter, user, levelType);
        Studies study = null;

        // 데이터가 존재하지 않으면 null 그대로
        if (studyOptional.isPresent()) {
            study = studyOptional.get();
        }

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        if (study == null || study.getText() == null) {
            Curriculums rootNode = chapter.getParent();
            String keyword = curriculumService.getKeywordForLevel(chapter, levelType); // CurriculumService의 메서드 사용
            String fullKeyword = chapter.getCurriculumName() + ": " + keyword;
            String generatedText = requestGptText(fullKeyword, rootNode.getCurriculumName());

            if (study == null) {
                study = new Studies();
                study.setUser(user);
                study.setCurriculum(chapter);
                study.setLevel(levelType);
                study.setText(generatedText);
                studyRepository.save(study);
            } else {
                study.setText(generatedText);
            }
        }

        StudyTextDto responseDto = new StudyTextDto();
        responseDto.setText(study.getText());

        log.info("getCurriculumText returning: {}", responseDto);
        return responseDto;
    }

    // GPT API를 호출하여 학습 내용 생성
    private String requestGptText(String keyword, String pLang) {
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of("role", "system", "content", "너는 " + pLang + "의 전문 튜터야."),
                        Map.of("role", "user", "content", "다음 키워드에 대해 자세한 학습 내용을 생성해줘. (키워드: " + keyword + ")")
                ),
                "max_tokens", 1000
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", entity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();
                log.info("requestGptText response: {}", response);
                String content = extractContentFromResponse(response);
                log.info("Extracted content from GPT response: {}", content);
                return content;
            } else {
                log.error("API call failed with status: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Error during API call");
            }
        } catch (Exception e) {
            log.error("API call failed", e);
            throw new RuntimeException("Error during API call", e);
        }
    }

    // GPT API 응답 JSON에서 학습 내용만 추출
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            log.error("Error parsing response JSON", e);
            return "";
        }
    }
}