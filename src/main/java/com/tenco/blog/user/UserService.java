package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 클래스에 읽기 전용
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJpaRepository userJpaRepository;


    @Transactional
    public User join(UserRequest.JoinDTO joinDTO){
        userJpaRepository.findByUsername(joinDTO.getUsername())
                .ifPresent(user1 -> {
                throw new Exception400("이미 존재하는 사용자 명입니다.");
        });
        return userJpaRepository.save(joinDTO.toEntity());
    }

    public User login(UserRequest.LoginDTO loginDTO){

        return  userJpaRepository
                .findByUsernameAndPassword(loginDTO.getUsername(),loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new Exception400("사용자명 또는 비밀번호가 틀렸어요");
                });
    }

    public User findById(Long id ){
        return userJpaRepository.findById(id).orElseThrow(() -> {
            log.info("사용자 조회 실패 ID {}" ,id);
            return new Exception404("사용자를 찾을 수 없습니다");
        });
    }


    @Transactional
    public User updateById(Long userId, UserRequest.UpdateDTO updateDTO){
        User user = findById(userId);
        user.update(updateDTO);
        return user;
    }
}
