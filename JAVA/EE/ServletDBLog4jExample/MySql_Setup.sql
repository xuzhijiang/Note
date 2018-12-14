/* 1. mysql -u root –p */
/* 2. mysql> source D:\java\Note\JAVA\EE\ServletDBLog4jExample\MySql_Setup.sql */
/* 用--注释报错 */
/* login with root to create user, DB and table and provide grants */

/* The MySQL server is running with the --skip-grant-tables 
option so it cannot execute this statement ，通过FLUSH PRIVILEGES解决这个问题*/

FLUSH PRIVILEGES;

create user 'xuzhijiang'@'localhost' identified by 'password';

grant all on *.* to 'xuzhijiang'@'localhost' identified by 'password';

FLUSH PRIVILEGES;

drop database if exists UserDB;

create database UserDB;

use UserDB;

CREATE TABLE `Users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(20) NOT NULL DEFAULT '',
  `country` varchar(20) DEFAULT 'CHINA',
  `password` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

