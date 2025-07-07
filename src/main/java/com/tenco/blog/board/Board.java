package com.tenco.blog.board;


import com.tenco.blog.company.Company;
import com.tenco.blog.reply.Reply;
import com.tenco.blog.skill.BoardSkill;
import com.tenco.blog.skill.Skill;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_skill_id")
	private BoardSkill boardSkill;

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

	@OrderBy("id desc") // 정렬 옵션 설정 (내림차순)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.REMOVE)
	List<Reply> replies = new ArrayList<>(); // List 선언과 동시에 초기화


}
