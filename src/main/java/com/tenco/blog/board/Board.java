package com.tenco.blog.board;


import com.tenco.blog.company.Company;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "board_tb")
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false) // 수정 불가
	private String businessName;

	@Column(updatable = false)
	private String ceoName;

	@Column(updatable = false)
	private String industry;

	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@CreationTimestamp
	private Timestamp createdAt;

	@Transient
	private boolean isBoardOwner;

	public boolean isOwner(Long checkUserId) {
		return this.company.getId().equals(checkUserId);
	}

	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}


}
