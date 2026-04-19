-- Seed data for GengDirection demo
USE geng_direction;

INSERT INTO role_info (role_code, role_name) VALUES
('ADMIN', 'Administrator'),
('USER', 'Normal User')
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

INSERT INTO user_info (username, password_hash, nickname, status) VALUES
('admin', '$2a$10$replace_with_bcrypt_hash', 'SystemAdmin', 1),
('student01', '$2a$10$replace_with_bcrypt_hash', 'StudentA', 1)
ON DUPLICATE KEY UPDATE nickname = VALUES(nickname), status = VALUES(status);

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user_info u
JOIN role_info r ON (u.username = 'admin' AND r.role_code = 'ADMIN')
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user_info u
JOIN role_info r ON (u.username = 'student01' AND r.role_code = 'USER')
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

INSERT INTO tag_info (tag_name) VALUES
('学习'),
('校园'),
('热点')
ON DUPLICATE KEY UPDATE tag_name = VALUES(tag_name);

INSERT INTO geng_post (title, content, source_url, author_id, heat_score, status)
SELECT '第一次实验梗', 'Spring Boot + MyBatis 真的香。', 'https://example.com', u.id, 100, 2
FROM user_info u
WHERE u.username = 'student01'
ON DUPLICATE KEY UPDATE heat_score = VALUES(heat_score), status = VALUES(status);

INSERT INTO geng_post_tag (post_id, tag_id)
SELECT p.id, t.id
FROM geng_post p
JOIN tag_info t ON t.tag_name IN ('学习', '热点')
WHERE p.title = '第一次实验梗'
ON DUPLICATE KEY UPDATE post_id = VALUES(post_id);

INSERT INTO post_comment (post_id, user_id, content)
SELECT p.id, u.id, '这个梗我懂了，给你点赞。'
FROM geng_post p
JOIN user_info u ON u.username = 'admin'
WHERE p.title = '第一次实验梗';

