package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CurriculumsServiceTest {

    @InjectMocks
    private CurriculumsService curriculumsService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurriculumChapters() {
        // Given
        Curriculums rootCurriculum = new Curriculums();
        rootCurriculum.setCurriculumId(1L);
        rootCurriculum.setCurriculumName("Root Curriculum");

        when(curriculumRepository.findById(1L)).thenReturn(java.util.Optional.of(rootCurriculum));
        when(curriculumRepository.findAllByParentOrderByChapterNum(any(Curriculums.class))).thenReturn(Collections.singletonList(rootCurriculum));

        CurriculumChapCallDto callDto = new CurriculumChapCallDto();
        callDto.setCurriculumId(1L);

        // When
        CurriculumChapResponseDto responseDto = curriculumsService.getCurriculumChapters(callDto);

        // Then
        assertEquals(1, responseDto.getList().size());
        assertEquals("Root Curriculum", responseDto.getList().get(0).getText());
    }
}