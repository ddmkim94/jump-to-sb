package com.ll.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findBySubjectContains(String kw, Pageable pageable);
    Page<Question> findBySubjectContainsOrContentContains(String kw, String kw_, Pageable pageable);

    Page<Question> findBySubjectContainsOrContentContainsOrAuthor_Username(String kw, String kw_, String kw__, Pageable pageable);
    Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameOrAnswerList_ContentContains(String kw, String kw_, String kw__, String kw___, Pageable pageable);
    Page<Question> findAllByOrderByIdDesc(Pageable pageable);
    List<Question> findAllByOrderByIdDesc();
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Transactional
    @Modifying
    @Query(value = "truncate question", nativeQuery = true)
    void truncate();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyChecks();


}
