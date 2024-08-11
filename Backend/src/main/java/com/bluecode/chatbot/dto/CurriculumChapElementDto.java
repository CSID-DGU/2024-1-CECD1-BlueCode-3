package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CurriculumChapElementDto {

    private Long curriculumId;
    private String text;
    private List<CurriculumChapElementDto> subChapters;
}
