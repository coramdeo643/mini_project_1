package com.tenco.blog.application;

import com.tenco.blog.UserSub.UserSub;
import com.tenco.blog._core.errors.exception.Exception401;
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

    // 지원 저장 기능 요청
    @PostMapping("/application/{id}/save")
    public String save(ApplicationRequest.SaveDTO saveDTO, HttpSession session) {
        // 인증 검사 (인터셉터에서 처리)
        //유효성 검사

        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        applicationService.save(saveDTO,sessionUser);
        return "redirect:/board/"+saveDTO.getBoardId();
    }

    // 지원 취소 기능 요청


//    // 지원리스트 목록 화면
//    @GetMapping("/company/{id}/applicationlist")
//    public String index(@PathVariable(name = "id") Long id,
//                        Model model,
//                        HttpSession session) {
//        Board board = boardService.findById(id);
//        model.addAttribute("board",board);
//        Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
//
//        List<Application> applications = applicationService.findAllByBoardIdWithUser(id);
//        model.addAttribute("companyUser", true);
//        model.addAttribute("applications", applications);
//        return "/company/applicationlist" + id;
//    }

    // 회사 입장에서 확인하는 지원자리스트
    @GetMapping("/board/application-list")
    public String list(Model model, HttpSession session, Company companyId) {
        Company sessionCompany = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);

        List<Application> applicationList = applicationService.findAllByBoardIdWithUser(sessionCompany.getId());
        model.addAttribute("applicationList",applicationList);
        return "board/application-list";
    }

    // 유저 입장에서 확인하는 지원리스트
    @GetMapping("/board/application-resource-list")
    public String resourceList(Model model, HttpSession session, User userId) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);

        List<Application> applicationResourceList = applicationService.findAllByBoardIdWithBoard(sessionUser.getId());
        model.addAttribute("applicationResourceList",applicationResourceList);
        return "board/application-resource-list";
    }


    // (회사)합격 여부의 합
    @PostMapping("/application/{id}/accept")
    public String accept(@PathVariable Long id) {
        applicationService.updateStatus(id, "PASSED");
        return "redirect:/board/application-list";
    }
    // (회사)합격 여부의 불
    @PostMapping("/application/{id}/reject")
    public String reject(@PathVariable Long id) {
        applicationService.updateStatus(id, "REJECTED");
        return "redirect:/board/application-list";
    }
    // 댓글 삭제 기능 요청
    @PostMapping("/application/{id}/delete")
    public String delete(@PathVariable(name = "id") Long applicationId,

                         HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        applicationService.deleteById(applicationId, sessionUser);

        return "redirect:/board/application-resource-list";
    }


}
