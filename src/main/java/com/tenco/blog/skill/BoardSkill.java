package com.tenco.blog.skill;

import com.tenco.blog.board.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "board_skill_tb")
@Entity
public class BoardSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

}
//-- 게시글과 기술스택의 다대다 관계 테이블
//CREATE TABLE board_skill_tb (
//        board_id INT,
//        skill_id INT,
//        PRIMARY KEY (board_id, skill_id),
//FOREIGN KEY (board_id) REFERENCES board_tb(id),
//FOREIGN KEY (skill_id) REFERENCES skill_tb(id)
//        );