package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Chats;
import com.bluecode.chatbot.domain.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chats, Long> {

    // 특정 유저가 커리큘럼(챕터)에서 채팅한 Chat 리스트 Chat 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.curriculumId = :chapterId " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndChapterIdOrderByChatDate(@Param("userId") Long userId,
                                                           @Param("chapterId") Long curriculumId);

    // 특정 유저가 특정 질문 유형인 Chat 리스트 Chat 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.questionType = :questionType " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndQuestionTypeOrderByChatDate(@Param("userId") Long userId,
                                                              @Param("questionType") QuestionType questionType);

    // 특정 유저가 커리큘럼(챕터)에서 특정 질문 유형인 Chat 리스트 Chat 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.curriculumId = :chapterId " +
            "and c.questionType = :questionType " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndChapterIdAAndQuestionTypeOrderByChatDate(@Param("userId") Long userId,
                                                                           @Param("chapterId") Long curriculumId,
                                                                           @Param("questionType") QuestionType questionType);

    // 특정 유저가 커리큘럼(루트)에서 특정 질문 유형인 Chat 리스트 Chat 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.parent.curriculumId = :rootId " +
            "and c.questionType = :questionType " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndRootIdAAndQuestionTypeOrderByChatDate(@Param("userId") Long userId,
                                                                           @Param("rootId") Long rootId,
                                                                           @Param("questionType") QuestionType questionType);

    // 특정 유저의 루트 커리큘럼에서 질문했던 모든 Chat 리스트 챕터 번호, Chat 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.parent.curriculumId = :parentId " +
            "order by c.curriculum.chapterNum, c.chatDate")
    List<Chats> findAllByUserIdAndParentIdOrderByChapterNumAndChatDate(@Param("userId") Long userId,
                                                                       @Param("parentId") Long curriculumId);

    // chat 테이블 id 기반 단일 Chat 검색
    Optional<Chats> findById(Long chatId);
}
