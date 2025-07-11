package com.tenco.blog.application;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardJpaRepository;
import com.tenco.blog.company.Company;
import com.tenco.blog.company.CompanyRepository;
import com.tenco.blog.rating.RatingJpaRepository;
import com.tenco.blog.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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


    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);
    private final ApplicationJpaRepository applicationJpaRepository;
    private final BoardJpaRepository boardJpaRepository;
    private final CompanyRepository companyRepository;
    private final RatingJpaRepository ratingJpaRepository;


    @Transactional
    public void save(ApplicationRequest.SaveDTO saveDTO, User sessionUser) {
        log.info("공고에 지원하는 서비스 처리 시작 - 공고 ID {} , 지원자 {} ",
                saveDTO.getBoardId(),sessionUser.getUsername());
        Board board = boardJpaRepository.findById(saveDTO.getBoardId())
                .orElseThrow(() -> new Exception404("존재하지 않는 공고임으로 지원 불가"));
        Company company = companyRepository.findById(saveDTO.getCompanyId())
                .orElseThrow(() -> new Exception404("존재하지 않는 회사 입니다."));
        Application application = saveDTO.toEntity(sessionUser,board,company);
        applicationJpaRepository.save(application);
    }
    @Transactional
    public void deleteById(Long applicationId, Long boardId, User sessionUser) {
        log.info("지원 취소 서비스 처리 시작 - Application  ID {} ", applicationId);
        Application application = applicationJpaRepository.findByApplicationId(sessionUser.getId(), boardId);
        if(!application.getUser().getId().equals( sessionUser.getId())) {
            throw new Exception403("권한이 없습니다!!");
        }
        applicationJpaRepository.deleteById(application.getId());
    }


    @Transactional
    public void deleteByListId(Long applicationId, User sessionUser) {
        log.info("지원 취소 서비스 처리 시작 - Application  ID {} ", applicationId);
        List<Application> application = applicationJpaRepository.findByUserIdAndApplicationId(sessionUser.getId(), applicationId);
        if (application.isEmpty()){
            throw new Exception403("해당 지원서를 삭제할 권한이 없습니다.");
        }
        applicationJpaRepository.deleteById(applicationId);
    }


    @Transactional
    public List<Application> findAllByBoardIdWithUser(Long companyId) {
        return applicationJpaRepository.findAllByBoardIdWithUser(companyId);
    }


    @Transactional
    public List<Application> findAllByBoardIdWithBoard(Long userId) {
        return applicationJpaRepository.findAllByUserIdWithBoard(userId);
    }


    @Transactional
    public void updateStatus(Long applicationId, String status) {
        Application application = applicationJpaRepository.findById(applicationId)
                .orElseThrow(() -> new Exception404("해당 지원서를 찾을 수 없습니다."));
        application.setStatus(status); //
    }


    @Transactional
    public List<Application> findAllByUserWithRatingStatus (Long userId) {
        List<Application> applications = applicationJpaRepository.findAllByUserIdWithBoard(userId);
        for (Application application : applications) {
            Long companyId = application.getCompany().getId();
            boolean hasRated = ratingJpaRepository.existsByUserIdAndCompanyId(userId,companyId);
            application.setRated(hasRated);
        }
        return applications;
    }


    @Transactional
    public List<Application> findAllBoardsWithApplyStatus(Long userId) {
        List<Application> applications = applicationJpaRepository.findAllByUserIdWithBoard(userId);

        for (Application application : applications) {
            Long boardId = application.getBoard().getId();
            boolean hasApplied = applicationJpaRepository.existsByUserIdAndBoardId(userId, boardId);
            application.setOnaji(hasApplied);
        }
        return applications;
    }



}
