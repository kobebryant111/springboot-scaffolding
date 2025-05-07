CREATE TABLE `user_login` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
  `email` VARCHAR(255) COMMENT '邮箱',
  `last_login` DATETIME COMMENT '最后登录时间',
  `is_active` BOOLEAN DEFAULT TRUE COMMENT '账户是否激活',
  INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录表'; 