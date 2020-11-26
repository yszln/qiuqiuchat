/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 80021
Source Host           : 127.0.0.1:3306
Source Database       : qiuqiu

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-11-10 22:23:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_friend
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberId` int DEFAULT NULL,
  `friendId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_friend
-- ----------------------------
INSERT INTO `tb_friend` VALUES ('1', '3', '5');
INSERT INTO `tb_friend` VALUES ('2', '3', '23404');
INSERT INTO `tb_friend` VALUES ('3', '5', '3');
INSERT INTO `tb_friend` VALUES ('4', '23404', '3');
INSERT INTO `tb_friend` VALUES ('5', '3', '6');
INSERT INTO `tb_friend` VALUES ('6', '6', '3');
INSERT INTO `tb_friend` VALUES ('7', '5', '6');
INSERT INTO `tb_friend` VALUES ('8', '6', '5');

-- ----------------------------
-- Table structure for tb_login
-- ----------------------------
DROP TABLE IF EXISTS `tb_login`;
CREATE TABLE `tb_login` (
  `memberId` int NOT NULL,
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `time` bigint DEFAULT '0' COMMENT '登陆时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_login
-- ----------------------------
INSERT INTO `tb_login` VALUES ('6', 'f7df71c4db30ca1dcd7e6fe79403f1b6', '1600328051');
INSERT INTO `tb_login` VALUES ('3', 'fdbce09a08c23ae9ce9967578fb6af23', '1600352782');
INSERT INTO `tb_login` VALUES ('5', 'cca9f0621f4df50390194d66049182cb', '1600353200');

-- ----------------------------
-- Table structure for tb_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_member`;
CREATE TABLE `tb_member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23405 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_member
-- ----------------------------
INSERT INTO `tb_member` VALUES ('3', 'yszln', 'e10adc3949ba59abbe56e057f20f883e', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3952673312,883101468&fm=26&gp=0.jpg', '1');
INSERT INTO `tb_member` VALUES ('5', 'xiaopang', 'e10adc3949ba59abbe56e057f20f883e', 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=791528510,3971562185&fm=26&gp=0.jpg', '1');
INSERT INTO `tb_member` VALUES ('6', 'xiaoshou', 'e10adc3949ba59abbe56e057f20f883e', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1909883129,774311030&fm=26&gp=0.jpg', '1');

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT '文字消息内容',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果是文件类型的消息，存储url',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT ' 消息类型,0：系统推送，1：普通消息，2：语音消息，3：图片消息，4：视频消息，5：其他文件消息',
  `sourceId` int NOT NULL DEFAULT '0' COMMENT '消息来源id，默认0，系统消息',
  `receiveId` int NOT NULL,
  `time` bigint DEFAULT NULL COMMENT '创建时间',
  `isPush` tinyint NOT NULL DEFAULT '0' COMMENT '是否已经推送过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=517 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
INSERT INTO `tb_message` VALUES ('501', '1', '', '1', '0', '5', '1600353301', '0');
INSERT INTO `tb_message` VALUES ('502', '', 'http://yszln.iask.in/upload/1602406211473.amr', '2', '0', '5', '1600353307', '0');
INSERT INTO `tb_message` VALUES ('503', '', 'http://yszln.iask.in/upload/1600793143739.amr', '2', '0', '5', '1600353314', '0');
INSERT INTO `tb_message` VALUES ('504', '1', '', '1', '0', '3', '1600396526', '0');
INSERT INTO `tb_message` VALUES ('505', '2', '', '1', '0', '6', '1600396541', '0');
INSERT INTO `tb_message` VALUES ('506', '', 'http://yszln.iask.in/upload/1602407430593.amr', '2', '0', '6', '1600411769', '0');
INSERT INTO `tb_message` VALUES ('507', '2', '', '1', '0', '6', '1600411790', '0');
INSERT INTO `tb_message` VALUES ('508', '1', '', '1', '0', '6', '1600417067', '0');
INSERT INTO `tb_message` VALUES ('509', '212', '', '1', '0', '6', '1600417068', '0');
INSERT INTO `tb_message` VALUES ('510', '21', '', '1', '0', '6', '1600417068', '0');
INSERT INTO `tb_message` VALUES ('511', '21212424', '', '1', '0', '6', '1600417070', '0');
INSERT INTO `tb_message` VALUES ('512', '。', '', '1', '0', '6', '1600420314', '0');
INSERT INTO `tb_message` VALUES ('513', '1', '', '1', '0', '5', '1600424925', '0');
INSERT INTO `tb_message` VALUES ('514', '1', '', '1', '0', '6', '1600424928', '0');
INSERT INTO `tb_message` VALUES ('515', '', 'http://yszln.iask.in/upload/1601976007461.amr', '2', '0', '6', '1600425067', '0');
INSERT INTO `tb_message` VALUES ('516', '', 'http://yszln.iask.in/upload/1600441365027.amr', '2', '0', '6', '1600425073', '0');
