package com.tenco.blog.rating;

import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.company.Company;
import com.tenco.blog.company.CompanyRepository;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final RatingJpaRepository ratingJpaRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void save(RatingRequest.SaveDTO saveDTO, User sessionUser){
        log.info("평점을 입력하는 서비스 처리 시작 - 회사 ID {},지원자 {}",
                saveDTO.getCompanyId(),sessionUser.getUsername());

        Company company = companyRepository.findById(saveDTO.getCompanyId())
                .orElseThrow(() -> new Exception404("존재하지 않는 회사 입니다."));

        Rating rating = saveDTO.toEntity(sessionUser,company);

        ratingJpaRepository.save(rating);

    }

    @Transactional
    public void delete(Long userId, Long companyId) {
        Rating rating = ratingJpaRepository.findByUserIdAndCompanyId(userId, companyId);

        if (rating == null) {
            throw new Exception404("해당 평점을 찾을 수 없습니다.");
        }

        ratingJpaRepository.delete(rating);
    }

    // 평균점을 반환해주는 메서드
    public Double avg (Long companyid) {
        Double score = ratingJpaRepository.findAvgScoreByCompanyId(companyid);
        return (score != null) ? Math.round(score * 10.0) / 10.0 : 0.0; // 소수점 첫번쩨 자리까지 반올림
    }
}
