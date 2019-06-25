/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : zhyj

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-28 17:52:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `doc_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `doc_attachment`;
CREATE TABLE `doc_attachment` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `YWID` varchar(36) DEFAULT NULL COMMENT '各类业务主键id',
  `NAME` varchar(100) DEFAULT NULL COMMENT '文件名',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '文件类型',
  `SUFFIX` varchar(10) DEFAULT NULL COMMENT '文件后缀',
  `LOCATION` varchar(200) DEFAULT NULL COMMENT '存储地址',
  `UPLOAD_TIME` datetime DEFAULT NULL COMMENT '上传时间 ',
  `UPLOAD_USER_ID` varchar(36) DEFAULT NULL COMMENT '上传用户id',
  `UPLOAD_USER_NAME` varchar(20) DEFAULT NULL COMMENT '上传用户姓名 ',
  `DEL_FLAG` char(1) DEFAULT NULL COMMENT '删除标记[0：已删除；1：未删除] ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';