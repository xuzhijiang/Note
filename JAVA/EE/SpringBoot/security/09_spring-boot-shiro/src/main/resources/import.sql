DROP DATABASE shiro;
CREATE DATABASE IF NOT EXISTS shiro;
USE shiro;

CREATE TABLE `user`(
	`uid` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL,
	`name` VARCHAR(20),
	`password` VARCHAR(255),
	`salt` VARCHAR(255),
	`state` TINYINT,
	`perms` VARCHAR(255)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO `user` (`username`,`name`,`password`,`salt`,`state`,`perms`) VALUES ('admin', '昵称1', '038bdaf98f2037b31f1e75b5b4c9b26e', '', 1,'user:add,user:view,user:del');
INSERT INTO `user` (`username`,`name`,`password`,`salt`,`state`,`perms`) VALUES ('user', '昵称2', '098d2c478e9c11555ce2823231e02ec1', '', 0,'user:view');
