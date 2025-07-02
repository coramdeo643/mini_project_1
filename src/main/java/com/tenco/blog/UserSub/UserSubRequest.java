package com.tenco.blog.UserSub;

import com.tenco.blog.company.Company;
import com.tenco.blog.ppost.PPost;
import com.tenco.blog.user.User;
import lombok.Data;

public class UserSubRequest {

    // 게시글 저장 DTO
    @Data
    public static class SaveDTO {
        private User user;
        private Company company;
        public UserSub toEntity(User user, Company company) {
            return UserSub.builder()
                    .user(user)
                    .company(company)
                    .build();
        }
    }
}
