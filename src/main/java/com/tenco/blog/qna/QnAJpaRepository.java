package com.tenco.blog.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnAJpaRepository extends JpaRepository<QnA, Long> {

    @Query("SELECT q FROM QnA q JOIN FETCH q.user u ORDER BY q.id DESC")
    List<QnA> findAllJoinUser();

    @Query("SELECT q FROM QnA q JOIN FETCH q.user u WHERE q.id = :id")
    Optional<QnA> findByIdJoinUser(@Param("id")Long id);

    @Query("SELECT q FROM QnA q " +
            "LEFT JOIN FETCH q.user u " +
            "LEFT JOIN FETCH q.company c " +
            "ORDER BY q.id DESC")
    List<QnA> findAllWithAuthors();

    @Query("SELECT q FROM QnA q LEFT JOIN FETCH q.user u LEFT JOIN FETCH q.company c ORDER BY q.id DESC")
    Page<QnA> findAllPaging(Pageable pageable);

    @Query("SELECT q FROM QnA q " +
            "LEFT JOIN FETCH q.user u " +
            "LEFT JOIN FETCH q.company c " +
            "WHERE q.id = :id")
    Optional<QnA> findByIdWithAuthors(@Param("id") Long id);
}