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

    // 유저의 루트 커리큘럼 학습 현황 요청
    @GetMapping("/root/{userId}")
    public ResponseEntity<CurriculumRootResponseDto> searchRootData(@PathVariable Long userId) {

        CurriculumRootResponseDto result = studyService.searchRootData(userId);
        return ResponseEntity.ok().body(result);
    }

    // 유저의 첫 학습 시 root 내 챕터들의 Study 데이터 초기화
    @PostMapping("/create")
    public ResponseEntity<CurriculumChapResponseDto> createChapterStudyData(@RequestBody DataCallDto dto) {

        CurriculumChapResponseDto result = studyService.createCurriculumStudyData(dto);
        return ResponseEntity.ok().body(result);
    }

    // 유저의 커리큘럼 진행 현황 요청
    @PostMapping("/chapters")
    public ResponseEntity<CurriculumPassedDto> findCurriculumProgress(@RequestBody DataCallDto dataCallDto){

        CurriculumPassedDto curriculumPassedDto = studyService.getCurriculumProgress(dataCallDto);
        return ResponseEntity.ok().body(curriculumPassedDto);
    }

    // 유저가 요청한 커리큘럼 챕터의 학습 text 요청(GPT API 사용)
    @PostMapping("/text")
    public ResponseEntity<StudyTextDto> findCurriculumText(@RequestBody CurriculumTextCallDto curriculumTextCallDto) {

        StudyTextDto studyTextDto = studyService.getCurriculumText(curriculumTextCallDto);
        return ResponseEntity.ok().body(studyTextDto);
    }

    // 학습중인 커리큘럼 서브 챕터 통과 처리
    @PostMapping("/subChapter/pass")
    public ResponseEntity<Boolean> subChapterPass(@RequestBody CurriculumPassCallDto dto) {
        boolean result = studyService.subChapterPass(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chapter/pass")
    public ResponseEntity<Boolean> chapterPass(@RequestBody CurriculumPassCallDto dto) {
        boolean result = studyService.chapterPass(dto);
        return ResponseEntity.ok(result);
    }
}