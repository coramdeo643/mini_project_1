package com.tenco.blog.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CompanyRequset {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
        private String telephone;
        private String email;
        private String company_number;
        private String industry;
        private String business_name;
        private String ceo_name;
        private String company_address;

        // JoinDTO 를 User Object 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public Company toEntity() {
            return Company.builder()
                    .username(this.username)
                    .password(this.password)
                    .telephone(this.telephone)
                    .email(this.email)
                    .company_number(this.company_number)
                    .industry(this.industry)
                    .business_name(this.business_name)
                    .ceo_name(this.ceo_name)
                    .company_address(this.company_address)
                    .build();
        }

        //회원가입시 유효성 검증 메서드
        public void validate() {

            if(username == null || username.trim().isEmpty()){
                throw new IllegalArgumentException("사용자 명은 필수입니다.");
            }
            if(password == null || password.trim().isEmpty()){
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
            if(telephone == null || telephone.trim().isEmpty()){
                throw new IllegalArgumentException("전화번호를 입력해주세요");
            }
            // 간단한 이메일 형식 검증 (정규화 표현식)
            if(email.contains("@") == false) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
            }
            if(company_number == null || company_number.trim().isEmpty()){
                throw new IllegalArgumentException("사업자 번호를 입력해주세요");
            }
            if(industry == null || industry.trim().isEmpty()){
                throw new IllegalArgumentException("업종을 입력해주세요");
            }
            if(business_name == null || business_name.trim().isEmpty()){
                throw new IllegalArgumentException("회사명을 입력해주세요");
            }
            if(ceo_name == null || ceo_name.trim().isEmpty()){
                throw new IllegalArgumentException("대표이름을 입력해주세요");
            }
            if(company_address == null || company_address.trim().isEmpty()){
                throw new IllegalArgumentException("회사 주소를 입력해주세요");
            }

        }
    }

    //로그인 용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO{
        private String username;
        private String password;


        // 유효성 검사
        public void validate() {
            if (username == null || username.trim().isEmpty()){
                throw new IllegalArgumentException("야 사용자 입력해");
            }
            if (password == null || password.trim().isEmpty()){
                throw new IllegalArgumentException("야 비번 입력해");
            }
        }

    }

    // 회원 정보 수정용 DTO
    @Data
    public static class UpdateDTO{
        private String password;
        private String email;
        // username <- 유니크 설정 함

        // toEntity (더티체킹 사용)
        public void validate(){
            if(password == null || password.trim().isEmpty()){
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
            if(password.length() < 4){
                throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다.");
            }
            // 간단한 이메일 형식 검증 (정규화 표현식)
            if(email.contains("@") == false) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
            }
        }
    }


}
