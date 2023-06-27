DROP DATABASE IF EXISTS user_db;

CREATE DATABASE IF NOT EXISTS `user_db` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

USE user_db;

CREATE TABLE `t_user` (
`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
`username` VARCHAR(64) NOT NULL,
`password` VARCHAR(64) NOT NULL,
`fullname` VARCHAR(255) NOT NULL COMMENT '用户姓名',
`mobile` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

# 密码是123
INSERT INTO t_user (username, PASSWORD, fullname, mobile) VALUES('zhangsan', '$2a$10$aFsOFzujtPCnUCUKcozsHux0rQ/3faAHGFSVb9Y.B1ntpmEhjRtru', '张三', '123342');
INSERT INTO t_user (username, PASSWORD, fullname, mobile) VALUES('lisi', '$2a$10$aFsOFzujtPCnUCUKcozsHux0rQ/3faAHGFSVb9Y.B1ntpmEhjRtru', '李四', '12sflsl3342');

CREATE TABLE `t_role` (
`id` VARCHAR(32) NOT NULL,
`role_name` VARCHAR(255) DEFAULT NULL,
`description` VARCHAR(255) DEFAULT NULL,
`create_time` DATETIME DEFAULT NULL,
`update_time` DATETIME DEFAULT NULL,
`status` CHAR(1) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `unique_role_name` (`role_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `t_role`(`id`,`role_name`,`description`,`create_time`,`update_time`,`status`) VALUES
('1','ADMIN',NULL,NULL,NULL,'');

# 联合主键
CREATE TABLE `t_user_role` (
`user_id` VARCHAR(32) NOT NULL,
`role_id` VARCHAR(32) NOT NULL,
`create_time` DATETIME DEFAULT NULL,
`creator` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `t_user_role`(`user_id`,`role_id`,`create_time`,`creator`) VALUES
('1','1',NULL,NULL);

CREATE TABLE `t_permission` (
`id` VARCHAR(32) NOT NULL,
`code` VARCHAR(32) NOT NULL COMMENT '权限标识符',
`description` VARCHAR(64) DEFAULT NULL COMMENT '描述',
`url` VARCHAR(128) DEFAULT NULL COMMENT '请求地址',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `t_permission`(`id`,`code`,`description`,`url`) VALUES ('1','p1','测试资源
1','/r/r1'),('2','p2','测试资源2','/r/r2');

# 联合主键
CREATE TABLE `t_role_permission` (
`role_id` VARCHAR(32) NOT NULL,
`permission_id` VARCHAR(32) NOT NULL,
PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `t_role_permission`(`role_id`,`permission_id`) VALUES ('1','1'),('1','2');

# 根据用户id查询用户权限
SELECT * FROM t_permission WHERE id IN (
SELECT permission_id FROM `t_role_permission` WHERE role_id IN (
	SELECT role_id FROM `t_user_role` WHERE user_id=1
)
);

# 根据用户id查询用户的角色信息
SELECT * FROM t_role WHERE id IN (
	SELECT role_id FROM `t_user_role` WHERE user_id=1
);
