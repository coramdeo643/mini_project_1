package com.tenco.blog.application;

import com.tenco.blog.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ApplicationJpaRepository extends JpaRepository<Application,Long> {


       @Query("SELECT a FROM Application a " +
               "JOIN FETCH a.user u " +
               "JOIN FETCH a.board b " +
               "WHERE b.company.id = :companyId")
       List<Application> findAllByBoardIdWithUser(@Param("companyId") Long companyId);


       @Query("SELECT a FROM Application a JOIN FETCH a.board b WHERE a.user.id = :userId")
       List<Application> findAllByUserIdWithBoard(@Param("userId") Long userId);


       @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.user.id = :userId AND a.board.id = :boardId")
       boolean existsByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);


       @Query("SELECT a FROM Application a WHERE a.user.id = :userId AND a.board.id = :boardId")
       Application findByApplicationId(@Param("userId") Long id, @Param("boardId") Long boardId);

       @Query("SELECT a FROM Application a JOIN FETCH a.user WHERE a.user.id = :userId AND a.id = :applicationId")
       List<Application> findByUserIdAndApplicationId(@Param("userId") Long userId, @Param("applicationId") Long applicationId);

}
