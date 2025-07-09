package com.tenco.blog.ppost;

import com.tenco.blog.UserSub.UserSub;
import com.tenco.blog.board.Board;
import com.tenco.blog.company.Company;
import com.tenco.blog.companySub.CompanySub;
import com.tenco.blog.companySub.CompanySubService;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PPostController {

    private static final Logger log = LoggerFactory.getLogger(PPostController.class);
    private final PPostService PPostService;
    private final CompanySubService companySubService;
    private final PPostJpaRepository PPostJpaRepository;

    /**
     * 게시글 수정 화면 요청
     */
    @GetMapping("/ppost/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long boardId,
                             HttpServletRequest request, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        PPostService.checkBoardOwner(boardId, sessionUser.getId());
        request.setAttribute("ppost", PPostService.findById(boardId));
        return "ppost/update-form";
    }


    @PostMapping("/ppost/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         PPostRequest.UpdateDTO reqDTO,
                         HttpSession session) {
        // 1. 인증 검사
        // 2. 데이터 유효성 검사
        // 3. 수정 요청 위임
        // 4. 리다이렉트 처리
        reqDTO.validate();
        User sessionUser = (User) session.getAttribute("sessionUser");
        PPostService.updateById(boardId, reqDTO, sessionUser);

        return "redirect:/ppost/" + boardId;
    }

    @PostMapping("/ppost/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
        // 1. 인증 검사
        // 2. 세션에서 로그인 한 사용자 정보 추출
        // 3. 서비스 위임
        // 4. 메인 페이지로 리다이렉트 처리
        User sessionUser = (User) session.getAttribute("sessionUser");
        PPostService.deleteById(id, sessionUser);
        return "redirect:/ppost/my-list";
    }

    @GetMapping("/ppost/save-form")
    public String saveForm() {
        return "ppost/save-form";
    }

    @PostMapping("/ppost/save")
    public String save(PPostRequest.SaveDTO reqDTO, HttpSession session) {
        // 1. 인증검사
        // 2. 유효성 검사
        // 3. 서비스 계층 위임
        reqDTO.validate();
        User sessionUser = (User)session.getAttribute("sessionUser");
        PPostService.save(reqDTO, sessionUser);
        return "redirect:/ppost/my-list";
    }

    @GetMapping("/ppost/list")
    public String index(Model model) {
        List<PPost> PPostList =  PPostService.findAll();
        model.addAttribute("boardList", PPostList);
        return "ppost/list";
    }

    @GetMapping("/ppost/{id}")
    public String detail(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("ppost", PPostService.findById(id));
        return "ppost/detail";
    }

    @GetMapping("/ppost/my-list")
    public String myBoardList(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Define.SESSIONUSER_USER);
        if (user == null) {
            return "redirect:/user/login-form";
        }
        Long userId = user.getId();
        List<PPost> userPPostList = PPostJpaRepository.findByUserId(userId);
        model.addAttribute("userPPostList", userPPostList);
        return "ppost/my-list";
    }

    @GetMapping("/user-sub/ppost-list")
    public String companySubPostList(HttpSession session, Model model) {
        Company sCompany = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
        if (sCompany == null) {
            return "redirect:/login-form";
        }
        List<PPost> companySubPostList = PPostService.findPostsBySubscribedUserId(sCompany.getId());
        model.addAttribute("companySubPostList", companySubPostList);
        List<CompanySub> companySubList = companySubService.findAllByUserAndCompanyId(sCompany.getId());
        model.addAttribute("companySubList", companySubList);
        return "user-sub/ppost-list";
    }

    @GetMapping("ppost/filter")
    public String filterBySkill(Pageable pageable, @RequestParam("skill")String skillName, Model model) {
        Page<PPost> ppostPage = PPostJpaRepository.findBySkillName(pageable, skillName);
        model.addAttribute("ppostPage", ppostPage);
        model.addAttribute("isCompanyUser", true);
        return "index";
    }
}
