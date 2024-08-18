package com.bluecode.chatbot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestAnswerResponseDto {

    // 공통 test 정답 여부 표시
    private Boolean passed;

    // 코드작성형 전용 결과 표시
    private String result;
}
