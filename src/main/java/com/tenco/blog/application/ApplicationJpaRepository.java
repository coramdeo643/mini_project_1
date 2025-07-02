package com.tenco.blog.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ApplicationJpaRepository extends JpaRepository<Application,Long> {

    // 회사가 지원자의 합격/불합격 통보 기능을 status 의 값을 변경해주면서 알려줄 예정

    // 중복 확인 체크할 예정
}
