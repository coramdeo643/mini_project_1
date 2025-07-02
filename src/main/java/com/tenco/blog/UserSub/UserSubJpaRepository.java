package com.tenco.blog.UserSub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSubJpaRepository extends JpaRepository<UserSub, Long> {

	@Query("SELECT us FROM UserSub us JOIN FETCH us.user u JOIN FETCH us.company c ORDER BY us.id DESC")
	List<UserSub> findAllJoinUser();

	@Query("SELECT us FROM UserSub us JOIN FETCH us.user u JOIN FETCH us.company c WHERE us.id = :id")
	Optional<UserSub> findByIdJoinUser(@Param("id") Long id);

	@Query("SELECT us FROM UserSub us JOIN FETCH us.user u JOIN FETCH us.company c WHERE us.id = :id")
	List<UserSub> findAllByIdJoinUser(@Param("id") Long id);

}
