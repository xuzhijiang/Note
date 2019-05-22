/* 1. mysql -u root -p */
/* 2. mysql> source D:\java\Note\JAVA\fundamental\src\org\java\core\base\jdbc\CallableStatement\create_employee.sql */

drop database if exists UserDB;

create database if not exists UserDB CHARACTER SET utf8mb4;

use UserDB;

/* -- For Oracle DB */
/* ENABLE is the default state, so leaving it out has the 
* same effect. The opposite would be to specify DISABLE, 
* in which case the constraint would not be active. */
/*
CREATE TABLE EMPLOYEE(
"EMPID"   NUMBER NOT NULL ENABLE,
"NAME"    VARCHAR2(10 BYTE) DEFAULT NULL,
"ROLE"    VARCHAR2(10 BYTE) DEFAULT NULL,
"CITY"    VARCHAR2(10 BYTE) DEFAULT NULL,
"COUNTRY" VARCHAR2(10 BYTE) DEFAULT NULL,
PRIMARY KEY ("EMPID")
);
*/

/* For Mysql DB*/
CREATE TABLE EMPLOYEE(
`EMPID` INTEGER NOT NULL AUTO_INCREMENT,
`NAME` VARCHAR(10) DEFAULT NULL,
`ROLE` VARCHAR(10) DEFAULT NULL,
`CITY` VARCHAR(10) DEFAULT NULL,
`COUNTRY` VARCHAR(10) DEFAULT NULL,
PRIMARY KEY (`EMPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/* 注意Mysql的字段名字不能用""括起来，Mysql不识别，要用``括起来*/
