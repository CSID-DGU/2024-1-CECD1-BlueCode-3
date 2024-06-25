package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CurriculumRepositoryTest {

    @Autowired private CurriculumRepository curriculumRepository;

    @Test
    @DisplayName("findByRootIdAndChapterNum 쿼리 테스트")
    void findByRootIdAndChapterNum_query_test() throws Exception {
        //given

        int chapNum = 1;
        Curriculums root = createCurriculum(null, "Test용 root 커리큘럼명", "", "", "", false, 0);
        curriculumRepository.save(root);
        Curriculums chapTest = createCurriculum(root, "Test용 커리큘럼명", "키워드easy", "키워드normal", "키워드Hard", true, chapNum);
        curriculumRepository.save(chapTest);

        //when
        Curriculums res = curriculumRepository.findByRootIdAndChapterNum(root.getCurriculumId(), chapNum);

        //then
        Assertions.assertThat(res).isEqualTo(chapTest);

    }

    @Test
    @DisplayName("findAllByParentOrderByChapterNum 쿼리 테스트")
    void findAllByParentOrderByChapterNum() throws Exception {
        //given

        int chapNum = 1;
        Curriculums root = createCurriculum(null, "Test용 root 커리큘럼명", "", "", "", false, 0);
        curriculumRepository.save(root);
        List<Curriculums> curriculums = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Curriculums chapTest = createCurriculum(root,  String.format("Test용 챕터 커리큘럼 %d", chapNum), "키워드easy", "키워드normal", "키워드Hard", true, chapNum);
            curriculumRepository.save(chapTest);
            curriculums.add(chapTest);
            chapNum++;
        }

        //when
        List<Curriculums> result = curriculumRepository.findAllByParentOrderByChapterNum(root);

        //then
        Assertions.assertThat(result.size()).isEqualTo(curriculums.size());

        for (int i = 0; i < 2; i++) {
            Assertions.assertThat(result.get(i)).isEqualTo(curriculums.get(i));
        }
    }

    @Test
    @DisplayName("findAllRootCurriculumList 쿼리 테스트")
    void findAllRootCurriculumList() throws Exception {
        //given
        List<Curriculums> roots = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Curriculums root = createCurriculum(null, String.format("Test용 root 커리큘럼명 %d", i + 1), "", "", "", false, 0);
            roots.add(root);
            curriculumRepository.save(root);
        }

        //when
        List<Curriculums> result = curriculumRepository.findAllRootCurriculumList();

        //then
        Assertions.assertThat(result.size()).isEqualTo(roots.size());

        for (int i = 0; i < 4; i++) {
            Assertions.assertThat(result.get(i)).isEqualTo(roots.get(i));
        }
    }

    private Curriculums createCurriculum(
            Curriculums parent,
            String curriculumName,
            String keywordEasy,
            String keywordNormal,
            String keywordHard,
            boolean testable,
            int chapterNum
    ) {
        Curriculums curriculums = new Curriculums();
        curriculums.setCurriculumName(curriculumName);
        curriculums.setParent(parent);
        curriculums.setKeywordEasy(keywordEasy);
        curriculums.setKeywordNormal(keywordNormal);
        curriculums.setKeywordHard(keywordHard);
        curriculums.setTestable(testable);
        curriculums.setChapterNum(chapterNum);

        return curriculums;
    }
}