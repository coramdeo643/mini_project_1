package com.tenco.blog.company;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "company_tb")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이름 중복 방지를 위한 유니크 제약 설정
    @Column(unique = true)
    private String username;
    private String password;
    private String telephone;
    private String email;
    private String company_number;
    private String industry;
    private String business_name;
    private String ceo_name;
    private String company_address;

    //now()
    // 엔티티가 영속화될때 자동으로 pc 현재시간을 설정해 준다.
    @CreationTimestamp
    private Timestamp createdAt;

    //객체 생성시 가독성과 안정성 향상
    @Builder
    public Company(Long id, String username, String password, String telephone,
                   String email, Timestamp createdAt, String company_number,
                   String industry, String business_name, String ceo_name,
                   String company_address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone= telephone;
        this.createdAt = createdAt;
        this.company_number = company_number;
        this.industry = industry;
        this.business_name = business_name;
        this.ceo_name = ceo_name;
        this.company_address = company_address;
    }

    }

