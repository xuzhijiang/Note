USE mydb;

DROP TABLE employee;

CREATE TABLE employee (
	last_name VARCHAR(30) PRIMARY KEY NOT NULL,
	email VARCHAR(30),
	gender VARCHAR(10),
	dept_id INT
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO employee (last_name,email,gender,dept_id) VALUES
    ('aa','aa@qq.com','male',20),
    ('bb','bb@qq.com','female',30);