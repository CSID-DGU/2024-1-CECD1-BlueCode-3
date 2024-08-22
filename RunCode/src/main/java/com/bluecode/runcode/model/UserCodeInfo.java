package com.bluecode.runcode.model;

public class UserCodeInfo { // 클라이언트 -> 서버
    private String selectedLang; // 설정 언어
    private String codeText; // 코드 본문

    public String getSelectedLang() {
        return selectedLang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }
}