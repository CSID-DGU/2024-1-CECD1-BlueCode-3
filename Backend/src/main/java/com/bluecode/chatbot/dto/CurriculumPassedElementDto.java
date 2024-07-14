package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.LevelType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurriculumPassedElementDto {

    private Long curriculumId;
    private String curriculumName;
    private Boolean passed;
    private LevelType levelType;
}
