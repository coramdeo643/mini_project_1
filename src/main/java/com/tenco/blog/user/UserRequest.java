package com.tenco.blog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    // 회원가입용

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinPersonalDTO { // 개인 사용자
        private String username;
        private String password;
        private String personalName;
        private String personalPhone;
        private String personalEmail;

        public User toEntity() {
            return User.personalBuilder()
                    .username(this.username)
                    .password(this.password)
                    .personalName(this.personalName)
                    .personalPhone(this.personalPhone)
                    .personalEmail(this.personalEmail)
                    .build();
        }

        public void personalValidate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명은 필수입니다");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            if (personalName == null || personalName.trim().isEmpty()) {
                throw new IllegalArgumentException("이름은 필수입니다");
            }
            if (personalPhone == null || personalPhone.trim().isEmpty()) {
                throw new IllegalArgumentException("전화번호는 필수입니다");
            }
            // 간단한 이메일 형식 검증 (정규화 표현식)
            if (personalEmail == null || !personalEmail.contains("@")) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinCompanyDTO { // 기업 사용자
        private String username;
        private String password;
        private String companyName;
        private String companyEmail;
        private String companyPhone;
        private String companyBusinessNo;
        private String companyCeoName;
        private String companyIndustry;
        private String companyAddress;

        public User toEntity() {
            return User.companyBuilder()
                    .username(this.username)
                    .password(this.password)
                    .companyName(this.companyName)
                    .companyEmail(this.companyEmail)
                    .companyPhone(this.companyPhone)
                    .companyBusinessNo(this.companyBusinessNo)
                    .companyCeoName(this.companyCeoName)
                    .companyIndustry(this.companyIndustry)
                    .companyAddress(this.companyAddress)
                    .build();
        }

        public void companyValidate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명은 필수입니다");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            if (companyName == null || companyName.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 이름은 필수입니다");
            }
            if (companyEmail == null || !companyEmail.contains("@")) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
            }
            if (companyPhone == null || companyPhone.trim().isEmpty()) {
                throw new IllegalArgumentException("전화번호는 필수입니다");
            }
            if (companyBusinessNo == null || companyBusinessNo.trim().isEmpty()) {
                throw new IllegalArgumentException("사업자번호는 필수입니다");
            }
            if (companyCeoName == null || companyCeoName.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 대표 이름은 필수입니다");
            }
            // 아래 두 줄을 이렇게 수정
            if (companyIndustry == null || companyIndustry.trim().isEmpty()) {
                throw new IllegalArgumentException("업종은 필수입니다");
            }
            if (companyAddress == null || companyAddress.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 주소는 필수입니다");
            }

        }
    }


    // 로그인 용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String username;
        private String password;

        // 유효성 검사
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명을 입력해주세요");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호를 입력해주세요");
            }
        }
    }

    // 회원 정보 수정용 DTO
    @Data
    public static class UpdatePersonalDTO {
        private String password;
        private String personalPhone;
        private String personalEmail;

        public void validate() {
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            if (personalPhone == null || personalPhone.trim().isEmpty()) {
                throw new IllegalArgumentException("전화번호는 필수입니다");
            }
            if (personalEmail == null || !personalEmail.contains("@")) {
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다");
            }
        }
    }

    @Data
    public static class UpdateCompanyDTO {
        private String password;
        private String companyPhone;
        private String companyName;
        private String companyCeoName;
        private String companyEmail;
        private String companyAddress;

        public void validate() {
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            if (companyPhone == null || companyPhone.trim().isEmpty()) {
                throw new IllegalArgumentException("전화번호는 필수입니다");
            }
            if (companyName == null || companyName.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 이름은 필수입니다");
            }
            if (companyCeoName == null || companyCeoName.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 대표 이름은 필수입니다");
            }
            if (companyEmail == null || !companyEmail.contains("@")) {
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다");
            }
            if (companyAddress == null || companyAddress.trim().isEmpty()) {
                throw new IllegalArgumentException("기업 주소는 필수입니다");
            }
        }
    }


}
