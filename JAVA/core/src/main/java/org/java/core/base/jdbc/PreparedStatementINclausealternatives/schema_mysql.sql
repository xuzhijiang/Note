/* 1. mysql -u root ¨Cp */
/* 2. mysql> source D:\java\Note\JAVA\fundamental\src\org\java\core\base\jdbc\PreparedStatementINclausealternatives\schema_mysql.sql */
/* ÓÃ--×¢ÊÍ±¨´í */

drop database if exists UserDB;

create database if not exists UserDB CHARACTER SET utf8mb4;

use UserDB;

CREATE TABLE `Employee` (
`empid` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(20) NOT NULL DEFAULT '',
PRIMARY KEY (`empid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Employee` (`empid`, `name`)
VALUES (1, 'xzj'),
(2, 'xzj2'),
(3, 'xzj3'),
(4, 'xzj4'),
(5, 'xzj5'),
(6, 'xzj6'),
(7, 'xzj7'),
(8, 'xzj8'),
(9, 'xzj9'),
(10, 'xzj10');
commit;