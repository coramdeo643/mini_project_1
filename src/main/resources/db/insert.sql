-- User 테이블 데이터 (5명의 사용자)
INSERT INTO user_tb (username, password, email, created_at, role) VALUES
('admin', '1234', 'admin@blog.com', NOW(),'USER'),
('ssar', '1234', 'ssar@nate.com', NOW(),'USER'),
('cos', '1234', 'cos@gmail.com', NOW(),'USER'),
('hong', '1234', 'hong@naver.com', NOW(),'ADMIN'),
('kim', '1234', 'kim@daum.net', NOW(),'COMPANY');
