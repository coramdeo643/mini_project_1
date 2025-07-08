package com.tenco.blog.rating;

import com.tenco.blog.application.Application;
import com.tenco.blog.application.ApplicationRequest;
import com.tenco.blog.application.ApplicationService;
import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RatingController {

    private final RatingService ratingService;
    private final ApplicationService applicationService;

    @PostMapping("/rating")
    public String save(RatingRequest.SaveDTO saveDTO, HttpSession session) {
        // 인증 검사 (인터셉터에서 처리)
        //유효성 검사

        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        ratingService.save(saveDTO, sessionUser);
        return "redirect:/board/application-resource-list";
    }

    @PostMapping("/delete")
    public String delete(RatingRequest.SaveDTO saveDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        ratingService.delete(sessionUser.getId(), saveDTO.getCompanyId());
        return "redirect:/board/application-resource-list";
    }

}
