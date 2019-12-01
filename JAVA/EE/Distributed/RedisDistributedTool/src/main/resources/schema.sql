USE exercise;
CREATE TABLE distributed_lock (
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
    lock_name VARCHAR(20),
    PRIMARY KEY(id)
)ENGINE=INNODB CHARSET=utf8;