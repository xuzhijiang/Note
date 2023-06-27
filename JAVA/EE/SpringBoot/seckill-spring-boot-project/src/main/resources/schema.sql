USE mydb;

drop table if exists `product`;
drop table if exists `seckillorder`;

CREATE TABLE `product`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`price` DECIMAL(10,2) DEFAULT NULL,
	`stock` INT(11) DEFAULT NULL,
	`pic` VARCHAR(255) DEFAULT '',
	PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO product (id,NAME,price,stock,pic) VALUES
	(1, '华为P40', 5000, 100, 'images/123456'),
	(6, '华为P30', 3000, 100, 'images/123456');

CREATE TABLE `seckillorder`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`productId` INT(11) NOT NULL,
	`amount` DECIMAL(10,2) DEFAULT NULL,
	PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=59624 DEFAULT CHARSET=utf8;
