package com.bluecode.chatbot.dto;

import com.bluecode.chatbot.domain.QuestionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QuestionResponseDto {

    private QuestionType questionType;
    private String answer;
    private Long chatId;
    private List<String> answerList;
}
