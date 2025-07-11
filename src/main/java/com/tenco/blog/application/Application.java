package com.tenco.blog.application;

import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "application_tb")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String status = "대기";

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder

    public Application(Long id, User user, Board board, Company company ,String status, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.board = board;
        this.status = status;
        this.company = company;
        //this.status = status != null ? status:"PENDING" ; // 삼항연사자를 사용하여 값이 없으면 자동으로 "PENDING"입력
        this.createdAt = createdAt;
    }

    @Transient
    private boolean isApplicationOwner;

    public boolean isOwner(Long sessionId) {
        return this.user.getId().equals(sessionId);
    }


    @Transient
    private boolean isAccepted;

    public boolean isAccepted() {
        return "합격".equals(this.status);
    }

    @Transient
    private boolean isRated;

    private boolean isRated() {
        return isRated;
    }

    @Transient
    private boolean isOnaji;

    private boolean isOnaji() {return isOnaji;}

    public String getTime(){
        return MyDateUtil.timestampFormat(createdAt);
    }
}
