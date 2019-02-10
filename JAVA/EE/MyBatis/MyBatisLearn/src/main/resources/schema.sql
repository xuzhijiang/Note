CREATE DATABASE IF NOT EXISTS mydb CHARACTER SET utf8;

USE mydb;

drop table user;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '',
  `dept` varchar(254) NOT NULL DEFAULT '',
  `website` varchar(254) DEFAULT '',
  `phone` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xzj', 'Tech', 'http://www.xzj.com', '13800009090');