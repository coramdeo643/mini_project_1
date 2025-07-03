package com.tenco.blog.UserSub;

import com.tenco.blog.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSubJpaRepository extends JpaRepository<UserSub, Long> {

	@Query("SELECT us FROM UserSub us JOIN FETCH us.user u JOIN FETCH us.company c WHERE u.id = :id")
	List<UserSub> findAllByUserAndCompanyId(@Param("id") Long id);
}
