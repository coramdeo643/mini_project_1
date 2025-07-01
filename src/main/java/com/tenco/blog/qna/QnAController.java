package com.tenco.blog.qna;

import com.tenco.blog.qna.QnARequest;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QnAController {

    private static final Logger log = LoggerFactory.getLogger(QnAController.class);
    private final QnAService qnaService;

    /**
     * 게시글 수정 화면 요청
     */
    @GetMapping("/qna/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long boardId,
                             HttpServletRequest request, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        qnaService.checkBoardOwner(boardId, sessionUser.getId());
        request.setAttribute("qna", qnaService.findById(boardId));
        return "qna/update-form";
    }


    @PostMapping("/qna/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         QnARequest.UpdateDTO reqDTO,
                         HttpSession session) {
        // 1. 인증 검사
        // 2. 데이터 유효성 검사
        // 3. 수정 요청 위임
        // 4. 리다이렉트 처리
        reqDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        qnaService.updateById(boardId, reqDTO, sessionUser);

        return "redirect:/qna/" + boardId;
    }

    @PostMapping("/qna/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
        // 1. 인증 검사
        // 2. 세션에서 로그인 한 사용자 정보 추출
        // 3. 서비스 위임
        // 4. 메인 페이지로 리다이렉트 처리
        User sessionUser = (User) session.getAttribute("sessionUser");
        qnaService.deleteById(id, sessionUser);
        return "redirect:/";
    }

    @GetMapping("/qna/save-form")
    public String saveForm() {
        return "qna/save-form";
    }

    @PostMapping("/qna/save")
    public String save(QnARequest.SaveDTO reqDTO, HttpSession session) {
        // 1. 인증검사
        // 2. 유효성 검사
        // 3. 서비스 계층 위임
        reqDTO.validate();
        User sessionUser = (User)session.getAttribute("sessionUser");
        qnaService.save(reqDTO, sessionUser);
        return "redirect:/";
    }

    @GetMapping("/qna/list")
    public String list(Model model) {
        List<QnA> qnaList =  qnaService.findAll();
        model.addAttribute("qnaList", qnaList);
        return "qna/list";
    }

    @GetMapping("/qna/{id}")
    public String detail(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("qna", qnaService.findById(id));
        return "/qna/detail";
    }
}
