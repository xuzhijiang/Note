USE mydb;

CREATE TABLE t_user (
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL UNIQUE,
	PASSWORD VARCHAR(50) NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE t_role (
	id INT PRIMARY KEY AUTO_INCREMENT,
	role_name VARCHAR(50) NOT NULL UNIQUE,
	create_time TIMESTAMP NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE t_permission (
	id INT PRIMARY KEY AUTO_INCREMENT,
	permission_name VARCHAR(50) NOT NULL UNIQUE,
	create_time TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE t_user_role (
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id INT REFERENCES t_user(id),
	role_id INT REFERENCES t_role(id),
	UNIQUE(user_id, role_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE t_role_permission (
	id INT PRIMARY KEY AUTO_INCREMENT,
	permission_id INT REFERENCES t_permission(id),
	role_id INT REFERENCES t_role(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 这些操作其实都是后台增加用户,增加权限,增加角色来搞的.

INSERT INTO t_user (username, PASSWORD) VALUES("松松", "123"), ("张远航", "456");

INSERT INTO t_role (role_name, create_time) VALUES("banzhang", "2019-10-09"), ("student", "2019-10-09");

INSERT INTO t_permission (permission_name, create_time) VALUES("student:yq", "2019-10-09"), ("student:study", "2019-10-09");

INSERT INTO t_user_role(user_id, role_id) VALUES(1,1), (1,2), (2,2);

INSERT INTO t_role_permission (permission_id, role_id) VALUES(1, 1), (2, 1), (2,2);

ALTER TABLE t_user ADD COLUMN salt VARCHAR(50) ;

UPDATE t_user SET PASSWORD='fPMIwSdkAE7viiglwlw/0UoXBswYEGVN59wLbsWwh24=', salt='b4255e9d-c8c9-4b70-aded-5f3bfb699d0b'
WHERE username='松松';