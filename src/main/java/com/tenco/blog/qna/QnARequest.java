package com.tenco.blog.qna;

import com.tenco.blog.company.Company;
import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.User;
import lombok.Data;

import java.util.List;

/**
 * 클라이언트에게 넘어온 데이터를
 * Object로 변화해서 전달하는 DTO 역할을 담당한다
 */
public class QnARequest {

	// 게시글 저장 DTO
	@Data
	public static class SaveDTO {
		private String title;
		private String content;
		// username 제거 : 세션에서 가져올 예정

		// (User) <-- toEntity() 호출할 때 세션에서 가져와서 넣어 주면 됨
		public QnA toEntity(Object object) {
			return QnA.builder()
					.title(this.title)
					.content(this.content)
					.user(object instanceof User ? (User) object : null)
					.company(object instanceof Company ? (Company) object : null)
					.build();
		}

		public void validate() {
			if (title == null || title.trim().isEmpty()) {
				throw new IllegalArgumentException("제목은 필수야");
			}
			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("내용은 필수야");
			}
		}
	}

	// 게시글 수정용 DTO 설계
	@Data
	public static class UpdateDTO {
		private String title;
		private String content;

		// toEntity 메서드 안 만들 예정 (더티 체킹 활용)
		// em.find() <--- Board <-- 영속화 <-- 상태값을 변경하면 자동 갱신

		// 유효성 검사
		public void validate() {
			if (title == null || title.trim().isEmpty()) {
				throw new IllegalArgumentException("제목은 필수야");
			}
			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("내용은 필수야");
			}
		}
	}

	@Data
	public static class DetailDTO {
		private Long id;
		private String title;
		private String content;
		private String authorName; // 작성자 이름을 하나로 통합
		private boolean isOwner;   // 최종 소유권 여부
		private List<Reply> replies;

		public DetailDTO(QnA qna, Object sessionPrincipal) {
			this.id = qna.getId();
			this.title = qna.getTitle();
			this.content = qna.getContent();

			// 작성자 이름 설정 (개인 또는 기업)
			if (qna.getUser() != null) {
				this.authorName = qna.getUser().getPersonalName();
			} else if (qna.getCompany() != null) {
				this.authorName = qna.getCompany().getBusinessName();
			} else {
				this.authorName = "알 수 없음";
			}

			// 최종 소유권 여부 설정
			this.isOwner = qna.isOwner(sessionPrincipal);

			this.replies = qna.getReplies();
			if (sessionPrincipal instanceof User) {
				User sessionUser = (User) sessionPrincipal;
				replies.forEach(reply -> {
					if (reply.getUser() != null && reply.isOwner(sessionUser.getId())) {
						reply.setReplyOwner(true);
					}
				});
			}

		}
	}

}