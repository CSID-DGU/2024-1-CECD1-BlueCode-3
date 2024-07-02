package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.dto.CurriculumPassedDto;
import com.bluecode.chatbot.dto.DataCallDto;
import com.bluecode.chatbot.service.CurriculumService;
import com.bluecode.chatbot.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CurriculumController {

    private final CurriculumService curriculumService;

    //유저가 선택한 커리큘럼의 챕터들 정보(챕터 명) 요청
    @GetMapping("/api/curriculum/{curriculumId}")
    public ResponseEntity<CurriculumChapResponseDto> findCurriculumName(@PathVariable long curriculumId){
        CurriculumChapCallDto callDto=new CurriculumChapCallDto();
        callDto.setCurriculumId(curriculumId);

        CurriculumChapResponseDto curriculumChapResponseDto=curriculumService.getCurriculumChapters(callDto);
        return ResponseEntity.ok().body(curriculumChapResponseDto);
    }

}
