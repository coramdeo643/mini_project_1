package com.tenco.blog.rating;

import com.tenco.blog.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingJpaRepository extends JpaRepository <Rating,Long> {




        @Query("SELECT COUNT(r) > 0 FROM Rating r WHERE r.user.id = :userId AND r.company.id = :companyId")
        boolean existsByUserIdAndCompanyId(@Param("userId") Long userId, @Param("companyId") Long companyId);



        @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.company.id = :companyId")
        Rating findByUserIdAndCompanyId(@Param("userId") Long userId, @Param("companyId") Long companyId);

        @Query("SELECT AVG(r.score) FROM Rating r WHERE r.company.id = :companyId")
        Double findAvgScoreByCompanyId(@Param("companyId") Long companyId);

}



