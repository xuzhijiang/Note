CREATE DATABASE my_temp;

USE my_temp;

CREATE TABLE sys_user(
	user_name VARCHAR(30) PRIMARY KEY,
	user_desc VARCHAR(100),
	PASSWORD VARCHAR(50)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO sys_user (user_name,PASSWORD,user_desc) VALUES('aa', '123456', 'boy');
INSERT INTO sys_user (user_name,PASSWORD,user_desc) VALUES('bb', '111111', 'girl');

CREATE TABLE sys_role(
	role_code VARCHAR(30) PRIMARY KEY,
	parent_role_code VARCHAR(30),
	role_desc VARCHAR(150)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO sys_role (role_code,parent_role_code,role_desc) VALUES
	('admin', '', 'admin'),
	('staff', 'admin', 'common role');

CREATE TABLE sys_user_role(
	user_name VARCHAR(30),
	role_code VARCHAR(30)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO sys_user_role (user_name, role_code) VALUES
	('aa', 'admin'),
	('aa', 'staff'),
	('bb', 'staff');
