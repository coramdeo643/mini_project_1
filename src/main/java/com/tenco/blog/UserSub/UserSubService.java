package com.tenco.blog.UserSub;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.company.Company;
import com.tenco.blog.ppost.PPost;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSubService {
	private static final Logger log = LoggerFactory.getLogger(UserSubService.class);
	private final UserSubJpaRepository userSubJpaRepository;

	@Transactional
	public UserSub save(UserSubRequest.SaveDTO saveDTO, User sUser, Company sCompany) {
		log.info("{},{}", saveDTO.getUser(), saveDTO.getCompany());
		UserSub userSub = saveDTO.toEntity(sUser, sCompany);
		userSubJpaRepository.save(userSub);
		return userSub;
	}

	public List<UserSub> findAll() {
		log.info("게시글 조회 서비스 처리 시작");
		List<UserSub> userSubList = userSubJpaRepository.findAllJoinUser();
		log.info("게시글 목록 조회 완료 - 총 {} 개", userSubList.size());
		return userSubList;
	}

	@Transactional
	public void deleteById(Long id, User sessionUser) {
		log.info("게시글 삭제 서비스 시작 - ID {}", id);
		UserSub userSub = userSubJpaRepository.findById(id).orElseThrow(() -> {
			return new Exception404("삭제하려는 게시글이 없습니다");
		});
		if(!userSub.isOwner(sessionUser.getId())) {
			throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
		}
		userSubJpaRepository.deleteById(id);
	}



}
