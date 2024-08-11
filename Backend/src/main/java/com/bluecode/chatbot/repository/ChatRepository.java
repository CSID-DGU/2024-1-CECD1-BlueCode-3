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

    // 유저 id, 서브챕터 id 기반 chat 리스트 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.curriculumId = :subChapterId " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndSubChapterIdOrderByChatDate(@Param("userId") Long userId,
                                                              @Param("subChapterId") Long subChapterId);

    // 유저 id, 부모 id 기반 chat 리스트 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.parent.curriculumId = :parentId " +
            "order by c.curriculum.subChapterNum, c.chatDate")
    List<Chats> findAllByUserIdAndParentIdOrderBySubChapterNumAndChatDate(@Param("userId") Long userId,
                                                                          @Param("parentId") Long parentId);

    // 유저 id, 루트 id 기반 chat 리스트 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.root.curriculumId = :rootId " +
            "order by c.curriculum.chapterNum, c.curriculum.subChapterNum, c.chatDate")
    List<Chats> findAllByUserIdAndRootIdOrderByChapterNumAndSubChapterNumAndChatDate(@Param("userId") Long userId,
                                                                                     @Param("rootId") Long rootId);

    // 유저 id, 서브챕터 id, 질문 유형 기반 chat 리스트 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.curriculumId = :subChapterId " +
            "and c.questionType = :questionType " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndSubChapterIdAndQuestionTypeOrderByChatDate(@Param("userId") Long userId,
                                                                             @Param("subChapterId") Long subChapterId,
                                                                             @Param("questionType") QuestionType questionType);

    // 유저 id, 부모 id, 질문 유형 기반 Chat 리스트 서브 챕터 번호, 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.parent.curriculumId = :parentId " +
            "and c.questionType = :questionType " +
            "order by c.curriculum.subChapterNum, c.chatDate")
    List<Chats> findAllByUserIdAndParentIdAAndQuestionTypeOrderBySubChapterNumAndChatDate(@Param("userId") Long userId,
                                                                                          @Param("parentId") Long parentId,
                                                                                          @Param("questionType") QuestionType questionType);

    // 유저 id, 루트 id, 질문 유형 기반 Chat 리스트 챕터 번호, 서브 챕터 번호, 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.curriculum.root.curriculumId = :rootId " +
            "and c.questionType = :questionType " +
            "order by c.curriculum.chapterNum, c.curriculum.subChapterNum, c.chatDate")
    List<Chats> findAllByUserIdAndRootIdAAndQuestionTypeOrderByChapterNumAndSubChapterNumAndChatDate(@Param("userId") Long userId,
                                                                                                     @Param("rootId") Long rootId,
                                                                                                     @Param("questionType") QuestionType questionType);

    // 유저 id 기반 전체 chat 리스트 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "order by c.curriculum.root.curriculumId, c.curriculum.chapterNum, c.curriculum.subChapterNum, c.chatDate")
    List<Chats> findAllByUserId(@Param("userId") Long userId);


    // 유저 id, 질문 유형 기반 chat 리스트 생성 시간 정렬 검색
    @Query("select c from Chats c " +
            "join fetch c.curriculum " +
            "join fetch c.user " +
            "where c.user.userId = :userId " +
            "and c.questionType = :questionType " +
            "order by c.chatDate")
    List<Chats> findAllByUserIdAndQuestionTypeOrderByChatDate(@Param("userId") Long userId,
                                                              @Param("questionType") QuestionType questionType);


    // chat 테이블 id 기반 단일 Chat 검색
    Optional<Chats> findById(Long chatId);
}
