package com.tenco.blog.board;

import com.tenco.blog.UserSub.UserSub;
import com.tenco.blog.UserSub.UserSubService;
import com.tenco.blog.company.Company;
import com.tenco.blog.company.CompanyService;
import com.tenco.blog.ppost.PPost;
import com.tenco.blog.ppost.PPostService;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
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
public class BoardController {

	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	private final CompanyService companyService;
	private final UserSubService userSubService;
	private final PPostService pPostService;
	private final BoardJpaRepository boardJpaRepository;

	/**
	 * 게시글 수정 화면 요청
	 */
	@GetMapping("/board/{id}/update-form")
	public String updateForm(@PathVariable(name = "id") Long boardId,
							 HttpServletRequest request, HttpSession session) {
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		boardService.checkBoardOwner(boardId, sessionUser.getId());
		request.setAttribute("board", boardService.findById(boardId));
		return "board/update-form";
	}


	@PostMapping("/board/{id}/update-form")
	public String update(@PathVariable(name = "id") Long boardId,
						 BoardRequest.UpdateDTO reqDTO,
						 HttpSession session) {
		// 1. 인증 검사
		// 2. 데이터 유효성 검사
		// 3. 수정 요청 위임
		// 4. 리다이렉트 처리
		reqDTO.validate();
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		boardService.updateById(boardId, reqDTO, sessionUser);

		return "redirect:/board/" + boardId;
	}

	@PostMapping("/board/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id, HttpSession session) {
		// 1. 인증 검사
		// 2. 세션에서 로그인 한 사용자 정보 추출
		// 3. 서비스 위임
		// 4. 메인 페이지로 리다이렉트 처리
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		boardService.deleteById(id, sessionUser);
		return "redirect:/board/list";
	}

	@GetMapping("/board/save-form")
	public String saveForm(HttpSession session, Model model) {

		// 1. 로그인한 유저 정보
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		Company company = companyService.findById(sessionUser.getId());
		model.addAttribute("company", company);
		return "board/save-form";
	}

	@PostMapping("/board/save")
	public String save(BoardRequest.SaveDTO reqDTO, HttpSession session) {
		// 1. 인증검사
		// 2. 유효성 검사
		// 3. 서비스 계층 위임
		reqDTO.validate();
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		boardService.save(reqDTO, sessionUser);
		return "redirect:/board/list";
	}

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		Object companyUser = session.getAttribute("companyUser");

		if (companyUser != null) {
			// 기업회원인 경우
			List<PPost> ppostList = pPostService.findAll();
			model.addAttribute("ppostList", ppostList);
			model.addAttribute("isCompanyUser", true);
		} else {
			// 일반 사용자 or 비로그인
			List<Board> boardList = boardService.findAll();
			model.addAttribute("boardList", boardList);
			model.addAttribute("isCompanyUser", false);
		}
		return "index";
	}


	@GetMapping("/board/{id}")
	public String detail(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
		Company sessionUser = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		Board board = boardService.findByIdWithBoard(id, sessionUser);
		model.addAttribute("board", board);
		return "board/detail";
	}


	@GetMapping("/board/my-list")
	public String myBoardList(HttpSession session, Model model) {
		Company company = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);
		if (company == null) {
			return "redirect:/company/login-form";
		}
		Long companyId = company.getId();
		List<Board> companyBoardList = boardJpaRepository.findByCompanyId(companyId);

		model.addAttribute("companyBoardList", companyBoardList);
		return "board/my-list";
	}

	@GetMapping("/user-sub/board-list")
	public String userSubBoardList(HttpSession session, Model model) {
		User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
		if (sessionUser == null) {
			return "redirect:/login-form";
		}
		List<Board> userSubBoardList = boardService.findBoardsBySubscribedUserId(sessionUser.getId());
		model.addAttribute("userSubBoardList", userSubBoardList);
		List<UserSub> userSubList = userSubService.findAllByUserAndCompanyId(sessionUser.getId());
		model.addAttribute("userSubList", userSubList);
		return "user-sub/board-list";
	}


	@GetMapping("/board/filter")
	public String filterBySkill(@RequestParam("skill") String skillName, Model model) {
		List<Board> boardList = boardJpaRepository.findBySkillName(skillName);
		model.addAttribute("boardList", boardList);
		return "index"; // 머스태치 페이지 이름
	}

}
