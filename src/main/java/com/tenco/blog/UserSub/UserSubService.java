package com.tenco.blog.UserSub;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
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
	public UserSub save(UserSubRequest.SaveDTO saveDTO, User sUser) {
		log.info("{},{}", sUser.getId(), saveDTO.getCompanyId());
		if (userSubJpaRepository.existsByCompanyIdAndUserId(saveDTO.getCompanyId(), sUser.getId())) {
			throw new Exception403("이미 구독했습니다.");
		}
		UserSub userSub = saveDTO.toEntity(sUser);
		userSubJpaRepository.save(userSub);
		return userSub;
	}

	public List<UserSub> findAllByUserAndCompanyId(Long id) {
		log.info("구독목록 조회 서비스 처리 시작");
		List<UserSub> userSubList = userSubJpaRepository.findAllByUserAndCompanyId(id);
		if (userSubList.isEmpty()) {
			throw new Exception404("해당 사용자의 구독 정보를 찾을 수 없습니다.");
		}
		log.info("구독 목록 조회 완료 - 총 {} 개", userSubList.size());
		return userSubList;
	}


	@Transactional
	public void deleteById(Long subsId, User sessionUser) {
		log.info("구독 삭제 서비스 시작 - 구독 ID {}", subsId);
		UserSub userSub = userSubJpaRepository.findById(subsId).orElseThrow(() ->
			 new Exception404("삭제하려는 구독이 없습니다"));
		if(!userSub.isOwner(sessionUser.getId())) {
			throw new Exception403("본인의 구독만 취소할 수 있습니다");
		}
		userSubJpaRepository.deleteById(subsId);
	}



}
