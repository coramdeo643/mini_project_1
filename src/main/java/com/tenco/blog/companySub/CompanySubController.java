package com.tenco.blog.companySub;

import com.tenco.blog.company.Company;
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
public class CompanySubController {
	private static final Logger log = LoggerFactory.getLogger(CompanySubController.class);
	private final CompanySubService companySubService;

	// 삭제 기능
	@PostMapping("/company-sub/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
		Company sessionC = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		if (sessionC == null) {
			return "redirect:/login-form";
		}
		companySubService.deleteById(id, sessionC);
		return "redirect:/user-sub/ppost-list";
	}

	// 구독하기 기능
	@PostMapping("/company-sub/save")
	public String save(CompanySubRequest.SaveDTO reqDTO,
					   HttpSession session) {
		log.info("subscription start");
		Company sessionC = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		if (sessionC == null) {
			return "redirect:/login-form";
		}
		companySubService.save(reqDTO, sessionC);
		log.info("subscription finished!");
		return "redirect:/ppost/" + reqDTO.getPpostId();
	}
}
