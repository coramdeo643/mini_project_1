package com.tenco.blog.ppost;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PPostJpaRepository extends JpaRepository<PPost, Long> {

    @Query("SELECT p FROM PPost p JOIN FETCH p.user u ORDER BY p.id DESC")
    List<PPost> findAllJoinUser();

    @Query("SELECT p FROM PPost p JOIN FETCH p.user u WHERE p.id = :id")
    Optional<PPost> findByIdJoinUser(@Param("id")Long id);

}
