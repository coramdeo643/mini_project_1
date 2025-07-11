package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception401;
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

@RequiredArgsConstructor
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final UserService userService;


    @GetMapping("/user/update-form")
    public String updateForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        User user = userService.findById(sessionUser.getId());
        model.addAttribute("user", user);
        return "user/update-form";
    }


    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO,
                         HttpSession session) {

        reqDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        User updateUser = userService.updateById(sessionUser.getId(), reqDTO);
        session.setAttribute("updateUser", updateUser);
        return "redirect:/user/login-form";

    }


    @GetMapping("/user/join-form")
    public String joinForm() {
        log.info("회원 가입 요청 폼");
        return "user/join-form";
    }


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        joinDTO.validate();
        userService.join(joinDTO);
        return "redirect:/user/login-form";
    }


    @GetMapping("/user/login-form")
    public String loginForm() {
        return "user/login-form";
    }


    @PostMapping("/user/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        loginDTO.validate();
        User user = userService.login(loginDTO);
        session.setAttribute(Define.SESSIONUSER_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
