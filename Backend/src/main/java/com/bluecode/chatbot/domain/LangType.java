package com.bluecode.chatbot.domain;



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
