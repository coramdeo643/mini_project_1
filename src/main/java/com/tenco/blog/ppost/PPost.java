package com.tenco.blog.ppost;


import com.tenco.blog.skill.Skill;
import com.tenco.blog.user.User;
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
// 기본 생성자 - JPA에서 엔티티는 기본 생성자가 필요
@Data
@Table(name = "ppost_tb")
@Entity
public class PPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient
    private boolean isPPostOwner;

    // 게시글에 소유자를 직접 확인하는 기능을 만들자
    public boolean isOwner(Long checkUserId){
        return this.user.getId().equals(checkUserId);
    }


    // 머스태치에서 표현할 시간을 포맷기능을(행위) 스스로 만들자
    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }


}
