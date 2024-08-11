package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapElementDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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

        // 해당 루트 노드의 자식들 찾기
        List<Curriculums> child = curriculumRepository.findAllByRoot(root);

        // 챕터 필터링
        List<Curriculums> chapters = child.stream().filter(i -> !i.isRootNode() && !i.isLeafNode()).toList();
        // 서브 챕터 필터링
        List<Curriculums> subChapters = child.stream().filter(Curriculums::isLeafNode).toList();
        Deque<Curriculums> deque = new ArrayDeque<>(subChapters);

        log.info("deque={}", deque);

        // 챕터 리스트를 DTO로 변환
        List<CurriculumChapElementDto> chapterList = new ArrayList<>();

        for (Curriculums chapter : chapters) {

            List<CurriculumChapElementDto> subChapterList = new ArrayList<>();

            while (!deque.isEmpty() && deque.getFirst().getParent() == chapter) {

                Curriculums subChapter = deque.removeFirst();

                CurriculumChapElementDto subChapterDto = new CurriculumChapElementDto();
                subChapterDto.setCurriculumId(subChapter.getCurriculumId());
                subChapterDto.setText(subChapter.getCurriculumName());
                // 서브 챕터 추가
                subChapterList.add(subChapterDto);
            }

            // 서브 챕터 list 챕터 dto에 추가
            CurriculumChapElementDto chapterDto = new CurriculumChapElementDto();
            chapterDto.setCurriculumId(chapter.getCurriculumId());
            chapterDto.setText(chapter.getCurriculumName());
            chapterDto.setSubChapters(subChapterList);

            chapterList.add(chapterDto);
        }

        // 결과를 담아 반환
        CurriculumChapResponseDto responseDto = new CurriculumChapResponseDto();
        responseDto.setList(chapterList);

        log.info("getCurriculumChapters returning: {}", responseDto);
        return responseDto;
    }
}