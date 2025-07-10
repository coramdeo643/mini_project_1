package com.tenco.blog.reply;

import com.tenco.blog.company.Company;
import com.tenco.blog.qna.QnA;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "reply_tb")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // = auto_increment
	private Long id;


	@Column(nullable = false, length = 500) // 기본값 255
	private String comment;

	/**
	 * 댓글 작성자
	 * 한명의 사용자가 여러개의 댓글을 작성할수있다
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	/**
	 * 게시글에 댓글이 달릴수있다
	 * 하나의 게시글에는 여러개의 댓글이 달릴수있다
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qna_id")
	private QnA qna;

	@CreationTimestamp // = now() server PC 시간 기준
	private Timestamp createdAt;

	@Builder
	public Reply(Long id, String comment, User user, Company company, QnA qna, Timestamp createdAt) {
		this.id = id;
		this.comment = comment;
		this.user = user;
		this.company = company;
		this.qna = qna;
		this.createdAt = createdAt;
	}

	/**
	 * Transient DB에 생성이 안되는 필드(즉, 변수)
	 * 왜? 뷰에 현재 로그인한 사용자가 여러개의 댓글 중
	 * 내가 작성한 댓글은 삭제하는 기능을 추가하기 위해 편의성 변수를 할당
	 */
	@Transient
	private boolean isReplyOwner;

	public String getAuthorName() {
		if(user != null) {
			return user.getPersonalName();
		}
		if(company != null) {
			return company.getBusinessName();
		}
		return "알 수 없음";
	}


	public boolean isOwner(Object sessionPrincipal) {
		if (sessionPrincipal == null) {
			return false;
		}
		if(sessionPrincipal instanceof User) {
			User sessionUser = (User) sessionPrincipal;
			return this.user != null && this.user.getId().equals(sessionUser.getId());
		}
		if (sessionPrincipal instanceof Company) {
			Company sessionCompany = (Company) sessionPrincipal;
			return this.company != null && this.company.getId().equals(sessionCompany.getId());
		}
		return false;
	}

	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}
}
