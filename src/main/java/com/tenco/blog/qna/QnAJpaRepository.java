package com.tenco.blog.qna;

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

    /**
     * [수정] QnA 목록 조회 시, 작성자인 User와 Company 정보를 함께 가져옵니다.
     * LEFT JOIN을 사용하여 개인 또는 기업 회원이 작성한 모든 글을 포함합니다.
     */
    @Query("SELECT q FROM QnA q " +
            "LEFT JOIN FETCH q.user u " +
            "LEFT JOIN FETCH q.company c " +
            "ORDER BY q.id DESC")
    List<QnA> findAllWithAuthors();

    /**
     * [수정] QnA 상세 조회 시, 작성자인 User와 Company 정보를 함께 가져옵니다.
     * @param id QnA의 ID
     * @return User 또는 Company 정보가 포함된 QnA Optional 객체
     */
    @Query("SELECT q FROM QnA q " +
            "LEFT JOIN FETCH q.user u " +
            "LEFT JOIN FETCH q.company c " +
            "WHERE q.id = :id")
    Optional<QnA> findByIdWithAuthors(@Param("id") Long id);
}