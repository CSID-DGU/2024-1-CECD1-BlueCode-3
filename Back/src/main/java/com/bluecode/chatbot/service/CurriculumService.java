package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final StudyRepository studyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

    @Value("${api.key}")
    private String apiKey;

    // 커리큘럼 루트노드(프로그래밍 언어) 로드
    private Curriculums findRootNode(Curriculums curriculum) {
        while (curriculum.getParent() != null) {
            curriculum = curriculum.getParent();
        }
        Curriculums rootNode = curriculum;
        return rootNode;
    }

    // 커리큘럼 챕터 목록 로드
    public CurriculumChapResponseDto getCurriculumChapters(CurriculumChapCallDto dto) {
        Curriculums curriculum = curriculumRepository.findById(dto.getCurriculumId()).orElse(null);
        if (curriculum == null) {
            throw new IllegalArgumentException("Invalid curriculum ID: " + dto.getCurriculumId());
        }

        // 루트 노드를 찾음
        Curriculums rootCurriculum = findRootNode(curriculum);

        // 루트 노드의 curriculumId를 부모로 가지는 챕터들을 가져옴
        List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(rootCurriculum);

        // 챕터 리스트를 DTO로 변환
        List<CurriculumChapElementDto> chapterList = chapters.stream().map(chapter -> {
            CurriculumChapElementDto elementDto = new CurriculumChapElementDto();
            elementDto.setCurriculumId(chapter.getCurriculumId());
            elementDto.setText(chapter.getCurriculumName());
            return elementDto;
        }).toList();

        // 결과를 담아 반환
        CurriculumChapResponseDto responseDto = new CurriculumChapResponseDto();
        responseDto.setList(chapterList);

        return responseDto;
    }

    // 유저의 커리큘럼 진행 현황 로드
    public CurriculumPassedDto getCurriculumProgress(DataCallDto dto) {
        List<Studies> studies = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());

        List<CurriculumPassedElementDto> progressList = studies.stream().map(study -> {
            CurriculumPassedElementDto elementDto = new CurriculumPassedElementDto();
            elementDto.setCurriculumId(study.getCurriculum().getCurriculumId());
            elementDto.setCurriculumName(study.getCurriculum().getCurriculumName());
            elementDto.setPassed(study.isPassed());
            return elementDto;
        }).toList();

        CurriculumPassedDto responseDto = new CurriculumPassedDto();
        responseDto.setList(progressList);

        return responseDto;
    }

    // 유저의 커리큘럼 학습 내용 로드
    @Transactional
    public StudyTextDto getCurriculumText(DataCallDto dto, LevelType levelType) {
        List<Studies> studiesList = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());
        Studies study = studiesList.isEmpty() ? null : studiesList.get(0);

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        if (study == null || study.getText() == null) {
            Curriculums curriculum = curriculumRepository.findById(dto.getCurriculumId()).orElse(null);
            Curriculums rootNode = findRootNode(curriculum);
            String keyword = getKeywordForLevel(curriculum, levelType);
            String fullKeyword = rootNode.getCurriculumName() + ": " + keyword;
            String generatedText = requestGptText(fullKeyword);

            if (study == null) {
                study = new Studies();
                study.setUser(new Users());
                study.getUser().setUserId(dto.getUserId());
                study.setCurriculum(curriculum);
                study.setLevel(levelType);
                study.setText(generatedText);
                studyRepository.save(study);
            } else {
                study.setText(generatedText);
            }
        }

        StudyTextDto responseDto = new StudyTextDto();
        responseDto.setText(study.getText());

        return responseDto;
    }

    // 유저 학습 수준에 맞춘 챕터의 키워드 출력(GPT API 학습내용 생성용)
    private String getKeywordForLevel(Curriculums curriculum, LevelType levelType) {
        switch (levelType) {
            case EASY:
                return curriculum.getKeywordEasy();
            case NORMAL:
                return curriculum.getKeywordNormal();
            case HARD:
                return curriculum.getKeywordHard();
            default:
                throw new IllegalArgumentException("Unknown level type: " + levelType);
        }
    }

    // GPT API를 호출하여 학습 내용 생성
    private String requestGptText(String keyword) {
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "prompt", "Create detailed learning content for the following keyword: " + keyword,
                "max_tokens", 1000
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.openai.com/v1/completions", entity, String.class);
            String response = responseEntity.getBody();
            return extractContentFromResponse(response);
        } catch (Exception e) {
            logger.error("API call failed", e);
            throw new RuntimeException("Error during API call", e);
        }
    }

    // GPT API 응답 JSON에서 학습 내용만 추출
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("text").asText();
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return "";
        }
    }
}