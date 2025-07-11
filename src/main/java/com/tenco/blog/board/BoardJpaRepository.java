package com.tenco.blog.board;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardJpaRepository extends JpaRepository<Board, Long> {

	@Query("SELECT b FROM Board b JOIN FETCH b.company c ORDER BY b.id DESC")
	Page<Board> findAllJoinCompany(Pageable pageable);

	@Query("SELECT b FROM Board b JOIN FETCH b.company c WHERE b.id = :id")
	Optional<Board> findByIdJoinUser(@Param("id") Long id);

	@Query("SELECT b FROM Board b JOIN FETCH b.company c WHERE c.id = :id")
	List<Board> findByCompanyId(@Param("id") Long id);

	@Query("SELECT b FROM Board b " +
			"JOIN FETCH b.company c " +
			"JOIN UserSub us ON b.company.id = us.company.id " +
			"WHERE us.user.id = :userId ORDER BY b.id DESC")
	List<Board> findBoardsBySubscribedUserId(@Param("userId") Long userId);

	@Query("SELECT b FROM Board b JOIN b.boardSkills bs JOIN bs.skill s WHERE s.name = :skillName")
	Page<Board> findBySkillName(Pageable pageable, @Param("skillName") String skillName);

}
