/* 1. mysql -u root ¨Cp */
/* 2. mysql> source D:\java\Note\JAVA\fundamental\src\org\java\core\base\jdbc\BatchProcessing\schema_mysql.sql */
/* ÓÃ--×¢ÊÍ±¨´í */

drop database if exists UserDB;

create database if not exists UserDB CHARACTER SET utf8mb4;

use UserDB;

/* --MySQL DB */
CREATE TABLE `Employee` (
  `empId` int(10) unsigned NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
--Oracle DB
CREATE TABLE Employee (
  empId NUMBER NOT NULL,
  name varchar2(10) DEFAULT NULL,
  PRIMARY KEY (empId)
);
*/

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