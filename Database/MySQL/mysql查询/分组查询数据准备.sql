USE myemployees;

CREATE TABLE beauty(
  id INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(10),
  sex VARCHAR(2),
  boyfriend_id INT,
  PRIMARY KEY (id)
);

CREATE TABLE boys(
  id INT NOT NULL AUTO_INCREMENT,
  boyName VARCHAR(10),
  userCP INT,
  PRIMARY KEY (id)
);

INSERT INTO beauty (NAME, sex, boyfriend_id) VALUES 
  ('刘岩', '女', 8),
  ('苍老师', '女', 9),
  ('Angelababy', '女', 3),
  ('热巴', '女', 2),
  ('周冬雨', '女', 9),
  ('周芷若', '女', 1),
  ('岳灵珊', '女', 9),
  ('小昭', '女', 1),
  ('双儿', '女', 9),
  ('夏雪', '女', 9),
  ('赵敏', '女', 1);
  
INSERT INTO boys(boyName, userCP) VALUES
  ('张无忌', 100),
  ('鹿晗', 800),
  ('黄晓明', 50),
  ('段誉', 300);
