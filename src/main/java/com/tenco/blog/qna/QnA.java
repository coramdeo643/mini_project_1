package com.tenco.blog.qna;


import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
// 기본 생성자 - JPA에서 엔티티는 기본 생성자가 필요
@Data
@Table(name = "qna_tb")
@Entity
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // user라는 정보를 호출할때만 사용됨
    @JoinColumn(name = "user_id") // 외래키 컬럼 명시
    private User user;

    @CreationTimestamp
    private Timestamp createdAt; // created_at (스네이크 케이스로 자동 변환)

    // 게시글에 소유자를 직접 확인하는 기능을 만들자
    public boolean isOwner(Long checkUserId) {
        return this.user.getId().equals(checkUserId);
    }


    // 머스태치에서 표현할 시간을 포맷기능을(행위) 스스로 만들자
    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }


}
