/*
Navicat MySQL Data Transfer

Source Server         : system
Source Server Version : 100131
Source Host           : localhost:3306
Source Database       : acountbook

Target Server Type    : MYSQL
Target Server Version : 100131
File Encoding         : 65001

Date: 2018-05-15 14:53:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL,
  `date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `categoty` varchar(255) DEFAULT NULL,
  `explain` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bill
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(255) DEFAULT '',
  `email` varchar(30) NOT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `gender` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('??', '1444625953@qq.com', '18020226782', '123456', '?');
INSERT INTO `user` VALUES ('??', '1700117425@qq.com', '18020226782', '123456', '?');
INSERT INTO `user` VALUES ('??', '18020226782@qq.com', '18020226782', '123456', '?');
SET FOREIGN_KEY_CHECKS=1;
