package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.company.Company;
import com.tenco.blog.qna.QnA;
import com.tenco.blog.user.User;
import lombok.Data;

public class ReplyRequest {

	@Data
	public static class SaveDTO {
		private Long qnaId;
		private String comment;

		public void validate() {
			if (comment == null || comment.trim().isEmpty()) {
				throw new RuntimeException("Please insert comment");
			}
			if (comment.length() > 500) {
				throw new Exception400("Please insert comment within 500 letters");
			}
			if (qnaId == null) {
				throw new Exception400("Please insert qnaId");
			}
		}


		public Reply toEntity(Object sessionPrincipal, QnA qna) {
			User user = (sessionPrincipal instanceof User) ? (User) sessionPrincipal : null;
			Company company = (sessionPrincipal instanceof Company) ? (Company) sessionPrincipal : null;
			return Reply.builder()
					.comment(comment.trim())
					.user(user)
					.company(company)
					.qna(qna)
					.build();
		}

	}

}
