package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapElementDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;

    // 커리큘럼 챕터 목록 로드
    public CurriculumChapResponseDto getCurriculumChapters(CurriculumChapCallDto dto) {
        log.info("getCurriculumChapters called with dto: {}", dto);

        // 루트 커리큘럼 찾기
        Optional<Curriculums> rootOptional = curriculumRepository.findById(dto.getCurriculumId());

        if (rootOptional.isEmpty()) {
            log.error("유효하지 않은 커리큘럼 id dto={}", dto);
            throw new IllegalArgumentException("유효하지 않은 커리큘럼 id 입니다.");
        }

        Curriculums root = rootOptional.get();

        if (!root.isRootNode()) {
            log.error("루트 커리큘럼 아님 root={}", root);
            throw new IllegalArgumentException("루트 커리큘럼이 아닙니다.");
        }

        // 해당 루트 노드의 챕터들 찾기
        List<Curriculums> chapters = curriculumRepository.findAllChildByParentOrderByChapterNumAndSubChapterNum(root);

        // 챕터 리스트를 DTO로 변환
        List<CurriculumChapElementDto> chapterList = chapters.stream().map(chapter -> {
            CurriculumChapElementDto elementDto = new CurriculumChapElementDto();
            elementDto.setCurriculumId(chapter.getCurriculumId());
            elementDto.setText(chapter.getCurriculumName());
            return elementDto;
        }).toList();

        // 결과를 담아 반환
        CurriculumChapResponseDto responseDto = new CurriculumChapResponseDto();
        responseDto.setList(chapterList);

        log.info("getCurriculumChapters returning: {}", responseDto);
        return responseDto;
    }
}