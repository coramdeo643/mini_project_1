package com.tenco.blog.ppost;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service // IoC 대상
@Transactional(readOnly = true)
public class PPostService {
    private static final Logger log = LoggerFactory.getLogger(PPostService.class);
    private final PPostJpaRepository PPostJpaRepository;

    /**
     * 게시글 저장
     */
    @Transactional // 데이 수정이 필요하는 읽지 전용 설정을 해제하고 쓰기 전용로 변환
    public PPost save(PPostRequest.SaveDTO saveDTO, User sessionUser) {
        log.info("게시글 저장 서비스 처리 시작 - 제목 {} , 작성자 {}",
                saveDTO.getTitle(), sessionUser.getUsername());
        PPost PPost = saveDTO.toEntity(sessionUser);
        PPostJpaRepository.save(PPost);
        log.info("게시글 저장 완료 - ID {} , 제목 {}",
                PPost.getId(), PPost.getTitle());
        return PPost;
    }

    /**
     * 게시글 목록 조회
     */
    public List<PPost> findAll() {
        // 1. 로그 기록
        // 2. 데이베이스 게시글 조회
        // 3. 로그 기록
        // 4. 조회된 게시글 목록 반환
        log.info("게시글 조회 서비스 처리 시작");
        List<PPost> PPostList = PPostJpaRepository.findAllJoinUser();
        log.info("게시글 목록 조회 완료 - 총 {} 개", PPostList.size());
        return PPostList;
    }

    /**
     * 게시글 상세 조회
     */
    public PPost findById(Long id) {
        // 1. 로그 기록
        // 2. 데이터 베스에서 해당 board id 로 조회 -  WHERE
        // 3. 게시글이 없다면 404 에러 처리
        // 4. 조회 성공시 로그 기록
        // 5. 조회된 게시글 반환
        log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
        PPost PPost = PPostJpaRepository.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("게시글을 찾을 수 없습니다");
        });
        log.info("게시글 상세 조회 완료 - 제목 {}", PPost.getTitle());
        return PPost;
    }

    /**
     *  게시글 수정(권한 체크 포함)
     */
    @Transactional
    public PPost updateById(Long id, PPostRequest.UpdateDTO updateDTO,
                            User sessionUser) {
        // 1. 로그 기록
        // 2. 수정하려는 게시글 조회
        // 3. 권한 체크
        // 4. 권한이 없다면 403 예외 발생
        // 5. Board 엔티티에 상태값 변경 (더티 체팅)
        // 6. 로그 기록 - 수정 완료
        // 7. 수정된 게시글 반환
        log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
        PPost PPost = PPostJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("해당 게시글이 존재하지 않습니다");
        });

        if(!PPost.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 수정 가능");
        }

        PPost.setTitle(updateDTO.getTitle()); // 필드값 상태 변경
        PPost.setContent(updateDTO.getContent()); // 필드값 상태 변경
        // TODO - board 엔티티에 update() 만들어 주기
        // 더티 체킹
        log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, PPost.getTitle());
        return PPost;
    }

    /**
     * 게시글 삭제 (권한 체크)
     */
    @Transactional
    public void deleteById(Long id, User sessionUser) {
        // 1. 로그 기록
        // 2. 삭제 하려는 게시글 조회
        // 3. 권한 체크
        // 4. 권한이 없으면 403 예외 처리
        // 5. 데이터 베이스 삭제 처리
        // 6. 삭제 완료 로그 기록
        log.info("게시글 삭제 서비스 시작 - ID {}", id);
        PPost PPost = PPostJpaRepository.findById(id).orElseThrow(() -> {
            return new Exception404("삭제하려는 게시글이 없습니다");
        });
        if(!PPost.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        PPostJpaRepository.deleteById(id);
    }

    /**
     *  게시글 소유자 확인 (수정 화면 요청 확인용)
     */
    public void checkBoardOwner(Long boardId, Long userId) {
        PPost PPost = findById(boardId);
        if(!PPost.isOwner(userId)) {
            throw new Exception403("본인 게시글만 수정할 수 있습니다.");
        }
    }

    public PPost findByIdWithPPost(Long id, User sessionUser) {
        PPost ppost = PPostJpaRepository.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글을 찾을 수 없습니다."));
        if (sessionUser != null) {
            boolean isPPostOwner = ppost.isOwner(sessionUser.getId());
            ppost.setPPostOwner(isPPostOwner);
        }
        return ppost;
    }
}
