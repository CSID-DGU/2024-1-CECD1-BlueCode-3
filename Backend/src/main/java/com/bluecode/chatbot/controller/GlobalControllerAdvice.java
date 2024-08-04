package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.ErrorResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResultDto illegalArgumentException(IllegalArgumentException e) {
        return new ErrorResultDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResultDto exception(Exception e) {
        return new ErrorResultDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
