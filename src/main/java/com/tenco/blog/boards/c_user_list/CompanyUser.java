package com.tenco.blog.boards.c_user_list;

import com.tenco.blog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "company_profile_tb")
@Entity
public class CompanyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    // 게시글 소유자를 직접 확인하는 기능을 만들자
    public boolean isOwner(Long checkUserId) {
        return this.user.getId().equals(checkUserId);
    }
}

