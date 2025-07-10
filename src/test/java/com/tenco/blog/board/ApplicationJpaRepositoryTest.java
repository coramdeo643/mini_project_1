package com.tenco.blog.board;

import com.tenco.blog.application.Application;
import com.tenco.blog.application.ApplicationJpaRepository;
import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class ApplicationJpaRepositoryTest {

    @Autowired
    private ApplicationJpaRepository applicationJpaRepository;

    @Test
    void 지원기록_삭제_정상작동() {
        // given
        Long applicationId = 1L; // 사전 삽입된 ID로 테스트

        // when
        applicationJpaRepository.deleteById(applicationId);

        // then
        Optional<Application> result = applicationJpaRepository.findById(applicationId);
        assertThat(result).isEmpty(); // 삭제 성공 확인
    }
}