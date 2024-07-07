package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.LevelType;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapElementDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;

    private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

    // 커리큘럼 챕터 목록 로드
    public CurriculumChapResponseDto getCurriculumChapters(CurriculumChapCallDto dto) {
        logger.info("getCurriculumChapters called with dto: {}", dto);

        // 루트 커리큘럼 찾기
        Curriculums rootCurriculum = curriculumRepository.findById(dto.getCurriculumId()).orElse(null);
        if (rootCurriculum == null) {
            throw new IllegalArgumentException("Invalid curriculum ID: " + dto.getCurriculumId());
        }

        // 해당 루트 노드의 챕터들 찾기
        List<Curriculums> chapters = curriculumRepository.findAllByParentOrderByChapterNum(rootCurriculum);

        // 챕터 리스트를 DTO로 변환
        List<CurriculumChapElementDto> chapterList = chapters.stream().map(chapter -> {
            CurriculumChapElementDto elementDto = new CurriculumChapElementDto();
            elementDto.setCurriculumId(chapter.getCurriculumId());
            elementDto.setText(chapter.getCurriculumName());
            return elementDto;
        }).collect(Collectors.toList());

        // 결과를 담아 반환
        CurriculumChapResponseDto responseDto = new CurriculumChapResponseDto();
        responseDto.setList(chapterList);

        logger.info("getCurriculumChapters returning: {}", responseDto);
        return responseDto;
    }

    // 유저 학습 수준에 맞춘 챕터의 키워드 출력(GPT API 학습내용 생성용)
    public String getKeywordForLevel(Curriculums curriculum, LevelType levelType) {
        logger.info("getKeywordForLevel called with curriculum: {}, levelType: {}", curriculum, levelType);

        switch (levelType) {
            case EASY:
                return curriculum.getKeywordEasy();
            case NORMAL:
                return curriculum.getKeywordNormal();
            case HARD:
                return curriculum.getKeywordHard();
            default:
                throw new IllegalArgumentException("Unknown level type: " + levelType);
        }
    }
}