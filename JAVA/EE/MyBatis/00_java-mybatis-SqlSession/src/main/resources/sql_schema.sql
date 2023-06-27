DROP DATABASE IF EXISTS mydb;

CREATE DATABASE IF NOT EXISTS `mydb`;

USE `mydb`;

CREATE TABLE IF NOT EXISTS `user`(
	id INT NOT NULL AUTO_INCREMENT,
	last_name VARCHAR(20),
	email VARCHAR(20),
	gender VARCHAR(20),
	d_id INT(11),
	PRIMARY KEY (`id`)
)ENGINE=INNODB CHARSET=utf8;

CREATE TABLE tb_dept(
	id INT(11) NOT NULL AUTO_INCREMENT,
	dept_name VARCHAR(255),
	PRIMARY KEY (`id`)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO tb_dept (dept_name) VALUES('开发部');
INSERT INTO tb_dept (dept_name) VALUES('销售部');

INSERT INTO `user` (last_name, email, gender, d_id) VALUES
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1),
('name1', 'email01@qq.com', 'male', 1),
('name2', 'email02@qq.com', 'female', 2),
('name3', 'email03@qq.com', 'male', 1);