/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 5.7.16-log : Database - myshop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `myshop`;

/*Table structure for table `tb_content` */

DROP TABLE IF EXISTS `tb_content`;

CREATE TABLE `tb_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL,
  `sub_title` varchar(30) NOT NULL,
  `title_desc` varchar(60) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `pic` varchar(200) DEFAULT NULL,
  `pic2` varchar(200) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_content` */

insert  into `tb_content`(`id`,`title`,`sub_title`,`title_desc`,`category_id`,`url`,`pic`,`pic2`,`content`,`created`,`updated`) values 
(1,'标题1','子标题1','标题描述1',1,'http://localhost:8080/api/content/article1','http://localhost:8080/static/upload/3e3ce9e3-fd9c-4297-a955-bd6dade7bab6.jpg','http://localhost:8080/static/upload/d09bf530-41a7-47a8-ac3f-99e49b0260cc.jpg','<h2>jlkajfldkaj</h2><p>lkajfkl;a</p><p><b>jklajfdlk</b></p><p>jlajfkl</p><p>jl;j;lkaf</p><p><img src=\"http://localhost:8080/static/upload/c3c95941-7906-4642-9b6c-257cfd78297d.jpg\" style=\"max-width:100%;\"><br></p><p>jfsjlfk</p><p>jlfkajflka</p>','2020-02-15','2020-02-16'),
(2,'标题22','子标题22','标题描述22',2,'http://localhost:8080/api/content/article2','http://localhost:8080/static/upload/f92a71f7-8c74-4d29-aeb3-a156155973f3.jpg','http://localhost:8080/static/upload/0307966a-caff-4246-8fa2-a89d89ddb8bb.jpg','<p>lkadjfkl;ajfkl;</p><p>lkajfkl</p><p>jlkafjdl;</p><p><img src=\"http://localhost:8080/static/upload/18de8b0b-32fd-4236-86be-cc725f80cfd9.png\" style=\"max-width:100%;\"><br></p><p><br></p>','2020-02-15','2020-02-17');

/*Table structure for table `tb_content_category` */

DROP TABLE IF EXISTS `tb_content_category`;

CREATE TABLE `tb_content_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `sort_order` int(11) NOT NULL,
  `is_parent` tinyint(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_content_category` */

insert  into `tb_content_category`(`id`,`name`,`status`,`sort_order`,`is_parent`,`parent_id`,`created`,`updated`) values 
(1,'大学科目',1,0,1,0,'2020-02-15','2020-02-17'),
(2,'计算机',1,1,1,1,'2020-02-15','2020-02-15'),
(3,'化学',1,1,1,1,'2020-02-15','2020-02-15'),
(4,'操作系统',1,0,0,2,'2020-02-17','2020-01-28'),
(5,'仪器化学',1,1,1,3,'2020-02-17','2020-02-06'),
(6,'网络',1,1,1,2,'2020-02-17','2020-02-13'),
(7,'液相色谱仪',1,1,0,5,'2020-02-17','2020-01-28'),
(10,'电影',1,0,1,0,'2020-02-17','2020-02-17'),
(11,'古装',1,1,1,10,'2020-02-17','2020-02-17'),
(12,'明朝',1,0,0,11,'2020-02-17','2020-02-17');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_user` */
/*e10adc3949ba59abbe56e057f20f883e	==>	123456(明文密码) */

insert  into `tb_user`(`id`,`username`,`password`,`phone`,`email`,`created`,`updated`) values 
(5,'xuzhijiang01','e10adc3949ba59abbe56e057f20f883e','15888888889','xxx@qq.com','2020-02-13','2019-09-15'),
(8,'xuzhijiang02','e10adc3949ba59abbe56e057f20f883e','13798282944','xxxx@qq.com','2020-02-15','2020-02-15'),
(9,'xuzhijiang03','e10adc3949ba59abbe56e057f20f883e','13798282944','xxxxx@qq.com','2020-02-15','2020-02-18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

