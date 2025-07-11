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

    // 사용자 이름 중복 방지를 위한 유니크 제약 설정
    @Column(unique = true)
    private String username;
    private String password;
    private String personalName;
    private String telephone;
    private String email;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public User(Long id, String username, String password, String personalName, String telephone, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.personalName = personalName;
        this.email = email;
        this.telephone = telephone;
        this.createdAt = createdAt;
    }

    public void update(UserRequest.UpdateDTO updateDTO) {
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().trim().isEmpty()) {
            this.password = updateDTO.getPassword();
        }
        if (updateDTO.getTelephone() != null && !updateDTO.getTelephone().trim().isEmpty()) {
            this.telephone = updateDTO.getTelephone();
        }
        if (updateDTO.getEmail() != null && updateDTO.getEmail().contains("@")) {
            this.email = updateDTO.getEmail();
        }
    }

}
