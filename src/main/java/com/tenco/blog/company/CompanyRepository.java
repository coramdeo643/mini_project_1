package com.tenco.blog.company;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    // 사용자명과 비밀번호로 사용자 조회 (로그인용) //
    @Query("SELECT c FROM Company c WHERE c.username = :username AND c.password = :password ")
    Optional<Company> findByUsernameAndPassword(@Param("username") String username,
                                             @Param("password") String password);

    // 사용자 이름으로 사용자 조회(중복 체크용)
    // @Query(value = "select * from user_tb where username = :username", nativeQuery = true)
    //JPQL 명시적으로 입력해서 사용함
    @Query("SELECT c FROM Company c WHERE c.username = :username")
    Optional<Company> findByUsername(@Param("username") String username);

}
