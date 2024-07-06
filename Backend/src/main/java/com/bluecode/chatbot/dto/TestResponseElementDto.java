package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.QuizLevel;
import com.bluecode.chatbot.domain.QuizType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestResponseElementDto {

    private Long quizId;
    private String text;
    private QuizLevel level;
    private QuizType quizType;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
}
