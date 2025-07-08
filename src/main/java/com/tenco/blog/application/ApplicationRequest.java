package com.tenco.blog.application;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import lombok.Data;


public class ApplicationRequest {

    @Data
    public static class SaveDTO {
        private Long boardId; // 지원서에 달릴 공고 ID
        private Long companyId;
        private String status = "대기";
        private boolean isAccepted = false;
        private boolean isRated = false;

        /**
         * 데이터 유효성 검증
         */
        public void validate() {
            if (boardId == null) {
                throw new Exception400("공고  정보가 필요합니다");
            }
        }


        public Application toEntity(User sessionUser, Board board , Company company) {
            return Application.builder()
                    .status(getStatus())
                    .user(sessionUser)
                    .board(board)
                    .company(company)
                    .build();
        }

    }
    @Data
    public static class ListDTO {
        private Long applicationId;
        private String name;
        private String email;
        private String status;
        private Long boardId;

        public void validate() {
            if (boardId == null) {
                throw new Exception400("공고  정보가 필요합니다");
            }
        }

        public ListDTO(Long applicationId, String name, String email, String status, Long boardId) {
            this.applicationId = applicationId;
            this.name = name;
            this.email = email;
            this.status = status;
            this.boardId = boardId;
        }
    }


}
