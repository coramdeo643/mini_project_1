package com.tenco.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    public enum UserType {PERSONAL, COMPANY, ADMIN}

    @Enumerated(EnumType.STRING)
    private UserType userType;


    @CreationTimestamp
    private Timestamp createdAt;

    // personal
    private String personalName;
    private String personalPhone;
    private String personalEmail;

    //company
    private String companyEmail;
    private String companyPhone;
    private String companyBusinessNo;
    private String companyIndustry;
    private String companyName;
    private String companyCeoName;
    private String companyAddress;


    @Builder(builderMethodName = "personalBuilder")
    public User(Long id, String username, String password, Timestamp createdAt,
                String personalName, String personalPhone, String personalEmail) {
        this.id = id;
        this.username = username;
        this.userType = UserType.PERSONAL;
        this.password = password;
        this.createdAt = createdAt;
        this.personalName = personalName;
        this.personalPhone = personalPhone;
        this.personalEmail = personalEmail;
    }

    @Builder(builderMethodName = "companyBuilder")
    public User(Long id, String username, String password, Timestamp createdAt,
                String companyEmail, String companyPhone, String companyBusinessNo,
                String companyIndustry, String companyName, String companyCeoName, String companyAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.companyEmail = companyEmail;
        this.companyPhone = companyPhone;
        this.companyBusinessNo = companyBusinessNo;
        this.companyIndustry = companyIndustry;
        this.companyName = companyName;
        this.companyCeoName = companyCeoName;
        this.companyAddress = companyAddress;
        this.userType = UserType.COMPANY;
    }

    @Builder(builderMethodName = "adminBuilder")
    public User(Long id, String username, String password, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.userType = UserType.ADMIN;
    }

    public void updatePersonal(UserRequest.UpdatePersonalDTO updatePersonalDTO) {
        if (updatePersonalDTO.getPassword() != null && !updatePersonalDTO.getPassword().trim().isEmpty()) {
            this.password = updatePersonalDTO.getPassword();
        }
        if (updatePersonalDTO.getPersonalPhone() != null && !updatePersonalDTO.getPersonalPhone().trim().isEmpty()) {
            this.personalPhone = updatePersonalDTO.getPersonalPhone();
        }
        if (updatePersonalDTO.getPersonalEmail() != null && updatePersonalDTO.getPersonalEmail().contains("@")) {
            this.personalEmail = updatePersonalDTO.getPersonalEmail();
        }

    }

    public void updateCompany(UserRequest.UpdateCompanyDTO updateCompanyDTO) {
        if (updateCompanyDTO.getPassword() != null && !updateCompanyDTO.getPassword().trim().isEmpty()) {
            this.password = updateCompanyDTO.getPassword();
        }
        if (updateCompanyDTO.getCompanyPhone() != null && !updateCompanyDTO.getCompanyPhone().trim().isEmpty()) {
            this.companyPhone = updateCompanyDTO.getCompanyPhone();
        }
        if (updateCompanyDTO.getCompanyName() != null && !updateCompanyDTO.getCompanyName().trim().isEmpty()) {
            this.companyName = updateCompanyDTO.getCompanyName();
        }
        if (updateCompanyDTO.getCompanyCeoName() != null && !updateCompanyDTO.getCompanyCeoName().trim().isEmpty()) {
            this.companyCeoName = updateCompanyDTO.getCompanyCeoName();
        }
        if (updateCompanyDTO.getCompanyEmail() != null && updateCompanyDTO.getCompanyEmail().contains("@")) {
            this.companyEmail = updateCompanyDTO.getCompanyEmail();
        }
        if (updateCompanyDTO.getCompanyAddress() != null && !updateCompanyDTO.getCompanyAddress().trim().isEmpty()) {
            this.companyAddress = updateCompanyDTO.getCompanyAddress();
        }
    }

}
