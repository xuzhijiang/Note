USE mydb;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `departmentName` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO department (departmentName) VALUES('department1');
INSERT INTO department (departmentName)  VALUES('department2');


-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `lastName` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `gender` INT(2) DEFAULT NULL,
  `d_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO employee (lastName, email, gender, d_id) VALUES('aaa', 'aaa.@qq.com', '1', '2');
INSERT INTO employee (lastName, email, gender, d_id) VALUES('bbb', 'bbb.@qq.com', '0', '1');
INSERT INTO employee (lastName, email, gender, d_id) VALUES('ccc', 'ccc.@qq.com', '1', '1');