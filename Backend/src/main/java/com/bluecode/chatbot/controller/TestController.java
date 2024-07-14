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
    public ResponseEntity createInitTest(@RequestBody DataCallDto dto) {
        log.info("call createInitTest in TestController: {}", dto);
        try {
            TestResponseDto initTest = testService.createInitTest(dto);
            log.info("createInitTest passed: {}", initTest);
            return ResponseEntity.ok().body(initTest);
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 dto 값일 경우, 400 error 발생 및 에러 메시지 String return
            log.error("createInitTest error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 Exception의 경우, back 내부 에러로 간주
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 이해도 테스트 생성
    @PostMapping("/test/create/normal")
    public ResponseEntity createNormalTest(@RequestBody DataCallDto dto) {

        log.info("call createNormalTest in TestController: {}", dto);

        try {
            TestResponseDto normalTest = testService.createNormalTest(dto);
            log.info("createNormalTest passed: {}", normalTest);
            return ResponseEntity.ok().body(normalTest);
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 dto 값일 경우, 400 error 발생 및 에러 메시지 String return
            log.error("createNormalTest error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 Exception의 경우, back 내부 에러로 간주
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 객관식 문제 채점
    @PostMapping("/test/submit/num")
    public ResponseEntity submitAnswerNum(@RequestBody TestAnswerCallDto dto) {

        log.info("call submitAnswerNum in TestController: {}", dto);

        try {
            TestAnswerResponseDto answer = testService.submitAnswerNum(dto);
            log.info("submitAnswerNum passed: {}", answer);
            return ResponseEntity.ok().body(answer);
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 dto 값일 경우, 400 error 발생 및 에러 메시지 String return
            log.error("submitAnswerNum error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 Exception의 경우, back 내부 에러로 간주
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // user 테이블에 초기 테스트 완료 처리
    @GetMapping("/test/complete/init/{userId}")
    public ResponseEntity completeInitTest(@PathVariable Long userId) {

        try {
            String result = testService.completeInitTest(userId);
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 유저 테이블 id 값일 경우, 400 error 발생 및 에러 메시지 String return
            log.error("completeInitTest error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 Exception의 경우, back 내부 에러로 간주
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
