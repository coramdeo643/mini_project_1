package com.tenco.blog.user;

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
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    /**
     * p,c
     * 회원 정보 수정 화면 요청
     */
    @GetMapping("/personal/update-form")
    public String updateP(Model model, HttpSession session) {
        User sessionPersonal = (User) session.getAttribute("sessionPersonal");
        User personalUser = userService.findById(sessionPersonal.getId());
        model.addAttribute("personalUser", personalUser);
        return "personal/update-form";
    }

    @GetMapping("/company/update-form")
    public String updateC(Model model, HttpSession session) {
        User sessionCompany = (User) session.getAttribute("sessionCompany");
        User companyUser = userService.findById(sessionCompany.getId());
        model.addAttribute("companyUser", companyUser);
        return "company/update-form";
    }

    /**
     * p,c
     * 회원 정보 수정 기능 요청
     */
    @PostMapping("/personal/update")
    public String updatePersonal(UserRequest.UpdatePersonalDTO reqDTO,
                                 HttpSession session) {
        reqDTO.validate();
        User sessionPersonal = (User) session.getAttribute("sessionPersonal");
        User personalUser = userService.updateById(sessionPersonal.getId(), reqDTO);
        session.setAttribute("personalUser", personalUser);
        return "redirect:/personal/update-form";
    }

    @PostMapping("/company/update")
    public String updateCompany(UserRequest.UpdateCompanyDTO reqDTO,
                                HttpSession session) {
        reqDTO.validate();
        User sessionCompany = (User) session.getAttribute("sessionCompany");
        User companyUser = userService.updateById(sessionCompany.getId(), reqDTO);
        session.setAttribute("companyUser", companyUser);
        return "redirect:/company/update-form";
    }

    /**
     * p,c
     * 회원 가입 화면 요청
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";  // user/join-form.html 또는 .jsp
    }


    /**
     * 회원 가입 기능 요청
     */
    @PostMapping("/user/join-form/personal/join")
    public String joinPersonal(UserRequest.JoinPersonalDTO dto) {
        dto.personalValidate();
        userService.joinPersonal(dto);
        return "redirect:/login-form";
    }

    @PostMapping("/user/join-form/company/join")
    public String joinCompany(UserRequest.JoinCompanyDTO dto) {
        dto.companyValidate();
        userService.joinCompany(dto);
        return "redirect:/login-form";
    }


    /**
     * 로그인 화면 요청
     */
    // 로그인 폼 진입 (단일 페이지)
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";  // 위의 HTML이 위치한 뷰 경로
    }

    /**
     * p.c.a
     * 로그인 요청
     */
    // 개인 로그인 요청 처리
    @PostMapping("/user/login-form/personal/login")
    public String personalLogin(UserRequest.LoginDTO loginDTO, HttpSession session) {
        // 로그인 로직 처리 후 리다이렉트 또는 에러 핸들링
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.SESSION_PERSONAL, user);
        return "redirect:/";
    }

    // 기업 로그인 요청 처리
    @PostMapping("/user/login-form/company/login")
    public String companyLogin(UserRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.SESSION_COMPANY, user);
        return "redirect:/";
    }

    @PostMapping("/user/login-form/admin/login")
    public String adminLogin(UserRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.SESSION_ADMIN, user);
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
