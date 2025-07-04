package com.tenco.blog.companySub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanySubJpaRepository extends JpaRepository<CompanySub, Long> {

	@Query("SELECT cs FROM CompanySub cs JOIN FETCH cs.user u JOIN FETCH cs.company c WHERE c.id = :id")
	List<CompanySub> findAllByUserAndCompanyId(@Param("id") Long id);
}
