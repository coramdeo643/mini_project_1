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
// 기본 생성자 - JPA에서 엔티티는 기본 생성자가 필요
@Data
@Table(name = "qna_tb")
@Entity
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY) // user라는 정보를 호출할때만 사용됨
	@JoinColumn(name = "user_id") // 외래키 컬럼 명시
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@CreationTimestamp
	private Timestamp createdAt; // created_at (스네이크 케이스로 자동 변환)

	@Transient
	private boolean isQnAOwner;

	public boolean isOwner(Object sessionPrincipal) {
		if (sessionPrincipal == null) {
			return false; // 로그인하지 않았으면 무조건 false
		}

		if (sessionPrincipal instanceof User) {
			User sessionUser = (User) sessionPrincipal;
			// 이 글의 작성자가 User이고, 그 ID가 세션 User의 ID와 일치하는지 확인
			return this.user != null && this.user.getId().equals(sessionUser.getId());
		}

		if (sessionPrincipal instanceof Company) {
			Company sessionCompany = (Company) sessionPrincipal;
			// 이 글의 작성자가 Company이고, 그 ID가 세션 Company의 ID와 일치하는지 확인
			return this.company != null && this.company.getId().equals(sessionCompany.getId());
		}

		return false;
	}

	// 머스태치에서 표현할 시간을 포맷기능을(행위) 스스로 만들자
	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}

	@OrderBy("id desc") // 정렬 옵션 설정 (내림차순)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "qna", cascade = CascadeType.REMOVE)
	List<Reply> replies = new ArrayList<>(); // List 선언과 동시에 초기화
}