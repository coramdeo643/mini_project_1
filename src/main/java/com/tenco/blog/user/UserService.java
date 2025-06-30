package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true) // 클래스 레벨에서의 읽기 전용 설정
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJpaRepository userJpaRepository;

    /**
     * 회원가입 처리
     */
    @Transactional
    public User joinPersonal(UserRequest.JoinPersonalDTO joinPersonalDTO) {
        userJpaRepository.findByUsername(joinPersonalDTO.getUsername()).ifPresent(user1 -> {
            throw new Exception400("이미 존재하는 사용자 명입니다.");
        });
        return userJpaRepository.save(joinPersonalDTO.toEntity());
    }

    @Transactional
    public User joinCompany(UserRequest.JoinCompanyDTO joinCompanyDTO) {
        userJpaRepository.findByUsername(joinCompanyDTO.getUsername()).ifPresent(user1 -> {
            throw new Exception400("이미 존재하는 사용자 명입니다.");
        });
        return userJpaRepository.save(joinCompanyDTO.toEntity());
    }

    @Transactional
    public User joinAdmin(UserRequest.JoinAdminDTO joinAdminDTO) {
        userJpaRepository.findByUsername(joinAdminDTO.getUsername()).ifPresent(user1 -> {
            throw new Exception400("이미 존재하는 사용자 명입니다.");
        });
        return userJpaRepository.save(joinAdminDTO.toEntity());
    }


    /**
     * 로그인 처리
     */
    public User login(UserRequest.LoginDTO loginDTO) {
        return userJpaRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new Exception400("사용자 명 또는 비밀번호를 확인해주세요.");
                });
    }

    /**
     * 사용자 정보 조회
     */
    public User findById(Long id) {
        return userJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("사용자 조회 실패 - id {}", id);
            return new Exception400("사용자를 찾을 수 없습니다.");
        });
    }

    /**
     * 회원 정보 수정 처리 (더티 체킹)
     */
    @Transactional
    public User updateById(Long userId, UserRequest.UpdatePersonalDTO updatePersonalDTO) {
        User user = findById(userId);
        user.updatePersonal(updatePersonalDTO);
        return user;
    }

    @Transactional
    public User updateById(Long userId, UserRequest.UpdateCompanyDTO updateCompanyDTO) {
        User user = findById(userId);
       user.updateCompany(updateCompanyDTO);
        user.setPassword(updateCompanyDTO.getPassword());
        return user;
    }


}
