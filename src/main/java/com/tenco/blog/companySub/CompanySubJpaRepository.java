package com.tenco.blog.companySub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanySubJpaRepository extends JpaRepository<CompanySub, Long> {

	@Query("SELECT cs FROM CompanySub cs JOIN FETCH cs.user u JOIN FETCH cs.company c WHERE c.id = :id")
	List<CompanySub> findAllByUserAndCompanyId(@Param("id") Long id);

	// 이 회사가 이 유저를 구독했는지 존재여부 확인 쿼리
	@Query("select count(*) > 0 from CompanySub cs where cs.company.id = :companyId and cs.user.id = :userId")
	boolean existsByCompanyIdAndUserId(Long companyId, Long userId);
}
