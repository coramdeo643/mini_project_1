package com.tenco.blog.qna;


import com.tenco.blog.company.Company;
import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "qna_tb")
@Entity
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@CreationTimestamp
	private Timestamp createdAt;

	@Transient
	private boolean isQnAOwner;

	public boolean isOwner(Object sessionPrincipal) {
		if (sessionPrincipal == null) {
			return false;
		}

		if (sessionPrincipal instanceof User) {
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

	@OrderBy("id desc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "qna", cascade = CascadeType.REMOVE)
	List<Reply> replies = new ArrayList<>();
}