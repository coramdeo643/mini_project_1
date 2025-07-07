package com.tenco.blog.UserSub;

import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		//return "redirect:/user-sub/" + sessionUser.getId() + "/list";
		return "redirect:/user-sub/board-list";
	}

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
}
