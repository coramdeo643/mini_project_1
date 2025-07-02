package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog.board.BoardController;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
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
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final UserService userService;

    // 주소 설계 : http://localhost:8080/user/update-form
    @GetMapping("/user/update-form")
    public String updateForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findById(sessionUser.getId());
        model.addAttribute("user", user);
        return "user/update-form";
    }

    /**
     * 회원 수정 기능 요청
     */
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO,
                         HttpSession session) {

        reqDTO.validate();
        User user = (User)session.getAttribute("sessionUser");
        User updateUser = userService.updateById(user.getId(),reqDTO);
        return "redirect:/user/update-form";

    }


    @GetMapping("/user/join-form")
    public String joinForm() {
        log.info("회원 가입 요청 폼");
        return "user/join-form";
    }

    /**
     *
     *회원 가입 기능 요청
     */
    @PostMapping("/user/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        joinDTO.validate();
        userService.join(joinDTO);
        return "redirect:/user/login-form";
    }

    /**
     *로그인 화면 요청
     */
    @GetMapping("/user/login-form")
    public String loginForm() {
        return "user/login-form";
    }


    /**
     *로그인 요청
     */
    @PostMapping("/user/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        User user =  userService.login(loginDTO);
        session.setAttribute(Define.SESSIONUSER_USER,user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
       session.invalidate();
        return "redirect:/";
    }


}
