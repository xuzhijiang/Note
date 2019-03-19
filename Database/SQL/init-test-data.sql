DROP DATABASE xzj;

CREATE DATABASE IF NOT EXISTS xzj;

USE xzj;

DROP TABLE IF EXISTS classes;
DROP TABLE IF EXISTS students;

CREATE TABLE classes (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE students (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`class_id` BIGINT NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`gender` VARCHAR(1) NOT NULL,
	`score` INT NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO classes(`id`, `name`) VALUES(1, 'ClassOne');
INSERT INTO classes(id, name) VALUES(2, 'ClassTwo');
INSERT INTO classes(`id`, `name`) VALUES(3, 'ClassThree');
INSERT INTO classes(id, name) VALUES(4, 'ClassFour');

INSERT INTO students (id, class_id, name, gender, score) VALUES (1, 1, 'ming', 'M', 90);
INSERT INTO students (id, class_id, name, gender, score) VALUES (2, 1, 'hong', 'F', 95);
INSERT INTO students (id, class_id, name, gender, score) VALUES (3, 1, 'jun', 'M', 88);
INSERT INTO students (id, class_id, name, gender, score) VALUES (4, 1, 'mi', 'F', 73);
INSERT INTO students (id, class_id, name, gender, score) VALUES (5, 2, 'bai', 'F', 81);
INSERT INTO students (id, class_id, name, gender, score) VALUES (6, 2, 'bing', 'M', 55);
INSERT INTO students (id, class_id, name, gender, score) VALUES (7, 2, 'lin', 'M', 85);
INSERT INTO students (id, class_id, name, gender, score) VALUES (8, 3, 'xin', 'F', 91);
INSERT INTO students (id, class_id, name, gender, score) VALUES (9, 3, 'wang', 'M', 89);
INSERT INTO students (id, class_id, name, gender, score) VALUES (10, 3, 'li', 'F', 85);
INSERT INTO students (id, class_id, name, gender, score) VALUES (11, 5, 'jiang', 'M', 79);

-- OK
SELECT 'ok' as 'result:';