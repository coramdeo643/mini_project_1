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

/**
 * Board 관련 비즈니스 로직을 처리하는 Service 계층
 */
@RequiredArgsConstructor
@Service // IoC 대상
@Transactional(readOnly = true)
// 모든 메서드를 일기 전용 트랜잭션으로 실행(findAll, findById 최적화)
// 성능 최적화 (변경 감지 비활성화), 데이터 수정 방지 ()
// 데이터이스 락(lock) 최소화 하여 동시성 성능 개선
public class QnAService {

	private static final Logger log = LoggerFactory.getLogger(QnAService.class);
	private final QnAJpaRepository qnaJpaRepository;

	/**
	 * 게시글 저장
	 */
//    @Transactional
//    public QnA save(QnARequest.SaveDTO saveDTO, User sessionUser) {
//        log.info("게시글 저장 서비스 처리 시작 - 제목 {} , 작성자 {}",
//                saveDTO.getTitle(), sessionUser.getUsername());
//        QnA qna = saveDTO.toEntity(sessionUser);
//        qnaJpaRepository.save(qna);
//        log.info("게시글 저장 완료 - ID {} , 제목 {}",
//                qna.getId(), qna.getTitle());
//        return qna;
//    }
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

	/**
	 * 게시글 목록 조회
	 */
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

	/**
	 * 게시글 상세 조회
	 */
	public QnA findById(Long id, User sessionUser) {
		// 1. 로그 기록
		// 2. 데이터 베스에서 해당 board id 로 조회 -  WHERE
		// 3. 게시글이 없다면 404 에러 처리
		// 4. 조회 성공시 로그 기록
		// 5. 조회된 게시글 반환
		log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findByIdWithAuthors(id).orElseThrow(() -> {
			log.warn("게시글 조회 실패 - ID {}", id);
			return new Exception404("게시글을 찾을 수 없습니다");
		});
		// 댓글 정보(양방향 mapping) < 양방향 설정 Board < Replies 가져옴
		List<Reply> replies = qna.getReplies();
		// 댓글 소유권 설정(삭제 버튼 표시용)
		if (sessionUser != null) {
			replies.forEach(reply -> {
				boolean isReplyOwner = reply.isOwner(sessionUser.getId());
				reply.setReplyOwner(isReplyOwner);
			});
		}
		log.info("게시글 상세 조회 완료 - 제목 {}", qna.getTitle());
		return qna;
	}

	/**
	 * 게시글 수정(권한 체크 포함)
	 */
	@Transactional
	public QnA updateById(Long id, QnARequest.UpdateDTO updateDTO,
						  User sessionUser) {
		// 1. 로그 기록
		// 2. 수정하려는 게시글 조회
		// 3. 권한 체크
		// 4. 권한이 없다면 403 예외 발생
		// 5. Board 엔티티에 상태값 변경 (더티 체팅)
		// 6. 로그 기록 - 수정 완료
		// 7. 수정된 게시글 반환
		log.info("게시글 수정 서비스 시작 - 게시글 ID {}", id);
		QnA qna = qnaJpaRepository.findById(id).orElseThrow(() -> {
			log.warn("게시글 조회 실패 - ID {}", id);
			return new Exception404("해당 게시글이 존재하지 않습니다");
		});

		if (!qna.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 게시글만 수정 가능");
		}

		qna.setTitle(updateDTO.getTitle()); // 필드값 상태 변경
		qna.setContent(updateDTO.getContent()); // 필드값 상태 변경
		// TODO - board 엔티티에 update() 만들어 주기
		// 더티 체킹
		log.info("게시글 수정 완료 - 게시글 ID {}, 게시글 제목 {}", id, qna.getTitle());
		return qna;
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
		QnA qna = qnaJpaRepository.findById(id).orElseThrow(() -> {
			return new Exception404("삭제하려는 게시글이 없습니다");
		});
		if (!qna.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
		}
		qnaJpaRepository.deleteById(id);
	}

	/**
	 * 게시글 소유자 확인 (수정 화면 요청 확인용)
	 */
	public void checkBoardOwner(Long qnaId, Long userId, User sessionUser) {
		QnA qna = findById(qnaId, sessionUser);
		if (!qna.isOwner(userId)) {
			throw new Exception403("본인 게시글만 수정할 수 있습니다.");
		}
	}

	/**
	 * [수정] 게시글 상세 조회 (DTO 반환)
	 */
	public QnARequest.DetailDTO findById(Long id, Object sessionPrincipal) {
		log.info("게시글 상세 조회 서비스 시작 - ID {}", id);
		QnA qna = qnaJpaRepository.findByIdWithAuthors(id)
				.orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));
		log.info("게시글 상세 조회 완료 - 제목 {}", qna.getTitle());
		return new QnARequest.DetailDTO(qna, sessionPrincipal);
	}

	/**
	 * [수정] 게시글 수정 (통합 권한 체크)
	 */
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

	/**
	 * [수정] 게시글 삭제 (통합 권한 체크)
	 */
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