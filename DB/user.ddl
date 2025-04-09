CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,      -- 用户ID，主键，自增
                       name VARCHAR(255) NOT NULL,         -- 用户名，不能为空
                       age INT NOT NULL                 -- 年龄，不能为空
);

insert into users(name,age) VALUES ('吴邪',28);