package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.domain.TextType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurriculumTextCallDto {
    private Long userId;
    private Long curriculumId;
    private TextType textType;
    private LevelType levelType;
}
