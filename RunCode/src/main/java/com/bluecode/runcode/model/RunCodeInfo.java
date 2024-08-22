package com.bluecode.runcode.model;

public class RunCodeInfo { // 서버 -> 클라이언트
    private String runResult;
    private boolean isInputFunc;  // 입력 함수 존재 여부(입력 함수 1개 이상 true, 없으면 false)

    // 기본 생성자
    public RunCodeInfo() {
    }

    // 문자열 인자를 받는 생성자
    public RunCodeInfo(String runResult) {
        this.runResult = runResult;
    }

    // 입력 함수 존재 여부와 결과 메시지를 설정하는 생성자
    public RunCodeInfo(String runResult, boolean isInputFunc) {
        this.runResult = runResult;
        this.isInputFunc = isInputFunc;
    }

    // 결과 가져오기
    public String getRunResult() {
        return runResult;
    }

    // 결과 설정하기
    public void setRunResult(String runResult) {
        this.runResult = runResult;
    }

    // 입력 대기 상태(입력 함수 존재 여부 확인) 로드
    public boolean isAwaitingInput() {
        return isInputFunc;
    }

    // 입력 대기 상태(입력 함수 존재 여부 확인) 설정
    public void setAwaitingInput(boolean isInputFunc) {
        this.isInputFunc = isInputFunc;
    }
}