package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.CurriculumPassedDto;
import com.bluecode.chatbot.dto.CurriculumTextCallDto;
import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.dto.StudyTextDto;
import com.bluecode.chatbot.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/curriculum")
public class StudyController {

    private final StudyService studyService;

    //유저의 커리큘럼 진행 현황 요청
    @PostMapping("/chapters")
    public ResponseEntity<CurriculumPassedDto> findCurriculumProgress(@RequestBody DataCallDto dataCallDto){
        CurriculumPassedDto curriculumPassedDto = studyService.getCurriculumProgress(dataCallDto);
        return ResponseEntity.ok().body(curriculumPassedDto);
    }

    //유저가 요청한 커리큘럼 챕터의 학습 내용 텍스트 요청
    @PostMapping("/chaptertext")
    public ResponseEntity<StudyTextDto> findCurriculumText(@RequestBody CurriculumTextCallDto curriculumTextCallDto){
        DataCallDto dataCallDto = new DataCallDto();
        dataCallDto.setCurriculumId(curriculumTextCallDto.getCurriculumId());
        dataCallDto.setUserId(curriculumTextCallDto.getUserId());

        StudyTextDto studyTextDto = studyService.getCurriculumText(dataCallDto,curriculumTextCallDto.getLevelType());
        return ResponseEntity.ok().body(studyTextDto);
    }


    //(초기 테스트 중)테스트 통과한 챕터의 내용 생성
    @PostMapping("/curriculumcreate")
    public ResponseEntity<Void> createChapterText(@RequestBody CurriculumTextCallDto curriculumTextCallDto){
        DataCallDto dataCallDto = new DataCallDto();
        dataCallDto.setCurriculumId(curriculumTextCallDto.getCurriculumId());
        dataCallDto.setUserId(curriculumTextCallDto.getUserId());
        studyService.getCurriculumText(dataCallDto, curriculumTextCallDto.getLevelType());

        return ResponseEntity.ok().build();
    }
}
