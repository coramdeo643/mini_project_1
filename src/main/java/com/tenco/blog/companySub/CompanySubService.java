package com.tenco.blog.companySub;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.company.Company;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanySubService {
	private static final Logger log = LoggerFactory.getLogger(CompanySubService.class);
	private final CompanySubJpaRepository companySubJpaRepository;

	@Transactional
	public CompanySub save(CompanySubRequest.SaveDTO saveDTO, Company sCompany) {
		log.info("{},{}", saveDTO.getUser(), saveDTO.getCompany());
		CompanySub cSub = saveDTO.toEntity(sCompany);
		companySubJpaRepository.save(cSub);
		return cSub;
	}

	public List<CompanySub> findAllByUserAndCompanyId(Long id) {
		log.info("구독목록 조회 서비스 처리 시작");
		List<CompanySub> companySubList = companySubJpaRepository.findAllByUserAndCompanyId(id);
		if (companySubList.isEmpty()) {
			throw new Exception404("해당 사용자의 구독 정보를 찾을 수 없습니다.");
		}
		log.info("구독 목록 조회 완료 - 총 {} 개", companySubList.size());
		return companySubList;
	}


	@Transactional
	public void deleteById(Long subsId, Company sCompany) {
		log.info("구독 삭제 서비스 시작 - 구독 ID {}", subsId);
		CompanySub cSub = companySubJpaRepository.findById(subsId).orElseThrow(() ->
				new Exception404("삭제하려는 구독이 없습니다"));
		if (!cSub.isOwner(sCompany.getId())) {
			throw new Exception403("본인의 구독만 취소할 수 있습니다");
		}
		companySubJpaRepository.deleteById(subsId);
	}
}
