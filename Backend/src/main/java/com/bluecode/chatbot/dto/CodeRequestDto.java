package com.bluecode.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequestDto {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("language")
    private String language;

    @JsonProperty("quiz_id")
    private Long quizId;
}