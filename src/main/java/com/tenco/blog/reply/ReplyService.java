package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardJpaRepository;
import com.tenco.blog.qna.QnA;
import com.tenco.blog.qna.QnAJpaRepository;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final keyword member init
@Service
public class ReplyService {

	private static final Logger log = LoggerFactory.getLogger(ReplyService.class);
	private final ReplyJPARepository replyJPARepository;
	private final QnAJpaRepository qnaJpaRepository;

	@Transactional
	public void save(ReplyRequest.SaveDTO saveDTO, Object sessionPrincipal) {
		if(sessionPrincipal == null) {
			throw new Exception403("댓글을 작성하려면 로그인이 필요합니다");
		}
		QnA qna = qnaJpaRepository.findById(saveDTO.getQnaId())
				.orElseThrow(() -> new Exception404("존재하지 않는 게시글입니다."));
		Reply reply = saveDTO.toEntity(sessionPrincipal, qna);
		replyJPARepository.save(reply);
	}


	@Transactional
	public void deleteById(Long replyId, Object sessionPrincipal) {
		log.info("댓글 삭제 서비스 처리 시작 - 댓글 ID {}", replyId);
		if(sessionPrincipal == null) {
			throw new Exception403("댓글을 삭제하려면 로그인이 필요합니다");
		}
		Reply reply = replyJPARepository.findById(replyId)
						.orElseThrow(() -> new Exception404("댓글을 찾을 수 없습니다"));
		if(!reply.isOwner(sessionPrincipal)) {
			throw new Exception403("본인이 작성한 댓글만 삭제할 수 있습니다");
		}
		replyJPARepository.deleteById(replyId);
	}


	public List<Reply> findAll() {
		List<Reply> replies = replyJPARepository.findAll();
		log.info("{}", replies.size());
		return replies;
	}
}
