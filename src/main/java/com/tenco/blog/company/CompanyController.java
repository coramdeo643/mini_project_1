package com.tenco.blog.company;

import com.tenco.blog.board.BoardController;
import com.tenco.blog.user.User;
import com.tenco.blog.user.UserRequest;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final CompanyService companyService;


    // 주소 설계 : http://localhost:8080/user/update-form
    @GetMapping("/company/update-form")
    public String updateForm(Model model, HttpSession session) {
        //TODO "sessionUser" 나중에 확인
        Company companyUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
        Company company = companyService.findById(companyUser.getId());
        model.addAttribute("company", company);
        return "company/update-form";
    }

    /**
     * 회원 수정 기능 요청
     */
    @PostMapping("/company/update")
    public String update(CompanyRequset.UpdateDTO reqDTO,
                         HttpSession session, Model model) {
        // 1. 인증검사
        // 2. 우효성 검사
        // 3. 서비스 계층 -> 회원 수정 기능 위임
        // 4. 세션 동기화 처리
        // 5. 리다이렉트 - > 회원 정보 화면 요청(새로운 request)요청
        reqDTO.validate();
        Company company = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
        Company Updatecompany = companyService.updateById(company.getId(),reqDTO);
        return "redirect:/company/update-form"; // 아스키코드만 그리고 공백도 안됨

    }


    @GetMapping("company/join-form")
    public String join_form() {
        log.info("회원 가입 요청 폼");
        return "company/join-form";
    }

    /**
     *
     *회원 가입 기능 요청
     */
    @PostMapping("/company/join")
    public String join(CompanyRequset.JoinDTO joinDTO) {
        joinDTO.validate();
        companyService.join(joinDTO);
        return "redirect:/company/login-form";
    }

    /**
     *로그인 화면 요청
     */
    @GetMapping("/company/login-form")
    public String loginForm() {
        return "company/login-form";
    }


    /**
     *로그인 요청
     */
    @PostMapping("/company/login")
    public String login(CompanyRequset.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        Company company =  companyService.login(loginDTO);
        session.setAttribute(Define.SESSIONUSER_USER,company);
        return "redirect:/";
    }

}


