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
    private String companyNumber;
    private String industry;
    private String businessName;
    private String ceoName;
    private String companyAddress;

    //now()
    // 엔티티가 영속화될때 자동으로 pc 현재시간을 설정해 준다.
    @CreationTimestamp
    private Timestamp createdAt;

    //객체 생성시 가독성과 안정성 향상
    @Builder
    public Company(Long id, String username, String password, String telephone,
                   String email, Timestamp createdAt, String companyNumber,
                   String industry, String businessName, String ceoName,
                   String companyAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.createdAt = createdAt;
        this.companyNumber = companyNumber;
        this.industry = industry;
        this.businessName = businessName;
        this.ceoName = ceoName;
        this.companyAddress = companyAddress;
    }

    public void update(CompanyRequest.UpdateDTO updateDTO) {
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().trim().isEmpty()) {
            this.password = updateDTO.getPassword();
        }
        if (updateDTO.getEmail() != null && updateDTO.getEmail().contains("@")) {
            this.email = updateDTO.getEmail();
        }
        if (updateDTO.getTelephone() != null && !updateDTO.getTelephone().trim().isEmpty()) {
            this.telephone = updateDTO.getTelephone();
        }
        if (updateDTO.getCompanyAddress() != null && !updateDTO.getCompanyAddress().trim().isEmpty()) {
            this.companyAddress = updateDTO.getCompanyAddress();
        }
    }

}

