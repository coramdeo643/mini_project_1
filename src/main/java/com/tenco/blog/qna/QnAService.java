package com.tenco.blog.qna;

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

public class QnAService {

	private static final Logger log = LoggerFactory.getLogger(QnAService.class);
	private final QnAJpaRepository qnaJpaRepository;


	@Transactional
	public QnA save(QnARequest.SaveDTO saveDTO, Object session) {
		QnA qna = new QnA();
		if (session instanceof User) {
			User sessionUser = (User) session;
			qna = saveDTO.toEntity(sessionUser);
		} else if (session instanceof Company) {
			Company sessionCompany = (Company) session;
			qna = saveDTO.toEntity(sessionCompany);
		}
		qnaJpaRepository.save(qna);
		log.info("게시글 저장 완료 - ID {} , 제목 {}",
				qna.getId(), qna.getTitle());
		return qna;
	}


	public List<QnA> findAll() {
		log.info("게시글 조회 서비스 처리 시작");
		List<QnA> qnaList = qnaJpaRepository.findAllWithAuthors();
		log.info("게시글 목록 조회 완료 - 총 {} 개", qnaList.size());
		return qnaList;
	}


	public Page<QnA> findAllPaging(Pageable pageable) {
		Page<QnA> qnaPage = qnaJpaRepository.findAllPaging(pageable);
		log.info("게시글 목록 조회 완료 - 총 {} 개", qnaPage.getTotalElements());
		return qnaPage;
	}


	public QnA findById(Long id, User sessionUser) {
		log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findByIdWithAuthors(id).orElseThrow(() -> {
			log.warn("게시글 조회 실패 - ID {}", id);
			return new Exception404("게시글을 찾을 수 없습니다");
		});
		List<Reply> replies = qna.getReplies();
		if (sessionUser != null) {
			replies.forEach(reply -> {
				boolean isReplyOwner = reply.isOwner(sessionUser.getId());
				reply.setReplyOwner(isReplyOwner);
			});
		}
		log.info("게시글 상세 조회 완료 - 제목 {}", qna.getTitle());
		return qna;
	}


	@Transactional
	public QnA updateById(Long id, QnARequest.UpdateDTO updateDTO,
						  User sessionUser) {
		log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
		QnA qna = qnaJpaRepository.findById(id).orElseThrow(() -> {
			log.warn("게시글 조회 실패 - ID {}", id);
			return new Exception404("해당 게시글이 존재하지 않습니다");
		});
		if (!qna.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 게시글만 수정 가능");
		}
		qna.setTitle(updateDTO.getTitle());
		qna.setContent(updateDTO.getContent());
		log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, qna.getTitle());
		return qna;
	}


	@Transactional
	public void deleteById(Long id, User sessionUser) {
		log.info("게시글 삭제 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findById(id).orElseThrow(() -> {
			return new Exception404("삭제하려는 게시글이 없습니다");
		});
		if (!qna.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
		}
		qnaJpaRepository.deleteById(id);
	}


	public void checkBoardOwner(Long qnaId, Long userId, User sessionUser) {
		QnA qna = findById(qnaId, sessionUser);
		if (!qna.isOwner(userId)) {
			throw new Exception403("본인 게시글만 수정할 수 있습니다.");
		}
	}


	public QnARequest.DetailDTO findById(Long id, Object sessionPrincipal) {
		log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findByIdWithAuthors(id)
				.orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));
		log.info("게시글 상세 조회 완료 - 제목 {}", qna.getTitle());
		return new QnARequest.DetailDTO(qna, sessionPrincipal);
	}


	@Transactional
	public void updateById(Long id, QnARequest.UpdateDTO updateDTO, Object sessionPrincipal) {
		log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
		QnA qna = qnaJpaRepository.findById(id)
				.orElseThrow(() -> new Exception404("해당 게시글이 존재하지 않습니다"));

		if (!qna.isOwner(sessionPrincipal)) {
			throw new Exception403("본인이 작성한 게시글만 수정 가능합니다.");
		}

		qna.setTitle(updateDTO.getTitle());
		qna.setContent(updateDTO.getContent());
		log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, qna.getTitle());
	}


	@Transactional
	public void deleteById(Long id, Object sessionPrincipal) {
		log.info("게시글 삭제 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findById(id)
				.orElseThrow(() -> new Exception404("삭제하려는 게시글이 없습니다"));

		if (!qna.isOwner(sessionPrincipal)) {
			throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다.");
		}
		qnaJpaRepository.deleteById(id);
	}

}