package com.bluecode.chatbot.controller;

public class ChatRequest {
    private String prompt;

    public ChatRequest() {
        // 기본 생성자
    }

    public ChatRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}