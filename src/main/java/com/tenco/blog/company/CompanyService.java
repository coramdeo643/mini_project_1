package com.tenco.blog.company;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;


    /**
     * 회원가입 처리
     */
    @Transactional // 메서드 레벨에서 쓰기 전용 트랜잭션 활성화
    public Company join(CompanyRequset.JoinDTO joinDTO){
        // 1. 사용자 명 중복 체크
        companyRepository.findByUsername(joinDTO.getUsername())
                .ifPresent(user1 -> {
                    throw new Exception400("이미 존재하는 사용자 명입니다.");
                });
//        User user = userJpaRepository.findByUsername(joinDTO.getUsername()).orElseThrow(() -> {
//           return new Exception400("이미 존재하는 사용자 명입니다.");
//        });

//        User user = joinDTO.toEntity();
//        User savedUser = userJpaRepository.save(user);
//        return user;
        return companyRepository.save(joinDTO.toEntity());
    }

    /**
     * 로그인 처리
     */
    public Company login(CompanyRequset.LoginDTO loginDTO){

        return  companyRepository
                .findByUsernameAndPassword(loginDTO.getUsername(),loginDTO.getPassword())
                .orElseThrow(() -> {
                    return new Exception400("사용자명 또는 비밀번호가 틀렸어요");
                });
    }

    /**
     * 사용자 정보 조회
     */
    public Company findById(Long id ){
        return companyRepository.findById(id).orElseThrow(() -> {
            log.info("사용자 조회 실패 ID {}" ,id);
            return new Exception404("사용자를 찾을 수 없습니다");
        });
    }

    /**
     *  회원 정보 수정 처리 (더티 체킹)
     */
    @Transactional
    public Company updateById(Long userId, CompanyRequset.UpdateDTO updateDTO){
        // 1.
        // 2. 사용자 조회
        // 3. 수정된 User 반환 왜? ----. 세션 동기화 때문!!1
        Company company = findById(userId);
        //user.update(updateDTO); TODO 추후 추가
        company.setPassword(updateDTO.getPassword());
        return company;
    }
}
