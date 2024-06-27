package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.dto.StudyTextDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import com.bluecode.chatbot.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StudiesServiceTest {

    @InjectMocks
    private StudiesService studiesService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private CurriculumRepository curriculumRepository;

    @Mock
    private CurriculumsService curriculumsService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurriculumText() {
        // Given
        Curriculums curriculum = new Curriculums();
        curriculum.setCurriculumId(1L);
        curriculum.setCurriculumName("Root Curriculum");

        Studies study = new Studies();
        study.setCurriculum(curriculum);
        study.setText("Existing Study Text");

        when(curriculumRepository.findById(1L)).thenReturn(java.util.Optional.of(curriculum));
        when(studyRepository.findAllByCurriculumIdAndUserId(1L, 1L)).thenReturn(Collections.singletonList(study));

        DataCallDto callDto = new DataCallDto();
        callDto.setCurriculumId(1L);
        callDto.setUserId(1L);

        // When
        StudyTextDto responseDto = studiesService.getCurriculumText(callDto, LevelType.EASY);

        // Then
        assertEquals("Existing Study Text", responseDto.getText());
    }
}