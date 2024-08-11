package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Studies;
import com.bluecode.chatbot.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Studies, Long> {

    // 유저, 루트 커리큘럼 기반 Studies 리스트 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.root = :root")
    List<Studies> findAllByUserAndRoot(@Param("user") Users user, @Param("root") Curriculums root);

    // 유저, 루트 커리큘럼 기반 존재성 검색
    @Query("select count(s.studyId) > 0 from Studies s " +
            "join s.user " +
            "join s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.root = :root")
    boolean isExistByUserAndRoot(@Param("user") Users user, @Param("root") Curriculums root);

    // 유저 기반 Studies 리스트 검색
    List<Studies> findAllByUser(Users user);

    // 유저의 루트 커리큘럼 대상 Study 리스트 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.rootNode = true")
    List<Studies> findAllRootByUser(@Param("user") Users user);


    // 유저, 커리큘럼 기반 단일 검색
    Optional<Studies> findByUserAndCurriculum(Users user, Curriculums curriculums);

    // 유저 부모 커리큘럼 기반 리스트 검색
    @Query("select s from Studies s " +
            "join fetch s.user " +
            "join fetch s.curriculum " +
            "where s.user = :user " +
            "and s.curriculum.parent = :parent")
    List<Studies> findAllByUserAndParent(@Param("user") Users user, @Param("parent") Curriculums parent);
}
