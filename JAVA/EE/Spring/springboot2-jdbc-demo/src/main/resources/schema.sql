CREATE DATABASE IF NOT EXISTS mydb CHARACTER SET utf8mb4;

USE mydb;

CREATE TABLE IF NOT EXISTS user (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(20) NOT NULL DEFAULT '',
`gender` varchar(4) NOT NULL DEFAULT 'male',
`age` int(5) DEFAULT 0,
PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

commit;