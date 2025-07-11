package com.tenco.blog.ppost;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PPostJpaRepository extends JpaRepository<PPost, Long> {

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u ORDER BY p.id DESC")
	Page<PPost> findAllJoinUser(Pageable pageable);

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u ORDER BY p.id DESC")
	List<PPost> findAllJoinUser();

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u WHERE p.id = :id")
	Optional<PPost> findByIdJoinUser(@Param("id") Long id);

	@Query("SELECT p FROM PPost p JOIN FETCH p.user u WHERE u.id = :id")
	List<PPost> findByUserId(@Param("id") Long id);

	@Query("SELECT p FROM PPost p " +
			"JOIN FETCH p.user u " +
			"JOIN CompanySub cs ON p.user.id = cs.user.id " +
			"WHERE cs.company.id = :companyId ORDER BY p.id DESC")
	List<PPost> findPostsBySubscribedUserId(@Param("companyId") Long companyId);

	// BoardRepository
	@Query("SELECT p FROM PPost p JOIN p.pPostSkills ps JOIN ps.skill s WHERE s.name = :skillName")
	Page<PPost> findBySkillName(Pageable pageable, @Param("skillName") String skillName);

}
