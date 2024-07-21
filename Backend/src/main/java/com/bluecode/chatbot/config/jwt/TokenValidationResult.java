package com.bluecode.chatbot.config.jwt;

public enum TokenValidationResult {
    VALID,               // 토큰이 유효함
    EXPIRED,             // 토큰이 만료됨
    UNSUPPORTED,         // 지원되지 않는 토큰 형식
    MALFORMED,           // 토큰 형식이 잘못됨
    INVALID_SIGNATURE,  // 서명이 유효하지 않음
    EMPTY_OR_NULL,       // 토큰이 비어 있거나 null
    INVALID              // 그 외의 모든 유효하지 않은 경우
}