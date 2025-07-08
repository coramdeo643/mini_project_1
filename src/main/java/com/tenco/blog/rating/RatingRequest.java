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
            if (companyId == null){    // (companyId == null || companyId <= 0)가 더 안정적이지만 배우지 않음
                throw new Exception400("회사의 정보가 없습니다.");
            }
            // 다른 방어 코드가 존재하지만 아직 배우지 않은 보안적 기능
            // 또한 평점을 클릭으로 할 것이기에 1에서 5를 벗어나지 않기에 다른 방어코드가 필요없다
            // 또한 값을 넣지 않아도 오류가 뜨지 않아야 하는 이유는 유저입장에서 불리한 기능이기 때문이다.
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
