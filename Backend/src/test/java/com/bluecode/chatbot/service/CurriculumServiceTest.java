package com.bluecode.chatbot.service;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.dto.CurriculumChapCallDto;
import com.bluecode.chatbot.dto.CurriculumChapElementDto;
import com.bluecode.chatbot.dto.CurriculumChapResponseDto;
import com.bluecode.chatbot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CurriculumServiceTest {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    void BeforeEach() {
        chatRepository.deleteAll();
        studyRepository.deleteAll();
        testRepository.deleteAll();
        quizRepository.deleteAll();
        curriculumRepository.deleteAll();
    }

    @Test
    @DisplayName("getCurriculumChapters 정상 로직 테스트")
    void getCurriculumChapters() {
        //given

        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, "루트", false, 0, 0, 3, false, true);
        curriculumRepository.save(root);

        // 챕터
        List<Curriculums> chapters = new ArrayList<>();
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",false, 1, 0, 2, false, false);
        Curriculums chap2 = Curriculums.createCurriculum(root, root, "챕터2",false, 2, 0,2, false, false);
        Curriculums chap3 = Curriculums.createCurriculum(root, root, "챕터3",false, 3, 0,2, false, false);
        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        curriculumRepository.saveAll(chapters);

        // 서브 챕터
        List<Curriculums> sub = new ArrayList<>();

        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터1", true, 1, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap1, root, "챕터1: 서브 챕터2", true, 1, 2, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap2, root, "챕터2: 서브 챕터1", true, 2, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap2, root, "챕터2: 서브 챕터2", true, 2, 2, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap3, root, "챕터3: 서브 챕터1", false, 3, 1, 1, true, false));
        sub.add(Curriculums.createCurriculum(chap3, root, "챕터3: 서브 챕터2", true, 3, 2, 1, true, false));

        curriculumRepository.saveAll(sub);

        //when
        CurriculumChapCallDto dto = new CurriculumChapCallDto();
        dto.setCurriculumId(root.getCurriculumId());

        CurriculumChapResponseDto result = curriculumService.getCurriculumChapters(dto);

        //then

        int subIdx = 0;
        // 챕터 레벨 내 일치 여부
        List<CurriculumChapElementDto> list = result.getList();
        for (int i = 0; i < list.size(); i++) {
            CurriculumChapElementDto chapDto = list.get(i);
            Assertions.assertThat(chapDto.getCurriculumId()).isEqualTo(chapters.get(i).getCurriculumId());
            Assertions.assertThat(chapDto.getText()).isEqualTo(chapters.get(i).getCurriculumName());

            //서브 챕터 레벨 내 일치 여부
            List<CurriculumChapElementDto> subList = chapDto.getSubChapters();
            for (int j = 0; j < subList.size(); j++) {
                CurriculumChapElementDto subChapter = subList.get(j);
                Assertions.assertThat(subChapter.getCurriculumId()).isEqualTo(sub.get(subIdx + j).getCurriculumId());
                Assertions.assertThat(subChapter.getText()).isEqualTo(sub.get(subIdx + j).getCurriculumName());
            }
            subIdx += subList.size();
        }
    }

    @Test
    @DisplayName("rootId Exception 테스트")
    void invalidRootId() {
        //given
        // 저장하지 않은 루트

        //when
        CurriculumChapCallDto dto = new CurriculumChapCallDto();
        dto.setCurriculumId(1000L);

        try {
            //when
            curriculumService.getCurriculumChapters(dto);
            Assertions.fail("IllegalArgumentException이 발생해야 함.");
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("유효하지 않은 커리큘럼 id 입니다.");
        } catch (Exception e) {
            Assertions.fail("IllegalArgumentException이 발생해야 함.");
        }
    }

    @Test
    @DisplayName("root가 아닌 curriculumId Exception 테스트")
    void invalidNonRootId() {
        //given
        // 루트
        Curriculums root = Curriculums.createCurriculum(null, null, "루트", false, 0, 0, 3, false, true);
        curriculumRepository.save(root);

        // 챕터
        Curriculums chap1 = Curriculums.createCurriculum(root, root, "챕터1",false, 1, 0, 2, false, false);
        curriculumRepository.save(chap1);

        //when
        CurriculumChapCallDto dto = new CurriculumChapCallDto();
        dto.setCurriculumId(chap1.getCurriculumId());
        try {
            curriculumService.getCurriculumChapters(dto);
            Assertions.fail("IllegalArgumentException이 발생해야 함.");
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("루트 커리큘럼이 아닙니다.");
        } catch (Exception e) {
            Assertions.fail("IllegalArgumentException이 발생해야 함.");
        }
    }

}