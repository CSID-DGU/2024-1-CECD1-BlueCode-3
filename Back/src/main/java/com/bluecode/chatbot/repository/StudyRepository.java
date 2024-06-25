package com.bluecode.chatbot.repository;

import com.bluecode.chatbot.domain.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Studies, Long>, StudyRepositoryCustom {

}