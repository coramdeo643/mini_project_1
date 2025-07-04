package com.tenco.blog.companySub;

import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import lombok.Data;

public class CompanySubRequest {

    // 게시글 저장 DTO
    @Data
    public static class SaveDTO {
        private User user;
        private Company company;
        private Long companyId;
        private Long boardId;
        public CompanySub toEntity(Company sessionC) {
            return CompanySub.builder()
                    .company(sessionC)
                    .user(User.builder().id(this.user.getId()).build())
                    .build();
        }
    }
}
