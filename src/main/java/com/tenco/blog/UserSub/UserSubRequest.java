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
        private Long companyId;
        private Long boardId;
        public UserSub toEntity(User sessionUser) {
            return UserSub.builder()
                    .user(sessionUser)
                    .company(Company.builder().id(this.companyId).build())
                    .build();
        }
    }
}
