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

    public enum UserType {USER, COMPANY, ADMIN}
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


    @Builder
    public User(Long id, String username, String password, UserType userType, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.userType = userType;
        this.password = password;
        this.createdAt = createdAt;
    }
}
