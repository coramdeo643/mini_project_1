package com.tenco.blog.company;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("SELECT c FROM Company c WHERE c.username = :username AND c.password = :password ")
    Optional<Company> findByUsernameAndPassword(@Param("username") String username,
                                             @Param("password") String password);

    @Query("SELECT c FROM Company c WHERE c.username = :username")
    Optional<Company> findByUsername(@Param("username") String username);

}
