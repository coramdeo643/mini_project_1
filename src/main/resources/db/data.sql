-- User 테이블 데이터 (5명의 사용자)
INSERT INTO user_tb (username, password, email, created_at, role) VALUES
('admin', '1234', 'admin@blog.com', NOW(),'USER'),
('ssar', '1234', 'ssar@nate.com', NOW(),'USER'),
('cos', '1234', 'cos@gmail.com', NOW(),'USER'),
('hong', '1234', 'hong@naver.com', NOW(),'ADMIN'),
('kim', '1234', 'kim@daum.net', NOW(),'COMPANY');

-- 2단계: Board 테이블 데이터 (10개의 게시글)
-- 주의: user_id는 위에서 생성된 사용자의 id를 참조

-- admin 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('블로그 개설을 환영합니다!', '안녕하세요! 새로운 블로그가 오픈했습니다. 많은 관심과 참여 부탁드립니다.', 1, NOW()),
('공지사항: 이용수칙 안내', '블로그 이용 시 지켜야 할 기본적인 수칙들을 안내드립니다. 건전한 소통 문화를 만들어가요.', 1, NOW()),
('업데이트 소식', '새로운 기능들이 추가되었습니다. 댓글 기능과 좋아요 기능을 곧 만나보실 수 있습니다.', 1, NOW());

-- ssar 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다. JPA가 정말 편리하네요!', 2, NOW()),
('JPA 연관관계 정리노트', '오늘 배운 @ManyToOne, @OneToMany 연관관계에 대해 정리해봤습니다. 헷갈리는 부분이 많아요.', 2, NOW()),
('코딩테스트 문제 추천', '백준과 프로그래머스에서 풀어볼 만한 문제들을 추천드립니다. 알고리즘 공부 화이팅!', 2, NOW());

-- cos 사용자가 작성한 게시글 (2개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('React vs Vue 비교', '프론트엔드 프레임워크 선택에 고민이 많았는데, 각각의 장단점을 비교해봤습니다.', 3, NOW()),
('개발자 취업 팁 공유', '신입 개발자로 취업하면서 도움이 되었던 팁들을 공유합니다. 포트폴리오가 중요해요!', 3, NOW());

-- hong 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('첫 번째 게시글입니다', '안녕하세요! 블로그에 처음 글을 올려봅니다. 앞으로 자주 소통해요~', 4, NOW());

-- kim 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('맛집 추천 - 강남역 근처', '강남역 근처에서 점심 먹기 좋은 맛집들을 추천드립니다. 가성비도 좋아요!', 5, NOW());


INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('블로그 개설을 환영합니다!', '안녕하세요! 새로운 블로그가 오픈했습니다. 많은 관심과 참여 부탁드립니다.', 1, NOW()),
('공지사항: 이용수칙 안내', '블로그 이용 시 지켜야 할 기본적인 수칙들을 안내드립니다. 건전한 소통 문화를 만들어가요.', 1, NOW()),
('업데이트 소식', '새로운 기능들이 추가되었습니다. 댓글 기능과 좋아요 기능을 곧 만나보실 수 있습니다.', 1, NOW());

-- ssar 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다. JPA가 정말 편리하네요!', 2, NOW()),
('JPA 연관관계 정리노트', '오늘 배운 @ManyToOne, @OneToMany 연관관계에 대해 정리해봤습니다. 헷갈리는 부분이 많아요.', 2, NOW()),
('코딩테스트 문제 추천', '백준과 프로그래머스에서 풀어볼 만한 문제들을 추천드립니다. 알고리즘 공부 화이팅!', 2, NOW());

-- cos 사용자가 작성한 게시글 (2개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('React vs Vue 비교', '프론트엔드 프레임워크 선택에 고민이 많았는데, 각각의 장단점을 비교해봤습니다.', 3, NOW()),
('개발자 취업 팁 공유', '신입 개발자로 취업하면서 도움이 되었던 팁들을 공유합니다. 포트폴리오가 중요해요!', 3, NOW());

-- hong 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('첫 번째 게시글입니다', '안녕하세요! 블로그에 처음 글을 올려봅니다. 앞으로 자주 소통해요~', 4, NOW());

-- kim 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('맛집 추천 - 강남역 근처', '강남역 근처에서 점심 먹기 좋은 맛집들을 추천드립니다. 가성비도 좋아요!', 5, NOW());


-- qna 리스트

-- hong 사용자가 작성한 게시글 (1개)
INSERT INTO qna_tb (title, content, user_id, created_at) VALUES
('[구직자] 면접 결과는 언제쯤 알 수 있을까요?', '지난주 금요일에 면접을 봤는데, 결과 발표는 보통 얼마나 걸리나요?', 1, NOW()),
('[채용기업] 이력서에 기재된 프로젝트에 대해 궁금합니다.', '김OO 지원자님, 이력서에 작성하신 XXX 프로젝트의 역할에 대해 좀 더 자세히 설명해주실 수 있을까요?', 2, NOW()),
('[구직자] 경력직 채용 절차는 어떻게 되나요?', '현재 이직을 준비 중인데, 경력직 채용 프로세스에 대해 궁금합니다.', 2, NOW()),
('[채용기업] 신입 개발자의 포트폴리오 비중은 어느 정도인가요?', '이력서 외에 신입 지원자 포트폴리오를 어느 정도 비중으로 보시는지 궁금합니다.', 5, NOW()),
('[구직자] 복리후생 중 자기계발 지원은 구체적으로 어떤 내용인가요?', '귀사의 자기계발 지원 프로그램에 대해 자세히 알고 싶습니다.', 3, NOW()),
('[채용기업] 지원하신 직무에 대한 경험이 충분한가요?', '지원자님의 이력서를 검토했는데, 해당 직무에 대한 깊은 경험을 가지고 계신지 확인하고 싶습니다.', 5, NOW()),
('[구직자] 기술 스택 관련 질문 드립니다.', '백엔드 개발자로 지원 예정인데, 주로 사용하시는 기술 스택은 무엇인가요?', 2, NOW()),
('[채용기업] 인터뷰 가능한 시간대 문의드립니다.', '면접 일정을 조율하기 위해 김OO 지원자님의 편리한 시간대를 알려주시면 감사하겠습니다.', 5, NOW()),
('[구직자] 기업 문화가 궁금합니다.', '귀사의 팀 분위기나 기업 문화는 어떤지 대략적으로 알려주실 수 있을까요?', 3, NOW()),
('[채용기업] 특정 프로젝트에 대한 추가 설명 요청합니다.', '이력서에 명시된 "OOO 프로젝트"에 대해 더 상세한 설명을 부탁드립니다.', 5, NOW()),
('[구직자] 온보딩 프로세스는 어떻게 진행되나요?', '입사 후 신입 직원을 위한 온보딩 프로그램이 있는지 궁금합니다.', 2, NOW()),
('[채용기업] 합류하게 되시면 어떤 기여를 하고 싶으신가요?', '저희 회사에 합류하신다면 어떤 부분에서 기여하고 싶은지 궁금합니다.', 5, NOW());

-- ppost sample
INSERT INTO ppost_tb (title, content, user_id, created_at) VALUES
('이력서_1', '개발 언어: Java, Spring Boot | 경력: 백엔드 개발 1년차 (게시판 CRUD 기능 구현 경험)', 2, NOW()),
('이력서_2', '개발 언어: Java, Spring Boot, MySQL | 경력: 백엔드 개발 2년차 (RESTful API 및 DB 연동 프로젝트 경험)', 2, NOW()),
('이력서_3', '개발 언어: Java, Spring Boot, Python, HTML/CSS/JS | 경력: 풀스택 개발 3년차 (JPA 활용 및 간단한 프론트엔드 연동)', 2, NOW()),
('이력서_4', '개발 언어: Java, Spring Cloud, Kafka, Docker | 경력: 백엔드 개발 4년차 (마이크로서비스 아키텍처 및 분산 시스템 이해)', 2, NOW()),
('이력서_5', '개발 언어: Java, Spring Cloud, Kotlin, AWS | 경력: 백엔드 개발 5년차 (클라우드 기반 대용량 트래픽 처리 시스템 설계 및 리팩토링 참여)', 2, NOW());