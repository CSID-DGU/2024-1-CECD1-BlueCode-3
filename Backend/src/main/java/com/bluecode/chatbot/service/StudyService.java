package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class StudyService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ApiService apiService;
    private final StudyRepository studyRepository;
    private final CurriculumService curriculumService;
    private final CurriculumRepository curriculumRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(StudyService.class);

    @Autowired
    public StudyService(
            RestTemplate restTemplate,
            @Value("${api.key}") String apiKey,
            ApiService apiService,
            StudyRepository studyRepository,
            CurriculumService curriculumService,
            CurriculumRepository curriculumRepository) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiService = apiService;
        this.studyRepository = studyRepository;
        this.curriculumService = curriculumService;
        this.curriculumRepository = curriculumRepository;
    }

    // 유저의 커리큘럼 진행 현황 로드
    public CurriculumPassedDto getCurriculumProgress(DataCallDto dto) {
        logger.info("getCurriculumProgress called with dto: {}", dto);

        //List<Studies> studies = studyRepository.findAllByCurriculumIdAndUserId(dto.getCurriculumId(), dto.getUserId());

        List<Studies> studies = studyRepository.findAllByUserId(dto.getUserId());

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
            String keyword = curriculumService.getKeywordForLevel(curriculum, levelType);
            String fullKeyword = curriculum.getCurriculumName() + ": " + keyword;
            String generatedResponse = requestGptText(fullKeyword, dto.getCurriculumId()); // 커리큘럼 ID로 루트 커리큘럼 이름을 조회
            String generatedText = apiService.extractContentFromResponse(generatedResponse); // json 형식 응답을 text로 추출

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
    private String requestGptText(String keyword, Long curriculumId) {
        String rules = apiService.loadRules(curriculumId); // 규칙 로드
        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of("role", "system", "content", rules));
        messages.add(Map.of("role", "user", "content", "다음 키워드에 대해 자세한 학습 내용을 생성해줘. (키워드: " + keyword + ")"));

        return apiService.sendPostRequest(messages);
    }
}