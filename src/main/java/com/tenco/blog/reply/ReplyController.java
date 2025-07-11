package com.tenco.blog.reply;

import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReplyController {
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	private final ReplyService replyService;

	private Object getSessionPrincipal(HttpSession session) {
		Object user = session.getAttribute(Define.SESSIONUSER_USER);
		if(user != null) {
			return user;
		}
		return session.getAttribute(Define.SESSIONUSER_COMPANY);
	}


	@PostMapping("/reply/save")
	public String save(ReplyRequest.SaveDTO saveDTO, HttpSession session) {
		saveDTO.validate();
		Object sessionPrincipal = getSessionPrincipal(session);
		replyService.save(saveDTO, sessionPrincipal);
		log.info("saved");
		return "redirect:/qna/" + saveDTO.getQnaId();
	}

	@PostMapping("/reply/{id}/delete")
	public String delete(@PathVariable(name = "id") Long replyId,
						 @RequestParam(name = "qnaId") Long qnaId,
						 HttpSession session) {
		Object sessionPrincipal = getSessionPrincipal(session);
		replyService.deleteById(replyId, sessionPrincipal);
		return "redirect:/qna/" + qnaId;
	}
}
