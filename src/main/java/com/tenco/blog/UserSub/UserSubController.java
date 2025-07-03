package com.tenco.blog.UserSub;

import com.tenco.blog.company.Company;
import com.tenco.blog.ppost.PPost;
import com.tenco.blog.ppost.PPostRequest;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserSubController {
	private static final Logger log = LoggerFactory.getLogger(UserSubController.class);
	private final UserSubService userSubService;

	// 삭제 기능
	@PostMapping("/user-sub/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
		if (sessionUser == null) {
			return "redirect:/login-form";
		}
		userSubService.deleteById(id, sessionUser);
		return "redirect:/user-sub/" + sessionUser.getId() + "/list";
	}

//	// 구독화면?
//	@GetMapping("/user-sub/save-form")
//	public String saveForm() {
//		return "user-sub/save-form";
//	}

	// 구독하기 기능
	@PostMapping("/user-sub/save")
	public String save(UserSubRequest.SaveDTO reqDTO,
					   HttpSession session) {
		log.info("subscription start");
		User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
		if (sessionUser == null) {
			return "redirect:/login-form";
		}
		userSubService.save(reqDTO, sessionUser);
		log.info("subscription finished!");
		return "redirect:/board/" + reqDTO.getBoardId();
	}

	// 구독기업 목록 화면
	@GetMapping("/user-sub/{id}/list")
	public String index(@PathVariable(name = "id") Long id,
						Model model,
						HttpSession session) {
		List<UserSub> userSubList = userSubService.findAllByUserAndCompanyId(id);
		model.addAttribute("userSubList", userSubList);
		return "user-sub/list";
	}
}
