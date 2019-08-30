/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25 : Database - ssm_wudi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssm_wudi` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssm_wudi`;

/*Table structure for table `tbl_dept` */

CREATE TABLE `tbl_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) NOT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_dept` */

insert  into `tbl_dept`(`dept_id`,`dept_name`) values (1,'开发部'),(2,'事业部'),(3,'销售部'),(4,'实施部'),(5,'行政部'),(6,'研发部');

/*Table structure for table `tbl_emp` */

CREATE TABLE `tbl_emp` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(255) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `dId` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_emp` */

insert  into `tbl_emp`(`emp_id`,`emp_name`,`gender`,`email`,`d_id`) values (1,'刘大','M','liud@qq.com',1),(2,'王二','M','wanger@163.com',2),(3,'张三','M','zhangsan@128.com',3),(4,'李四','M','lisi@qq.com',4),(5,'王五','M','wangwu@qq.com',3),(6,'赵六','M','zhaoliu@qq.com',4),(7,'谢八','M','xieba@qq.com',2),(8,'a7feb0','M','a7feb0@outlook.com',5),(9,'2c3841','M','2c3841@outlook.com',2),(10,'a53fa2','M','a53fa2@outlook.com',4),(11,'f29f43','M','f29f43@outlook.com',6),(12,'ae29b4','M','ae29b4@outlook.com',4),(13,'97db95','M','97db95@outlook.com',2),(14,'b341c6','M','b341c6@outlook.com',4),(15,'bc68e7','M','bc68e7@outlook.com',3),(16,'bb5fa8','M','bb5fa8@outlook.com',4),(17,'0ed629','M','0ed629@outlook.com',2),(28,'wu','M','greatu@outlook.com',1),(29,'guorui','M','adix973@163.com',4),(30,'ui','M','asdasda2@qq.com',6);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
