package com.tenco.blog.rating;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import lombok.Data;

public class RatingRequest {

    @Data
    public static class SaveDTO{
    private Long companyId;
    private Integer score;


        public void validate() {
            if (companyId == null){
                throw new Exception400("회사의 정보가 없습니다.");
            }
        }

        public Rating toEntity(User user, Company company){
            return Rating.builder()
                    .user(user)
                    .company(company)
                    .score(score)
                    .build();
        }
    }

}
