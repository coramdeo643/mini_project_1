-- -----------------------------
-- Data for `User` table (21 entries)
-- -----------------------------
INSERT INTO `User` (user_id, username, password, type) VALUES
(1, 'admin_user', 'admin_password_hash', 'admin'),
(2, 'user_kim', 'user_pass_hash_1', 'user'),
(3, 'user_lee', 'user_pass_hash_2', 'user'),
(4, 'user_park', 'user_pass_hash_3', 'user'),
(5, 'user_choi', 'user_pass_hash_4', 'user'),
(6, 'user_jung', 'user_pass_hash_5', 'user'),
(7, 'user_kang', 'user_pass_hash_6', 'user'),
(8, 'user_yoon', 'user_pass_hash_7', 'user'),
(9, 'user_lim', 'user_pass_hash_8', 'user'),
(10, 'user_oh', 'user_pass_hash_9', 'user'),
(11, 'user_seo', 'user_pass_hash_10', 'user'),
(12, 'company_alpha', 'company_pass_hash_1', 'company'),
(13, 'company_beta', 'company_pass_hash_2', 'company'),
(14, 'company_gamma', 'company_pass_hash_3', 'company'),
(15, 'company_delta', 'company_pass_hash_4', 'company'),
(16, 'company_epsilon', 'company_pass_hash_5', 'company'),
(17, 'company_zeta', 'company_pass_hash_6', 'company'),
(18, 'company_eta', 'company_pass_hash_7', 'company'),
(19, 'company_theta', 'company_pass_hash_8', 'company'),
(20, 'company_iota', 'company_pass_hash_9', 'company'),
(21, 'company_kappa', 'company_pass_hash_10', 'company');

-- -----------------------------
-- Data for `personal_profile` table (10 entries for 'user' type)
-- -----------------------------
INSERT INTO `personal_profile` (user_id, name, phone, email) VALUES
(2, '김철수', '010-1111-1111', 'kim.cs@example.com'),
(3, '이영희', '010-2222-2222', 'lee.yh@example.com'),
(4, '박지민', '010-3333-3333', 'park.jm@example.com'),
(5, '최현우', '010-4444-4444', 'choi.hw@example.com'),
(6, '정수정', '010-5555-5555', 'jung.sj@example.com'),
(7, '강민준', '010-6666-6666', 'kang.mj@example.com'),
(8, '윤미래', '010-7777-7777', 'yoon.mr@example.com'),
(9, '임서준', '010-8888-8888', 'lim.sj@example.com'),
(10, '오지훈', '010-9999-9999', 'oh.jh@example.com'),
(11, '서하윤', '010-0000-0000', 'seo.hy@example.com');

-- -----------------------------
-- Data for `company_profile` table (10 entries for 'company' type)
-- -----------------------------
INSERT INTO `company_profile` (user_id, company_email, company_phone, company_business_no, company_industry, company_name, company_ceo_name, company_address) VALUES
(12, 'contact@alpha.com', '02-1234-0001', '123-01-00001', 'IT 솔루션', '알파테크', '김알파', '서울시 강남구 테헤란로 1'),
(13, 'info@beta.com', '02-1234-0002', '123-02-00002', '소프트웨어 개발', '베타소프트', '이베타', '경기도 성남시 분당구 판교역로 2'),
(14, 'hr@gamma.com', '02-1234-0003', '123-03-00003', '게임 개발', '감마스튜디오', '박감마', '서울시 서초구 서초대로 3'),
(15, 'support@delta.com', '02-1234-0004', '123-04-00004', '핀테크', '델타파이낸스', '최델타', '부산시 해운대구 센텀남대로 4'),
(16, 'careers@epsilon.com', '02-1234-0005', '123-05-00005', '바이오 헬스', '엡실론바이오', '정엡실론', '대구시 수성구 동대구로 5'),
(17, 'sales@zeta.com', '02-1234-0006', '123-06-00006', '교육 기술', '제타에듀', '강제타', '인천시 연수구 송도과학로 6'),
(18, 'pr@eta.com', '02-1234-0007', '123-07-00007', '컨설팅', '에타컨설팅', '윤에타', '광주시 서구 상무대로 7'),
(19, 'webmaster@theta.com', '02-1234-0008', '123-08-00008', '이커머스', '쎄타마켓', '임쎄타', '대전시 유성구 대덕대로 8'),
(20, 'recruit@iota.com', '02-1234-0009', '123-09-00009', '제조업', '이오타산업', '오이오타', '울산시 남구 산업로 9'),
(21, 'info@kappa.com', '02-1234-0010', '123-10-00010', '미디어 엔터', '카파미디어', '서카파', '세종시 한누리대로 10');

-- -----------------------------
-- Data for `resume_post` table (based on user_ids 2-11)
-- -----------------------------
INSERT INTO `resume_post` (user_id, title, summary) VALUES
(2, '신입 프론트엔드 개발자 이력서 - 김철수', 'React, Vue.js를 활용한 웹 개발 경험.'),
(3, '경력 백엔드 개발자 이력서 - 이영희', 'Java, Spring Boot를 이용한 RESTful API 개발.'),
(4, '데이터 분석가 포트폴리오 - 박지민', 'Python, R을 이용한 데이터 분석 및 시각화 프로젝트.'),
(5, 'UX/UI 디자이너 포트폴리오 - 최현우', 'Figma, Sketch를 활용한 디자인 시스템 구축.'),
(6, '모바일 앱 개발자 이력서 - 정수정', 'Kotlin, Swift를 이용한 Android/iOS 앱 개발.'),
(7, '클라우드 엔지니어 이력서 - 강민준', 'AWS, Azure 클라우드 환경 구축 및 관리.'),
(8, '풀스택 개발자 이력서 - 윤미래', 'Node.js, Express를 활용한 풀스택 개발 경험.'),
(9, 'QA 엔지니어 이력서 - 임서준', '소프트웨어 테스트 자동화 및 수동 테스트 수행.'),
(10, '인프라 엔지니어 이력서 - 오지훈', 'Docker, Kubernetes를 이용한 컨테이너 관리.'),
(11, '게임 개발자 이력서 - 서하윤', 'Unity, Unreal Engine을 이용한 게임 개발.');

-- -----------------------------
-- Data for `Skill` table (10 entries)
-- -----------------------------
INSERT INTO `Skill` (name) VALUES
('Java'),
('SQL'),
('Python'),
('Spring Boot'),
('React'),
('Vue.js'),
('AWS'),
('Docker'),
('C'),
('HTML');

-- -----------------------------
-- Data for `user_skill` table (referencing resume_post IDs)
-- (Note: user_skill's user_id column references resume_post's resume_id.
-- Ensure the resume_id values below correspond to actual resume_id values created above.
-- Assuming auto-incremented resume_id for resume_post starts from 1,
-- then resume_id 1 corresponds to user_id 2, resume_id 2 to user_id 3, and so on.)
-- -----------------------------
INSERT INTO `user_skill` (user_id, skill_id) VALUES
(1, 1), -- resume_id 1 (user_id 2) -> Skill ID 1 (Java)
(1, 5), -- resume_id 1 (user_id 2) -> Skill ID 5 (React)
(2, 2), -- resume_id 2 (user_id 3) -> Skill ID 2 (SQL)
(2, 4), -- resume_id 2 (user_id 3) -> Skill ID 4 (Spring Boot)
(3, 3), -- resume_id 3 (user_id 4) -> Skill ID 3 (Python)
(4, 6), -- resume_id 4 (user_id 5) -> Skill ID 6 (Vue.js)
(5, 7), -- resume_id 5 (user_id 6) -> Skill ID 7 (AWS)
(6, 8), -- resume_id 6 (user_id 7) -> Skill ID 8 (Docker)
(7, 1), -- resume_id 7 (user_id 8) -> Skill ID 1 (Java)
(8, 10); -- resume_id 8 (user_id 9) -> Skill ID 10 (HTML)

-- -----------------------------
-- Data for `favorite_company` table (based on user_ids 2-11 and company_ids 12-21)
-- -----------------------------
INSERT INTO `favorite_company` (user_id, company_id) VALUES
(2, 12), -- user_kim -> company_alpha
(3, 13), -- user_lee -> company_beta
(4, 14), -- user_park -> company_gamma
(5, 15), -- user_choi -> company_delta
(6, 16), -- user_jung -> company_epsilon
(7, 17), -- user_kang -> company_zeta
(8, 12), -- user_yoon -> company_alpha
(9, 13), -- user_lim -> company_beta
(10, 14), -- user_oh -> company_gamma
(11, 15); -- user_seo -> company_delta

-- -----------------------------
-- Data for `board_post` table (based on user_ids 1-11)
-- -----------------------------
INSERT INTO `board_post` (user_id, title, content) VALUES
(1, '관리자 공지사항: 시스템 점검 안내', '서비스 안정화를 위한 시스템 점검이 예정되어 있습니다.'),
(2, '프론트엔드 스터디 그룹 모집', 'React 스터디 함께 하실 분 찾습니다!'),
(3, 'Spring Boot 마이크로서비스 아키텍처', 'MSA 구축 경험 공유합니다.'),
(4, '데이터 시각화 툴 추천', '효율적인 데이터 시각화 툴은 무엇일까요?'),
(5, 'UX 디자인 포트폴리오 리뷰 요청', '제 UX/UI 포트폴리오에 대한 피드백 부탁드립니다.'),
(6, '모바일 앱 개발 최신 트렌드', 'Flutter와 Kotlin Multiplatform 중 고민 중입니다.'),
(7, '클라우드 보안 베스트 프랙티스', 'AWS 보안 설정 시 유의할 점.'),
(8, 'SQL 쿼리 성능 튜닝 팁', '대용량 데이터 조회 시 쿼리 최적화 방법.'),
(9, 'Git Workflow 효율화 방안', 'Git rebase vs merge, 어떤 것을 선호하시나요?'),
(10, '사이드 프로젝트 아이디어 공유', '작은 규모의 개발 프로젝트 아이디어 나눔.');

-- -----------------------------
-- Data for `company_post` table (based on company_ids 12-21)
-- -----------------------------
INSERT INTO `company_post` (company_id, title, position, location, content, schedule, deadline, created_at) VALUES
(12, '신입 프론트엔드 개발자 채용 (React/Vue)', '프론트엔드 개발자', '서울 강남구', 'React 또는 Vue.js 기반 웹 서비스 개발.', '주 5일, 09:00-18:00', '2025-07-31', NOW()),
(13, 'Java 백엔드 개발자 모집 (Spring Boot)', '백엔드 개발자', '경기 성남시', 'Spring Boot 기반 RESTful API 설계 및 개발.', '주 5일, 10:00-19:00', '2025-08-15', NOW()),
(14, 'Unity 게임 클라이언트 개발자', '클라이언트 개발자', '서울 서초구', 'Unity 엔진을 이용한 모바일 게임 개발.', '주 5일, 09:00-18:00', '2025-07-20', NOW()),
(15, '데이터 엔지니어 채용 (Python/SQL)', '데이터 엔지니어', '부산 해운대구', '빅데이터 파이프라인 구축 및 데이터 처리.', '주 5일, 09:00-18:00', '2025-08-30', NOW()),
(16, 'DevOps 엔지니어 모집 (AWS/Docker)', 'DevOps 엔지니어', '대구 수성구', '클라우드 인프라 운영 및 CI/CD 구축.', '주 5일, 10:00-19:00', '2025-09-10', NOW()),
(17, '교육 콘텐츠 기획자 채용', '콘텐츠 기획자', '인천 연수구', '온라인 교육 콘텐츠 기획 및 개발 협력.', '주 5일, 09:00-18:00', '2025-07-25', NOW()),
(18, '경력 컨설턴트 모집', '경영 컨설턴트', '광주 서구', 'IT 산업 관련 컨설팅 프로젝트 수행.', '주 5일, 09:00-18:00', '2025-08-01', NOW()),
(19, 'E-commerce 백엔드 개발자', '백엔드 개발자', '대전 유성구', '온라인 쇼핑몰 시스템 개발 및 유지보수.', '주 5일, 10:00-19:00', '2025-08-20', NOW()),
(20, '생산 관리 시스템 개발자', '생산 관리 SW 개발자', '울산 남구', 'MES/ERP 시스템 개발 및 연동.', '주 5일, 09:00-18:00', '2025-07-28', NOW()),
(21, '미디어 콘텐츠 마케터', '콘텐츠 마케터', '세종시', '디지털 미디어 콘텐츠 홍보 및 캠페인 실행.', '주 5일, 09:00-18:00', '2025-09-05', NOW());

-- -----------------------------
-- Data for `company_post_skill` table (referencing company_post IDs and Skill IDs)
-- -----------------------------
INSERT INTO `company_post_skill` (job_post_id, skill_id) VALUES
(1, 5), -- job_id 1 (프론트엔드) -> React
(1, 6), -- job_id 1 (프론트엔드) -> Vue.js
(2, 1), -- job_id 2 (Java 백엔드) -> Java
(2, 4), -- job_id 2 (Java 백엔드) -> Spring Boot
(3, 1), -- job_id 3 (Unity 게임) -> Java (for C# like syntax within Unity)
(4, 3), -- job_id 4 (데이터 엔지니어) -> Python
(4, 2), -- job_id 4 (데이터 엔지니어) -> SQL
(5, 7), -- job_id 5 (DevOps) -> AWS
(5, 8), -- job_id 5 (DevOps) -> Docker
(7, 2); -- job_id 7 (E-commerce 백엔드) -> SQL (for database interaction)

-- -----------------------------
-- Data for `favorite_candidate` table (based on company_ids 12-21 and user_ids 2-11)
-- -----------------------------
INSERT INTO `favorite_candidate` (company_id, user_id) VALUES
(12, 2), -- company_alpha -> user_kim
(13, 3), -- company_beta -> user_lee
(14, 4), -- company_gamma -> user_park
(15, 5), -- company_delta -> user_choi
(16, 6), -- company_epsilon -> user_jung
(17, 7), -- company_zeta -> user_kang
(12, 8), -- company_alpha -> user_yoon
(13, 9), -- company_beta -> user_lim
(14, 10), -- company_gamma -> user_oh
(15, 11); -- company_delta -> user_seo

-- -----------------------------
-- Data for `Rating` table (based on interactions between users and companies)
-- -----------------------------
INSERT INTO `Rating` (company_id, user_id, score, type) VALUES
(12, 2, 5, 'user'), -- user_kim이 알파테크에 5점
(13, 3, 4, 'user'), -- user_lee가 베타소프트에 4점
(14, 4, 5, 'user'), -- user_park가 감마스튜디오에 5점
(2, 12, 5, 'company'), -- 알파테크가 user_kim에 5점
(3, 13, 4, 'company'), -- 베타소프트가 user_lee에 4점
(4, 14, 5, 'company'), -- 감마스튜디오가 user_park에 5점
(15, 5, 3, 'user'), -- user_choi가 델타파이낸스에 3점
(16, 6, 4, 'user'), -- user_jung이 엡실론바이오에 4점
(5, 15, 3, 'company'), -- 델타파이낸스가 user_choi에 3점
(6, 16, 4, 'company'); -- 엡실론바이오가 user_jung에 4점