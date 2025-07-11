package com.tenco.blog.board;

import com.tenco.blog.company.Company;
import lombok.Data;

/**
 * 클라이언트에게 넘어온 데이터를
 * Object로 변화해서 전달하는 DTO 역할을 담당한다
 */
public class BoardRequest {

    // 게시글 저장 DTO
    @Data
    public static class SaveDTO {
        private String businessName;
        private String ceoName;
        private String industry;
        private String title;
        private String content;


        public Board toEntity(Company company) {
            return Board.builder()
                    .businessName(company.getBusinessName())
                    .ceoName(company.getCeoName())
                    .industry(company.getIndustry())
                    .title(this.title)
                    .company(company)
                    .content(this.content)
                    .build();
        }

        public void validate() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다.");
            }
            if(title.length() > 20){
                throw new IllegalArgumentException("제목은 20자 이내로 작성해주세요.");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
            if(content.length() > 1000){
                throw new IllegalArgumentException("제목은 1000자 이내로 작성해주세요.");
            }
        }
    }


    @Data
    public static class UpdateDTO {
        private String title;
        private String content;


        public void validate() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다.");
            }
            if(title.length() > 20){
                throw new IllegalArgumentException("제목은 20자 이내로 작성해주세요.");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
            if(content.length() > 1000){
                throw new IllegalArgumentException("제목은 1000자 이내로 작성해주세요.");
            }
        }
    }


}
