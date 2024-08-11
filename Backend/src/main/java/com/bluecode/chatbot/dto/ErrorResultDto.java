package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ErrorResultDto {

    private int errorCode;
    private String text;
    private LocalDateTime callDateTime;

    public ErrorResultDto(int errorCode, String text) {
        this.errorCode = errorCode;
        this.text = text;
        this.callDateTime = LocalDateTime.now();
    }
}
