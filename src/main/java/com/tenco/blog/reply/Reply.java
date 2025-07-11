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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qna_id")
	private QnA qna;

	@CreationTimestamp
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
