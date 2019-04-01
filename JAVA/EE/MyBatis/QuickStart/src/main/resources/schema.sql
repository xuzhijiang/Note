CREATE DATABASE IF NOT EXISTS mydb CHARACTER SET utf8;

USE mydb;

DROP TABLE user IF EXISTS;

CREATE TABLE user(
`id` int,
`name` varchar(255) not NULL,
`age` int,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;