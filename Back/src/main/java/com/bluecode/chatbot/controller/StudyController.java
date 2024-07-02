package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.CurriculumPassedDto;
import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudyController {

    private final StudyService studyService;

    //유저의 커리큘럼 진행 현황 요청
    @PostMapping("/api/curriculum/chapters")
    public ResponseEntity<CurriculumPassedDto> findCurriculumProgress(@RequestBody DataCallDto dataCallDto){
        CurriculumPassedDto curriculumPassedDto=studyService.getCurriculumProgress(dataCallDto);

        return ResponseEntity.ok().body(curriculumPassedDto);
    }

}
