/* 1. mysql -u root ¨Cp */
/* 2. mysql> source D:\java\Note\JAVA\fundamental\src\org\java\core\base\jdbc\example\schema_mysql.sql */
/* ÓÃ--×¢ÊÍ±¨´í */

create database if not exists UserDB CHARACTER SET utf8;

use UserDB;

/*mysql create Users*/
create table Users(
id int(3) primary key,
name varchar(20),
email varchar(20),
country varchar(20),
password varchar(20)
);

INSERT INTO Users (id, name, email, country, password)
VALUES (1, 'xzj', '2233835996@qq.com', 'China', 'xzj123456');
INSERT INTO Users (id, name, email, country, password)
VALUES (4, 'David', 'David@gmail.com', 'USA', 'David123456');
INSERT INTO Users (id, name, email, country, password)
VALUES (5, 'Rose', 'Rose@Apple.com', 'UK', 'Rose123456');
commit;