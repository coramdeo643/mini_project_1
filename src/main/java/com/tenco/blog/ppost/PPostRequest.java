package com.tenco.blog.ppost;

import com.tenco.blog.user.User;
import lombok.Data;

public class PPostRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        public PPost toEntity(User user) {
            return PPost.builder()
                    .title(this.title)
                    .user(user)
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
