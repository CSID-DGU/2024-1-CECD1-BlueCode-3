package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.dto.TestAnswerCallDto;
import com.bluecode.chatbot.dto.TestAnswerResponseDto;
import com.bluecode.chatbot.dto.TestResponseDto;
import com.bluecode.chatbot.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // 초기 테스트 생성(1개 챕터)
    @PostMapping("/test/create/init")
    public ResponseEntity<TestResponseDto> createInitTest(@RequestBody DataCallDto dto) {
        log.info("call createInitTest in TestController: {}", dto);
        TestResponseDto initTest = testService.createInitTest(dto);
        log.info("createInitTest passed: {}", initTest);
        return ResponseEntity.ok().body(initTest);
}

    // 이해도 테스트 생성
    @PostMapping("/test/create/normal")
    public ResponseEntity<TestResponseDto> createNormalTest(@RequestBody DataCallDto dto) {

        log.info("call createNormalTest in TestController: {}", dto);
        TestResponseDto normalTest = testService.createNormalTest(dto);
        log.info("createNormalTest passed: {}", normalTest);
        return ResponseEntity.ok().body(normalTest);

    }

    // 객관식 문제 채점
    @PostMapping("/test/submit/num")
    public ResponseEntity<TestAnswerResponseDto> submitAnswerNum(@RequestBody TestAnswerCallDto dto) {

        log.info("call submitAnswerNum in TestController: {}", dto);
        TestAnswerResponseDto answer = testService.submitAnswerNum(dto);
        log.info("submitAnswerNum passed: {}", answer);
        return ResponseEntity.ok().body(answer);
    }

    // 단답형 문제 채점
    @PostMapping("/test/submit/word")
    public ResponseEntity<TestAnswerResponseDto> submitAnswerWord(@RequestBody TestAnswerCallDto dto) {

        log.info("call submitAnswerWord in TestController: {}", dto);


        TestAnswerResponseDto answer = testService.submitAnswerWord(dto);
        log.info("submitAnswerNum passed: {}", answer);
        return ResponseEntity.ok().body(answer);
    }

    // user 테이블에 초기 테스트 완료 처리
    @GetMapping("/test/complete/init/{userId}")
    public ResponseEntity<String> completeInitTest(@PathVariable Long userId) {

        String result = testService.completeInitTest(userId);
        return ResponseEntity.ok().body(result);
    }
}
