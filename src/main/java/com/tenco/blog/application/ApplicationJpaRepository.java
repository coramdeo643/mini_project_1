package com.tenco.blog.application;

import com.tenco.blog.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ApplicationJpaRepository extends JpaRepository<Application,Long> {

//     회사가 지원자의 합격/불합격 통보 기능을 status 의 값을 변경해주면서 알려줄 예정

//     공고 ID 로 한번에 유저 정보도 가져오기 JOIN FETCH 사용하면 됨
// 회사가 등록한 공고에 대한 모든 지원자를 가져오기

       @Query("SELECT b FROM Board b JOIN FETCH b.company c ORDER BY b.id DESC")
       List<Board> findAllJoinUser();

       // 회사가 사용하는거
//       @Query("SELECT a FROM Application a JOIN FETCH a.user u WHERE a.board.id = :boardId")
//       List<Application> findAllByBoardIdWithUser(@Param("boardId") Long boardId);

       // 회사 테스트
       @Query("SELECT a FROM Application a " +
               "JOIN FETCH a.user u " +
               "JOIN FETCH a.board b " +
               "WHERE b.company.id = :companyId")
       List<Application> findAllByBoardIdWithUser(@Param("companyId") Long companyId);
       // 유저가 사용하는거
       @Query("SELECT a FROM Application a JOIN FETCH a.board b WHERE a.user.id = :userId")
       List<Application> findAllByUserIdWithBoard(@Param("userId") Long userId);

//       // 1 단계 모든회사의 모든 지원자 보기
//       @Query("SELECT a FROM Application a " +
//               "JOIN FETCH a.board b " +
//               "JOIN FETCH a.user u " +
//               "WHERE b.company.id = :companyId")
//       List<Application> findAllByCompanyId(@Param("companyId") Long companyId);

       // 2 단계 회사의 모든 공고 보기
       @Query("SELECT a FROM Application a " +
               "JOIN FETCH a.board b " +
               "JOIN FETCH a.user u " +
               "JOIN FETCH a.company c " +
               "WHERE b.company.id = :companyId")
       List<Application> findAllByCompanyId(@Param("companyId") Long companyId);




       // 3 . 2단계의 쿼리문과 비교 점이 먼지 알아보고 사용할 것
       @Query("SELECT a FROM Application a JOIN FETCH a.board b JOIN FETCH b.company JOIN FETCH a.user u")
       List<Application> findAllJoinUserAndBoardAndCompany();




       // 유저 입장에서 자기가 지원한 공고들 조회
       @Query("SELECT a FROM Application a JOIN FETCH a.board b JOIN FETCH b.company c WHERE a.user.id = :userId")
       List<Application> findAllByUserIdWithBoardAndCompany(@Param("userId") Long userId);



}
