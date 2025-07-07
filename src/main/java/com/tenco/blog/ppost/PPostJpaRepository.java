package com.tenco.blog.ppost;


import com.tenco.blog.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PPostJpaRepository extends JpaRepository<PPost, Long> {

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u ORDER BY p.id DESC")
	List<PPost> findAllJoinUser();

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u WHERE p.id = :id")
	Optional<PPost> findByIdJoinUser(@Param("id") Long id);

	// 유저 아이디로 게시글 전부 가져오기
	@Query("SELECT p FROM PPost p JOIN FETCH p.user u WHERE u.id = :id")
	List<PPost> findByUserId(@Param("id") Long id);

	// 내가 구독한 구직자의 이력서를 조회하자
	@Query("SELECT p FROM PPost p " +
			"JOIN FETCH p.user u " +
			"JOIN CompanySub cs ON p.user.id = cs.user.id " +
			"WHERE cs.company.id = :companyId ORDER BY p.id DESC")
	List<PPost> findPostsBySubscribedUserId(@Param("companyId") Long companyId);

	// BoardRepository
	@Query("SELECT p FROM PPost p JOIN p.pPostSkills ps JOIN ps.skill s WHERE s.name = :skillName")
	List<PPost> findBySkillName(@Param("skillName") String skillName);

}
