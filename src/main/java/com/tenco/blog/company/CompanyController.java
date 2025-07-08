package com.tenco.blog.company;

import com.tenco.blog.board.BoardController;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    // 주소 설계 : http://localhost:8080/user/update-form
    @GetMapping("/company/update-form")
    public String updateForm(Model model, HttpSession session) {
        Company companyUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
        Company Company = companyService.findById(companyUser.getId());
        model.addAttribute("company", Company);
        return "company/update-form";
    }

    /**
     * 회원 수정 기능 요청
     */
    @PostMapping("/company/update")
    public String update(CompanyRequest.UpdateDTO reqDTO,
                         HttpSession session) {
        reqDTO.validate();
        Company company = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
        Company updateCompany = companyService.updateById(company.getId(), reqDTO);
        session.setAttribute("updateCompany", updateCompany);
        return "redirect:/company/login-form";

    }


    @GetMapping("/company/join-form")
    public String joinForm() {
        log.info("회원 가입 요청 폼");
        return "company/join-form";
    }

    /**
     * 회원 가입 기능 요청
     */
    @PostMapping("/company/join")
    public String join(CompanyRequest.JoinDTO joinDTO) {
        joinDTO.validate();
        companyService.join(joinDTO);
        return "redirect:/company/login-form";
    }

    /**
     * 로그인 화면 요청
     */
    @GetMapping("/company/login-form")
    public String loginForm() {
        return "company/login-form";
    }


    /**
     * 로그인 요청
     */
    @PostMapping("/company/login")
    public String login(CompanyRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        Company company = companyService.login(loginDTO);
        session.setAttribute(Define.SESSIONUSER_COMPANY, company);
        return "redirect:/";
    }

    @GetMapping("/company/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/company/list")
    public String list(Model model) {
        List<Company> companyList = companyRepository.findAll();
        model.addAttribute("companyList", companyList);
        return "company/list"; // 머스태치: company/list.mustache
    }

}


