package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CurriculumPassedElementDto {

    private Long curriculumId;
    private String curriculumName;
    private Boolean passed;
    private List<CurriculumPassedElementDto> subChapters;
}
