package com.tenco.blog.application;

import com.tenco.blog.UserSub.UserSub;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardService;
import com.tenco.blog.company.Company;
import com.tenco.blog.company.CompanyService;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final BoardService boardService;
    private final CompanyService companyService;


    @PostMapping("/application/{id}/save")
    public String save(ApplicationRequest.SaveDTO saveDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        applicationService.save(saveDTO, sessionUser);
        return "redirect:/board/" + saveDTO.getBoardId();
    }


    @GetMapping("/board/application-list")
    public String list(Model model, HttpSession session, Company companyId) {
        Company sessionCompany = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);

        List<Application> applicationList = applicationService.findAllByBoardIdWithUser(sessionCompany.getId());
        model.addAttribute("applicationList", applicationList);
        return "board/application-list";
    }


    @GetMapping("/board/application-resource-list")
    public String resourceList(Model model, HttpSession session, User userId) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);

        List<Application> applicationResourceList = applicationService.findAllByUserWithRatingStatus(sessionUser.getId());
        model.addAttribute("applicationResourceList", applicationResourceList);
        return "board/application-resource-list";
    }


    @PostMapping("/application/{id}/accept")
    public String accept(@PathVariable Long id) {
        applicationService.updateStatus(id, "합격");
        return "redirect:/board/application-list";
    }

    @PostMapping("/application/{id}/reject")
    public String reject(@PathVariable Long id) {
        applicationService.updateStatus(id, "불합격");
        return "redirect:/board/application-list";
    }

    @PostMapping("/application/{id}/delete")
    public String delete(@PathVariable(name = "id") Long applicationId,
                         HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        // public void deleteById(Long applicationId, Long boardId, User sessionUser) {
        applicationService.deleteByListId(applicationId, sessionUser);
        return "redirect:/board/application-resource-list";
    }

    @PostMapping("/application/delete")
    public String boardDelete(
            @RequestParam(name = "boardId") Long boardId,
            @RequestParam(name = "applicationId") Long applicationId, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        applicationService.deleteById(applicationId, boardId, sessionUser);
        return "redirect:/board/" + boardId;
    }


}
