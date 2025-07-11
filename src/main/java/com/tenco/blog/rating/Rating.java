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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;


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
