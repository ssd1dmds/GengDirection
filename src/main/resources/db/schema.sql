-- GengDirection database schema (MySQL 8+)
CREATE DATABASE IF NOT EXISTS geng_direction
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE geng_direction;

CREATE TABLE IF NOT EXISTS role_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_code VARCHAR(32) NOT NULL UNIQUE,
  role_name VARCHAR(64) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL UNIQUE,
  password_hash VARCHAR(128) NOT NULL,
  nickname VARCHAR(64) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1 COMMENT '1: active, 0: disabled',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES user_info(id),
  CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role_info(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS geng_post (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  content TEXT NOT NULL,
  source_url VARCHAR(255) NULL,
  author_id BIGINT NOT NULL,
  heat_score INT NOT NULL DEFAULT 0,
  status TINYINT NOT NULL DEFAULT 1 COMMENT '1: draft, 2: published, 3: archived',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_geng_post_author FOREIGN KEY (author_id) REFERENCES user_info(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tag_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tag_name VARCHAR(64) NOT NULL UNIQUE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS geng_post_tag (
  post_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (post_id, tag_id),
  CONSTRAINT fk_post_tag_post FOREIGN KEY (post_id) REFERENCES geng_post(id) ON DELETE CASCADE,
  CONSTRAINT fk_post_tag_tag FOREIGN KEY (tag_id) REFERENCES tag_info(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS post_comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  content VARCHAR(500) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES geng_post(id) ON DELETE CASCADE,
  CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user_info(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_favorite_post (
  user_id BIGINT NOT NULL,
  post_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, post_id),
  CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES user_info(id) ON DELETE CASCADE,
  CONSTRAINT fk_favorite_post FOREIGN KEY (post_id) REFERENCES geng_post(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_geng_post_author ON geng_post(author_id);
CREATE INDEX idx_geng_post_status ON geng_post(status);
CREATE INDEX idx_comment_post_id ON post_comment(post_id);

