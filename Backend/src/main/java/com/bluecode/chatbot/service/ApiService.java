package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;
    private final CurriculumRepository curriculumRepository;

    @Value("${api.key}")
    private String apiKey;

    private ObjectMapper objectMapper = new ObjectMapper();

    // gpt API에 응답을 요청
    public String sendPostRequest(List<Map<String, String>> messages, Long curriculumId) {
        String rules = loadRules(curriculumId); // 규칙 로드
        log.info("rules: {}", rules);
        messages.add(Map.of("role", "system", "content", rules));
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", messages,
                "max_tokens", 1500
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalArgumentException("API 호출 실패! Status: " + response.getStatusCode());
        }
    }

    // json에서 content만 추출하여 텍스트로 저장
    public String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").path(0).path("message").path("content").asText();
        } catch (IOException e) {
            throw new IllegalArgumentException("JSON 응답 파싱 에러", e);
        }
    }

    // 루트 커리큘럼 출력
    private String getRootCurriculumName(Curriculums curriculum) {
        return curriculum.getRoot().getCurriculumName();
    }

    // rules.txt 파일에서 응답 규칙을 로드
    public String loadRules(Long curriculumId) {
        Curriculums curriculum = curriculumRepository.findById(curriculumId).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 curriculum ID"));
        String rootCurriculumName = getRootCurriculumName(curriculum);

        try {
            ClassPathResource resource = new ClassPathResource("rules.txt");
            String rules = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
            return rules.replace("Plang", rootCurriculumName);
        } catch (IOException e) {
            throw new IllegalArgumentException("응답 규칙 로드 실패", e);
        }
    }
}