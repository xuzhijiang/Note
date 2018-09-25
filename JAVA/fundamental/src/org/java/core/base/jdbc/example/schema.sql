--mysql create table
create table Users(
id int(3) primary key,
name varchar(20),
email varchar(20),
country varchar(20),
password varchar(20)
);

--oracle create table
create table Users(
id number(3) primary key,
name varchar2(20),
email varchar2(20),
country varchar2(20),
password varchar2(20)
);

--insert rows
INSERT INTO Users (id, name, email, country, password)
VALUES (1, 'xzj', '2233835996@qq.com', 'China', 'xzj123456');
INSERT INTO Users (id, name, email, country, password)
VALUES (4, 'David', 'David@gmail.com', 'USA', 'David123456');
INSERT INTO Users (id, name, email, country, password)
VALUES (5, 'Rose', 'Rose@Apple.com', 'UK', 'Rose123456');
commit;