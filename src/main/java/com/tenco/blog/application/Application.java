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



    /**
     * 생성시 무조건 PENDING 으로 생성될 것이고
     * 추후 업데이트 쿼리로 지정된 "PASSED"/"REJECTED"값을 버튼으로
     * 구현해 기업이 변경해주게 만들 예정
     */
    @Column(nullable = false)
    private String status = "PENDING";

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
/**
     * Transient 데이터 베이스에 생성이 안되는 필드(즉 변수)
     * 왜 사용 ? - 현재 로그인한 사용자가  여러개의 지원서 중 작성했던
     * 이력에 삭제 기능을 추가하기 위해 편의성 변수를 할당한다.
     */
    @Transient
    private boolean isApplicationOwner;

    public boolean isOwner(Long sessionId) {
        return this.user.getId().equals(sessionId);
    }


    public String getTime(){
        return MyDateUtil.timestampFormat(createdAt);
    }
}
