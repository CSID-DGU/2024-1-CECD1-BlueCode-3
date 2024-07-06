package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class QuestionListResponseElementDto {

    private String curriculumText;
    private String question;
    private String answer;
    private QuestionType questionType;
    private LocalDateTime chatDate;
}
