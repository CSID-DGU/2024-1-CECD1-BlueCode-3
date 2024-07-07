package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionCallDto {

    private Long userId;
    private Long curriculumId;
    private String text;
    private QuestionType type;
}
