package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionListResponseElementDto {

    private String curriculumText;
    private String question;
    private List<String> answer;
    private int level;
    private QuestionType questionType;
    private LocalDateTime chatDate;
}
