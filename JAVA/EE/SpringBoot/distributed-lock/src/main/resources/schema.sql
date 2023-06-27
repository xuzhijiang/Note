USE mydb;
CREATE TABLE distributed_lock (
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
    lock_name VARCHAR(20),
    PRIMARY KEY(id)
)ENGINE=INNODB CHARSET=utf8;

INSERT INTO distributed_lock (lock_name) VALUES ('lock1');
INSERT INTO distributed_lock (lock_name) VALUES ('lock2');
INSERT INTO distributed_lock (lock_name) VALUES ('lock3');