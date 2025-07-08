package com.tenco.blog.application;

import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardJpaRepository;
import com.tenco.blog.company.Company;
import com.tenco.blog.company.CompanyRepository;
import com.tenco.blog.rating.RatingJpaRepository;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    // 양방향 이기에 여러 레파지토리를 가져올 것이다
    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);
    private final ApplicationJpaRepository applicationJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final CompanyRepository companyRepository;
    private final RatingJpaRepository ratingJpaRepository;



    // 지원 기능
    @Transactional
    public void save(ApplicationRequest.SaveDTO saveDTO, User sessionUser) {
        log.info("공고에 지원하는 서비스 처리 시작 - 공고 ID {} , 지원자 {} ",
                saveDTO.getBoardId(),sessionUser.getUsername());

        // 지원시 공고가 존재하는지 존재 여부 확인
        Board board = boardJpaRepository.findById(saveDTO.getBoardId())
                .orElseThrow(() -> new Exception404("존재하지 않는 공고임으로 지원 불가"));
        Company company = companyRepository.findById(saveDTO.getCompanyId())
                .orElseThrow(() -> new Exception404("존재하지 않는 회사 입니다."));

        // 준 영속상태
        Application application = saveDTO.toEntity(sessionUser,board,company);

        // 저장 - 정방향 insert 처리
        applicationJpaRepository.save(application);
    }


    public void deleteById(Long applicationId, User sessionUser) {
        log.info("지원 취소 서비스 처리 시작 - 댓글 ID{}");

        Application application = applicationJpaRepository.findById(applicationId)
                .orElseThrow(() -> new Exception404("지원한 이력이 없습니다."));


        applicationJpaRepository.deleteById(applicationId);
    }

    // 회사가 사용하는 지원자 확인
    public List<Application> findAllByBoardIdWithUser(Long companyId) {
        return applicationJpaRepository.findAllByBoardIdWithUser(companyId);
    }

    // 유저가 사용하는 공고 확인
    public List<Application> findAllByBoardIdWithBoard(Long userId) {
        return applicationJpaRepository.findAllByUserIdWithBoard(userId);
    }




    @Transactional
    public void updateStatus(Long applicationId, String status) {
        Application application = applicationJpaRepository.findById(applicationId)
                .orElseThrow(() -> new Exception404("해당 지원서를 찾을 수 없습니다."));
        application.setStatus(status); //
    }

    public List<Application> findAllByUserWithRatingStatus (Long userId) {
        // 1. 사용자의 전체 지원서 가져오기
        List<Application> applications = applicationJpaRepository.findAllByUserIdWithBoard(userId);

        // 2. 각 지원서에 대해 평점 남긴 적 있는지 확인
        for (Application application : applications) {
            Long companyId = application.getCompany().getId();
            boolean hasRated = ratingJpaRepository.existsByUserIdAndCompanyId(userId,companyId);
            application.setRated(hasRated);
        }

        return applications;
    }

}
