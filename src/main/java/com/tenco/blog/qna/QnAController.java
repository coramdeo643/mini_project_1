package com.tenco.blog.qna;

import com.tenco.blog._core.common.PageLink;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog.board.Board;
import com.tenco.blog.qna.QnARequest;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class QnAController {

	private static final Logger log = LoggerFactory.getLogger(QnAController.class);
	private final QnAService qnaService;
	private final HttpSession session;


	private Object getSessionPrincipal() {
		Object user = session.getAttribute(Define.SESSIONUSER_USER);
		if (user != null) {
			return user;
		}
		return session.getAttribute(Define.SESSIONUSER_COMPANY);
	}


	@GetMapping("/qna/{id}/update-form")
	public String updateForm(@PathVariable Long id, Model model) {
		Object sessionPrincipal = getSessionPrincipal();
		QnARequest.DetailDTO dto = qnaService.findById(id, sessionPrincipal);
		if (!dto.isOwner()) {
			throw new Exception403("수정할 권한이 없습니다.");
		}
		model.addAttribute("qna", dto);
		return "qna/update-form";
	}


	@PostMapping("/qna/{id}/update-form")
	public String update(@PathVariable Long id, QnARequest.UpdateDTO reqDTO) {
		reqDTO.validate();
		Object sessionPrincipal = getSessionPrincipal();
		qnaService.updateById(id, reqDTO, sessionPrincipal);
		return "redirect:/qna/" + id;
	}


	@PostMapping("/qna/{id}/delete")
	public String delete(@PathVariable Long id) {
		Object sessionPrincipal = getSessionPrincipal();
		qnaService.deleteById(id, sessionPrincipal);
		return "redirect:/qna/list";
	}


	@GetMapping("/qna/save-form")
	public String saveForm() {
		if (getSessionPrincipal() == null) {
			throw new Exception401("로그인이 필요합니다.");
		}
		return "qna/save-form";
	}

	@PostMapping("/qna/save")
	public String save(QnARequest.SaveDTO reqDTO) {
		reqDTO.validate();
		Object sessionPrincipal = getSessionPrincipal();
		qnaService.save(reqDTO, sessionPrincipal);
		return "redirect:/qna/list";
	}


	@GetMapping("/qna/list")
	public String list(Model model,
					   @RequestParam(name = "page", defaultValue = "1") int page,
					   @RequestParam(name = "size", defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
		Page<QnA> qnaPage = qnaService.findAllPaging(pageable);
		List<PageLink> pageLinks = new ArrayList<>();
		for (int i = 0; i < qnaPage.getTotalPages(); i++) {
			pageLinks.add(new PageLink(i, i + 1, i == qnaPage.getNumber()));
		}
		Integer prePageNumber = qnaPage.hasPrevious() ? qnaPage.getNumber() : null;
		Integer nxtPageNumber = qnaPage.hasNext() ? qnaPage.getNumber() + 2: null;
		model.addAttribute("pageLinks", pageLinks);
		model.addAttribute("qnaPage", qnaPage);
		model.addAttribute("prePageNumber", prePageNumber);
		model.addAttribute("nxtPageNumber", nxtPageNumber);
		return "qna/list";
	}


	@GetMapping("/qna/{id}")
	public String detail(@PathVariable Long id, Model model) {
		Object sessionPrincipal = getSessionPrincipal();
		QnARequest.DetailDTO dto = qnaService.findById(id, sessionPrincipal);
		model.addAttribute("qna", dto);
		model.addAttribute("isLoggedIn", sessionPrincipal != null);
		return "qna/detail";
	}

}