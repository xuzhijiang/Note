CREATE DATABASE myemployees;

USE myemployees;

CREATE TABLE employees (
  employee_id INT(6),
  first_name VARCHAR(20),
  last_name VARCHAR(25),
  email VARCHAR(25),
  phone_number VARCHAR(20),
  job_id VARCHAR(10),
  salary DOUBLE(10,2),
  commission_pct DOUBLE(4,2),
  manager_id INT(6),
  department_id INT(4),
  hiredate DATETIME
);

CREATE TABLE departments (
  department_id INT(4),
  department_name VARCHAR(3),
  manager_id INT(6),
  location_id INT(4)
);

CREATE TABLE locations (
  location_id INT(11),
  street_address VARCHAR(40),
  postal_code VARCHAR(12),
  city VARCHAR(30),
  state_province VARCHAR(25),
  country_id VARCHAR(2)
);

CREATE TABLE jobs (
  job_id VARCHAR(10),
  job_title VARCHAR(35),
  min_salary INT(6),
  max_salary INT(6)
);

CREATE TABLE job_grades(
  grade_level VARCHAR(3),
  lowest_sal INT,
  highest_sal INT
);

INSERT INTO job_grades VALUES('A', 1000, 2999);
INSERT INTO job_grades VALUES('B', 3000, 5999);
INSERT INTO job_grades VALUES('C', 6000, 9999);
INSERT INTO job_grades VALUES('D', 10000, 14999);
INSERT INTO job_grades VALUES('E', 15000, 24999);
INSERT INTO job_grades VALUES('F', 25000, 40000);

INSERT INTO employees (`employee_id`, `first_name`, `last_name`, `email`, `phone_number`, `job_id`, `salary`, `commission_pct`, `manager_id`, `department_id`, `hiredate`)
VALUES (1,'xxx','zj','2233@qq.com','1233232343','2',2000.23,NULL,7,8,NULL),
       (2,'chens','jack','aaa3@qq.com','1233232233','8',3000.23,NULL,7,8,NULL),
       (3,'chessn','jaddck','zzza3@qq.com','1233292233','8',4500.23,0.3,7,8,NULL);

