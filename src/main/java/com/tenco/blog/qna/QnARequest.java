package com.tenco.blog.qna;

import com.tenco.blog.company.Company;
import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.User;
import lombok.Data;

import java.util.List;

public class QnARequest {

	@Data
	public static class SaveDTO {
		private String title;
		private String content;


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


	@Data
	public static class UpdateDTO {
		private String title;
		private String content;

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
		private String authorName;
		private boolean isOwner;
		private List<Reply> replies;

		public DetailDTO(QnA qna, Object sessionPrincipal) {
			this.id = qna.getId();
			this.title = qna.getTitle();
			this.content = qna.getContent();

			if (qna.getUser() != null) {
				this.authorName = qna.getUser().getPersonalName();
			} else if (qna.getCompany() != null) {
				this.authorName = qna.getCompany().getBusinessName();
			} else {
				this.authorName = "알 수 없음";
			}

			this.isOwner = qna.isOwner(sessionPrincipal);
			this.replies = qna.getReplies();
			if (sessionPrincipal != null) {
				replies.forEach(reply -> {
					reply.setReplyOwner(reply.isOwner(sessionPrincipal));
				});
			}

		}
	}

}