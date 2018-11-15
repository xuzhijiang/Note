/* 1. mysql -u root ¨Cp */
/* 2. mysql> source D:\java\Note\JAVA\fundamental\src\org\java\core\base\jdbc\StatementAndPreparedStatement\schema_mysql.sql */
/* ÓÃ--×¢ÊÍ±¨´í */

drop database if exists UserDB;

create database if not exists UserDB CHARACTER SET utf8mb4;

use UserDB;

CREATE TABLE `Users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(20) NOT NULL DEFAULT '',
  `country` varchar(20) DEFAULT 'USA',
  `password` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Users` (`id`, `name`, `email`, `country`, `password`)
VALUES (1, 'xzj', 'xzj@qq.com', 'China', 'xzj123'),
(4, 'David', 'david@gmail.com', 'USA', 'david123'),
(5, 'Raman', 'raman@google.com', 'UK', 'raman123');
commit;