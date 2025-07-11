package com.tenco.blog.ppost;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PPostService {
    private static final Logger log = LoggerFactory.getLogger(PPostService.class);
    private final PPostJpaRepository PPostJpaRepository;


    @Transactional
    public PPost save(PPostRequest.SaveDTO saveDTO, User sessionUser) {
        log.info("게시글 저장 서비스 처리 시작 - 제목 {} , 작성자 {}",
                saveDTO.getTitle(), sessionUser.getUsername());
        PPost PPost = saveDTO.toEntity(sessionUser);
        PPostJpaRepository.save(PPost);
        log.info("게시글 저장 완료 - ID {} , 제목 {}",
                PPost.getId(), PPost.getTitle());
        return PPost;
    }

    public Page<PPost> findAllPaging(Pageable pageable) {
        Page<PPost> ppostPage = PPostJpaRepository.findAllJoinUser(pageable);
        log.info("게시글 목록 조회 완료 - 총 게시글 {} 개, 총 {} 페이지 ", ppostPage.getTotalElements(), ppostPage.getTotalPages());
        return ppostPage;
    }


    public List<PPost> findAll() {
        log.info("게시글 조회 서비스 처리 시작");
        List<PPost> PPostList = PPostJpaRepository.findAllJoinUser();
        log.info("게시글 목록 조회 완료 - 총 {} 개", PPostList.size());
        return PPostList;
    }


    public PPost findById(Long id) {
        log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
        PPost PPost = PPostJpaRepository.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("게시글을 찾을 수 없습니다");
        });
        log.info("게시글 상세 조회 완료 - 제목 {}", PPost.getTitle());
        return PPost;
    }


    @Transactional
    public PPost updateById(Long id, PPostRequest.UpdateDTO updateDTO,
                            User sessionUser) {
        log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
        PPost PPost = PPostJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("해당 게시글이 존재하지 않습니다");
        });

        if(!PPost.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 수정 가능");
        }

        PPost.setTitle(updateDTO.getTitle());
        PPost.setContent(updateDTO.getContent());
        log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, PPost.getTitle());
        return PPost;
    }


    @Transactional
    public void deleteById(Long id, User sessionUser) {
        log.info("게시글 삭제 서비스 시작 - ID {}", id);
        PPost PPost = PPostJpaRepository.findById(id).orElseThrow(() -> {
            return new Exception404("삭제하려는 게시글이 없습니다");
        });
        if(!PPost.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        PPostJpaRepository.deleteById(id);
    }


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


    public List<PPost> findPostsBySubscribedUserId(Long id) {
        log.info("구독기업채용공고 조회 시작");
        List<PPost> companySubPpostList = PPostJpaRepository.findPostsBySubscribedUserId(id);
        log.info("Total {} posts found", companySubPpostList.size());
        return companySubPpostList;
    }

}
