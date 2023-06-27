USE learn;

DROP TABLE IF EXISTS zg_account;
DROP TABLE IF EXISTS zg_message;

CREATE TABLE zg_account(
	user_id VARCHAR(50) NOT NULL,
	amount BIGINT(11),
	update_time DATETIME DEFAULT NOW()
)ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE zg_message(
	message_id VARCHAR(50) NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	amount BIGINT(11),
	state VARCHAR(10),
	update_time DATETIME DEFAULT NOW()
)ENGINE=INNODB DEFAULT CHARSET=utf8;

-- 初始化支付宝中的余额
INSERT INTO zg_account(user_id, amount, update_time) VALUES("user_00001", 5000, NOW());