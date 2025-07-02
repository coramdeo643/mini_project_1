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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserSubController {
	private static final Logger log = LoggerFactory.getLogger(UserSubController.class);
	private final UserSubService userSubService;

	@PostMapping("/usersub/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
		userSubService.deleteById(id, sessionUser);
		return "redirect:/usersub/list";
	}

	@GetMapping("/usersub/save-form")
	public String saveForm() {
		return "usersub/save-form";
	}

	@PostMapping("/usersub/save")
	public String save(UserSubRequest.SaveDTO reqDTO, HttpSession session) {
		User sessionUser = (User)session.getAttribute(Define.SESSIONUSER_USER);
		Company sessionCompany = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		userSubService.save(reqDTO, sessionUser, sessionCompany);
		return "redirect:/usersub/list";
	}

	@GetMapping("/usersub/list")
	public String index(Model model) {
		List<UserSub> userSubList =  userSubService.findAll();
		model.addAttribute("boardList", userSubList);
		return "usersub/list";
	}
}
