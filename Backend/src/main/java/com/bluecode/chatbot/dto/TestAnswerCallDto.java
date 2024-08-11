package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestAnswerCallDto {

    private Long testId;
    private Long userId;
    private String answer;
}
