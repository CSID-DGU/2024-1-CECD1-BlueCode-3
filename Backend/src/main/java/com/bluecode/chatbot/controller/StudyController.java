package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/curriculum")
public class StudyController {

    private final StudyService studyService;

    //
    @GetMapping("/root/{userId}")
    public ResponseEntity<Object> searchRootData(@PathVariable Long userId) {
        try {
            CurriculumRootResponseDto result = studyService.searchRootData(userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 유저의 root 내 챕터들의 커리큘럼 학습 Study 데이터 생성 요청
    @PostMapping("/create")
    public ResponseEntity<Object> createChapterStudyData(@RequestBody DataCallDto dto) {
        try {
            CurriculumChapResponseDto result = studyService.createCurriculumStudyData(dto);
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //유저의 커리큘럼 진행 현황 요청
    @PostMapping("/chapters")
    public ResponseEntity<Object> findCurriculumProgress(@RequestBody DataCallDto dataCallDto){
        try {
            CurriculumPassedDto curriculumPassedDto = studyService.getCurriculumProgress(dataCallDto);
            return ResponseEntity.ok().body(curriculumPassedDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //유저가 요청한 커리큘럼 챕터의 학습 내용 텍스트 요청
    @PostMapping("/text")
    public ResponseEntity<Object> findCurriculumText(@RequestBody CurriculumTextCallDto curriculumTextCallDto) {

        try {
            StudyTextDto studyTextDto = studyService.getCurriculumText(curriculumTextCallDto);
            return ResponseEntity.ok().body(studyTextDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 학습중인 커리큘럼 챕터 통과 처리
    @PostMapping("/pass")
    public ResponseEntity<Object> chapterPass(@RequestBody CurriculumPassCallDto dto) {

        try {
            String result = studyService.chapterPass(dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}