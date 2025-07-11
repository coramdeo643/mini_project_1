package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.company.Company;
import com.tenco.blog.reply.Reply;
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

public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    private final BoardJpaRepository boardJpaRepository;


    @Transactional
    public Board save(BoardRequest.SaveDTO saveDTO, Company sessionUser) {
        log.info("게시글 저장 서비스 처리 시작 - 제목 {} , 작성자 {}",
                saveDTO.getTitle(), sessionUser.getUsername());
        Board board = saveDTO.toEntity(sessionUser);
        boardJpaRepository.save(board);
        log.info("게시글 저장 완료 - ID {} , 제목 {}",
                board.getId(), board.getTitle());
        return board;
    }

    public Page<Board> findAllPaging(Pageable pageable) {
        Page<Board> boardPage = boardJpaRepository.findAllJoinCompany(pageable);
        log.info("게시글 목록 조회 완료 - 총 게시글 {} 개, 총 {} 페이지 ", boardPage.getTotalElements(), boardPage.getTotalPages());
        return boardPage;
    }


    public Board findByIdWithReplies(Long id, User sessionUser) {
        log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
        Board board = boardJpaRepository.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글을 찾을 수 없습니다"));
        if (sessionUser != null) {
            boolean isBoardOwner = board.isOwner(sessionUser.getId());
            board.setBoardOwner(isBoardOwner);
        }
        return board;
    }


    public Board findById(Long id) {
        log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
        Board board = boardJpaRepository.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("게시글을 찾을 수 없습니다");
        });
        log.info("게시글 상세 조회 완료 - 제목 {}", board.getTitle());
        return board;
    }


    @Transactional
    public Board updateById(Long id, BoardRequest.UpdateDTO updateDTO,
                            Company sessionUser) {
        log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
        Board board = boardJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("해당 게시글이 존재하지 않습니다");
        });

        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 수정 가능");
        }

        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());
        log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, board.getTitle());
        return board;
    }


    @Transactional
    public void deleteById(Long id, Company sessionUser) {
        log.info("게시글 삭제 서비스 시작 - ID {}", id);
        Board board = boardJpaRepository.findById(id).orElseThrow(() -> {
            return new Exception404("삭제하려는 게시글이 없습니다");
        });
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        boardJpaRepository.deleteById(id);
    }


    public void checkBoardOwner(Long boardId, Long userId) {
        Board board = findById(boardId);
        if (!board.isOwner(userId)) {
            throw new Exception403("본인 게시글만 수정할 수 있습니다.");
        }
    }


    public Board findByIdWithBoard(Long id, Company sessionUser) {
        Board board = boardJpaRepository.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글을 찾을 수 없습니다."));
        if (sessionUser != null) {
            boolean isBoardOwner = board.isOwner(sessionUser.getId());
            board.setBoardOwner(isBoardOwner);
        }
        return board;
    }


    public List<Board> findBoardsBySubscribedUserId(Long id) {
        log.info("구독기업채용공고 조회 시작");
        List<Board> userSubBoardList = boardJpaRepository.findBoardsBySubscribedUserId(id);
        log.info("Total {} posts found", userSubBoardList.size());
        return userSubBoardList;
    }


}
