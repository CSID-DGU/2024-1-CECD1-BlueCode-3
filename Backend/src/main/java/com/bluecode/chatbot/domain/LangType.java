package com.bluecode.chatbot.domain;

/**
 * 코드작성형 대상 언어 표시용
 * PYTHON("python")
 * JAVA("java")
 * CPP("cpp")
 */

public enum LangType {

    PYTHON("python"),
    JAVA("java"),
    CPP("cpp");

    private final String lang;

    // Constructor to initialize the language string
    LangType(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return this.lang;
    }
}
