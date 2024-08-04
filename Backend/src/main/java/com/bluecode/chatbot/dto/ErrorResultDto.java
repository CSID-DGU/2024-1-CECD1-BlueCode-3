package com.bluecode.chatbot.dto;

import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@ToString
public class ErrorResultDto {

    private String errorCode;
    private String text;
    private LocalDateTime callDateTime;

    public ErrorResultDto(HttpStatus errorCode, String text) {
        this.errorCode = errorCode.toString();
        this.text = text;
        this.callDateTime = LocalDateTime.now();
    }
}
