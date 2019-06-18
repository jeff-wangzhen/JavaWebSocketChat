/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : db_javawebsocketchat

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2019-05-15 14:20:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
create database db_JavaWebSocketChat;
use db_JavaWebSocketChat;
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `portrait` varchar(255) DEFAULT NULL,
  `gender` enum('ÄÐ','Å®','ÆäËû') DEFAULT NULL,
  `logined` bit(1) DEFAULT b'0',
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `signature` varchar(200) DEFAULT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `interests` varchar(2550) DEFAULT NULL,
  `weibo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
