package com.tenco.blog.rating;

import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "rating_tb")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 평점을 남길 유저 (// 익명 처리될 것임)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 평가 받는 회사
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // 평점 점수 (1 ~ 5 점) // Integer을 사용할 수 밖에 없는 이유
    // 만약 int를 사용하게 되면 값이 없으면 0이지만   // null 못 받음 → 폼에서 누락되면 에러
    // Integer에 값이 없을 경유 null로 발생할것이다  // null도 받을 수 있음 → 서비스 단에서 체크 가능
    @Column(nullable = false)
    private Integer score;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Rating(Long id, User user, Company company, Integer score, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.score = score;
        this.createdAt = createdAt;
    }
}
