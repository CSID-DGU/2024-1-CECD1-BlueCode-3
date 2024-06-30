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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudiesService {

    private final StudyRepository studyRepository;
    private final CurriculumRepository curriculumRepository;
    private final CurriculumsService curriculumsService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(StudiesService.class);

    @Value("${api.key}")
    private String apiKey;

    // 유저의 커리큘럼 진행 현황 로드
    public CurriculumPassedDto getCurriculumProgress(DataCallDto dto) {
        logger.info("getCurriculumProgress called with dto: {}", dto);

        List<Studies> studies = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());

        List<CurriculumPassedElementDto> progressList = studies.stream().map(study -> {
            CurriculumPassedElementDto elementDto = new CurriculumPassedElementDto();
            elementDto.setCurriculumId(study.getCurriculum().getCurriculumId());
            elementDto.setCurriculumName(study.getCurriculum().getCurriculumName());
            elementDto.setPassed(study.isPassed());
            return elementDto;
        }).collect(Collectors.toList());

        CurriculumPassedDto responseDto = new CurriculumPassedDto();
        responseDto.setList(progressList);

        logger.info("getCurriculumProgress returning: {}", responseDto);
        return responseDto;
    }

    // 유저의 커리큘럼 학습 내용 로드
    @Transactional
    public StudyTextDto getCurriculumText(DataCallDto dto, LevelType levelType) {
        logger.info("getCurriculumText called with dto: {}, levelType: {}", dto, levelType);

        List<Studies> studiesList = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());
        Studies study = studiesList.isEmpty() ? null : studiesList.get(0);

        // 학습 내용이 없으면 GPT API를 호출하여 학습 내용 생성
        if (study == null || study.getText() == null) {
            Curriculums curriculum = curriculumRepository.findById(dto.getCurriculumId()).orElse(null);
            Curriculums rootNode = curriculum.getParent();
            String keyword = curriculumsService.getKeywordForLevel(curriculum, levelType); // CurriculumsService의 메서드 사용
            String fullKeyword = rootNode.getCurriculumName() + ": " + keyword;
            String generatedText = requestGptText(fullKeyword, rootNode.getCurriculumName());

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

        logger.info("getCurriculumText returning: {}", responseDto);
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
                logger.info("requestGptText response: {}", response);
                String content = extractContentFromResponse(response);
                logger.info("Extracted content from GPT response: {}", content);
                return content;
            } else {
                logger.error("API call failed with status: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Error during API call");
            }
        } catch (Exception e) {
            logger.error("API call failed", e);
            throw new RuntimeException("Error during API call", e);
        }
    }

    // GPT API 응답 JSON에서 학습 내용만 추출
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            logger.error("Error parsing response JSON", e);
            return "";
        }
    }
}