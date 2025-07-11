package com.tenco.blog.ppost;


import com.tenco.blog.skill.BoardSkill;
import com.tenco.blog.skill.PPostSkill;
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
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "ppost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PPostSkill> pPostSkills = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient
    private boolean isPPostOwner;

    public boolean isOwner(Long checkUserId) {
        return this.user.getId().equals(checkUserId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }


}
