/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : zhyj

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-10-09 11:45:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_business_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log`;
CREATE TABLE `sys_business_log` (
  `id` varchar(36) NOT NULL,
  `type` varchar(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `operate_by` varchar(36) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务日志';

-- ----------------------------
-- Records of sys_business_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dic_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_group`;
CREATE TABLE `sys_dic_group` (
  `id` varchar(36) NOT NULL,
  `dic_name` varchar(50) DEFAULT NULL,
  `dic_code` varchar(50) DEFAULT NULL,
  `type` varchar(36) DEFAULT NULL COMMENT '0:列表，1:树型',
  `orders` int(11) DEFAULT NULL,
  `status` char(1) DEFAULT NULL COMMENT '1:启用,0:禁用',
  `del_flag` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项';

-- ----------------------------
-- Records of sys_dic_group
-- ----------------------------
INSERT INTO `sys_dic_group` VALUES ('0eb5387c-1aed-403e-9b1c-ab6793eca12a', '知识类型', 'dicKnowledgeType', '0', '3', '1', '1', '2018-09-21 15:14:53', '2018-09-21 15:14:53', null, null);
INSERT INTO `sys_dic_group` VALUES ('1', '角色类型', 'roleType', '0', '1', '1', '1', '2018-08-31 14:45:13', '2018-08-31 14:45:18', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_dic_group` VALUES ('15eba262-88bf-453a-8711-d7b934c33f83', '事件类型', 'dicEventType', '1', '2', '1', '1', '2018-09-21 15:11:44', '2018-09-21 15:11:44', null, null);

-- ----------------------------
-- Table structure for `sys_dic_group_items`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_group_items`;
CREATE TABLE `sys_dic_group_items` (
  `id` varchar(36) NOT NULL,
  `dic_code` varchar(50) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `type` char(1) DEFAULT NULL COMMENT '冗余字段，标明列表或者树型结构',
  `orders` int(11) DEFAULT NULL,
  `item_code` varchar(100) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dic_group_items
-- ----------------------------
INSERT INTO `sys_dic_group_items` VALUES ('0e912806-5bac-412f-b5c1-57713f9682ec', 'dicEventType', '地质灾害类', '-1', '1', '2', null, '1', '2018-09-21 15:20:42', '2018-09-21 15:20:42', null, null);
INSERT INTO `sys_dic_group_items` VALUES ('0f390985-512f-4549-b890-acaaf61b5148', 'dicKnowledgeType', '高温类', '-1', '0', '1', null, '1', '2018-09-21 15:22:04', '2018-09-21 15:22:04', null, null);
INSERT INTO `sys_dic_group_items` VALUES ('1', 'roleType', '超级管理员', '-1', '0', '1', '100', '1', '2018-08-31 14:47:16', '2018-08-31 14:47:16', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_dic_group_items` VALUES ('2', 'roleType', '二级管理员', '-1', '0', '2', '200', '1', '2018-08-31 14:47:16', '2018-08-31 14:47:16', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_dic_group_items` VALUES ('3', 'roleType', '普通操作员', '-1', '0', '3', '300', '1', '2018-08-31 14:47:16', '2018-08-31 14:47:16', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_dic_group_items` VALUES ('57a10841-9699-45a0-b641-6e27368dd314', 'dicEventType', '高温突发事件', 'faa0683b-f503-47b2-9646-ae631498ac55', '1', '1', null, '1', '2018-09-21 15:20:21', '2018-09-21 15:20:21', null, null);
INSERT INTO `sys_dic_group_items` VALUES ('71f1ab9a-51f7-439e-b933-400c862ffc1e', 'dicKnowledgeType', '地震类', '-1', '0', '2', null, '1', '2018-09-21 15:22:20', '2018-09-21 15:22:20', null, null);
INSERT INTO `sys_dic_group_items` VALUES ('f5cec9be-9fe2-4ada-a09e-a9d54f2c8a2b', 'dicEventType', '地震灾害事件', '0e912806-5bac-412f-b5c1-57713f9682ec', '1', '1', null, '1', '2018-09-21 15:21:36', '2018-09-21 15:21:36', null, null);
INSERT INTO `sys_dic_group_items` VALUES ('faa0683b-f503-47b2-9646-ae631498ac55', 'dicEventType', '生活灾难类', '-1', '1', '1', null, '1', '2018-09-21 15:19:21', '2018-09-21 15:19:21', null, null);

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL,
  `menu_name` varchar(50) DEFAULT NULL,
  `permission` varchar(50) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `redirect` varchar(128) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `component` varchar(100) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `type` char(1) DEFAULT NULL COMMENT '0:菜单,1:按钮',
  `del_flag` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_k_u_sys_authority` (`permission`,`menu_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', null, '-1', '/settings', '/settings/user', 'setting', 'views/layout/Layout', '1', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('10', '编辑', 'sys_user_edit', '2', null, null, null, null, '2', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('11', '删除', 'sys_user_del', '2', null, null, null, null, '3', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('12', '赋角色', 'sys_user_role', '2', null, null, null, null, '4', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('13', '启用/禁用', 'sys_user_enable', '2', null, null, null, null, '5', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('14', '重置密码', 'sys_user_reset', '2', null, null, null, null, '6', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('15', '添加', 'sys_role_add', '3', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('16', '编辑', 'sys_role_edit', '3', null, null, null, null, '2', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('17', '删除', 'sys_role_del', '3', null, null, null, null, '3', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('18', '赋权', 'sys_role_perm', '3', null, null, null, null, '4', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('19', '启用/禁用', 'sys_role_enable', '3', null, null, null, null, '5', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('1f25a2a3-03bc-4c35-a9f6-8b649c382ac9', '测试', null, '1', null, '123', null, null, '1', '0', '0', '2018-09-11 16:55:58', '2018-09-11 17:36:43', null, null);
INSERT INTO `sys_menu` VALUES ('2', '用户管理', null, '1', 'user', '', 'setting-user', 'views/settings/User', '1', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('20', '添加', 'sys_menu_add', '4', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('21', '编辑', 'sys_menu_edit', '4', null, null, null, null, '2', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('22', '删除', 'sys_menu_del', '4', null, null, null, null, '3', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('23', '配置', 'sys_menuAssign_edit', '5', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('24', '添加', 'sys_org_add', '6', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('25', '编辑', 'sys_org_edit', '6', null, null, null, null, '2', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('26', '删除', 'sys_org_del', '6', null, null, null, null, '3', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('27', '添加', 'sys_dic_add', '7', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-09-03 17:46:00', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('28', '编辑', 'sys_dic_edit', '7', null, null, null, null, '2', '1', '1', '2018-08-31 14:55:03', '2018-09-03 17:46:10', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('29', '删除', 'sys_dic_del', '7', null, null, null, null, '3', '1', '1', '2018-08-31 14:55:03', '2018-09-03 17:46:19', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('3', '角色管理', null, '1', 'role', null, 'setting-role', 'views/settings/Role', '2', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('30', '启用/禁用', 'sys_dic_enable', '7', null, null, null, null, '4', '1', '1', '2018-08-31 14:55:03', '2018-09-03 17:46:26', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('4', '菜单管理', null, '1', 'menu', null, 'setting-menu', 'views/settings/Menu', '3', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('5', '菜单配置', null, '1', 'menuAssign', null, 'setting-menu-assign', 'views/settings/MenuAssign', '4', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('6', '机构管理', null, '1', 'org', null, 'setting-org', 'views/settings/Org', '5', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('7', '字典管理', null, '1', 'dictionary', null, 'setting-dictionary', 'views/settings/Dictionary', '6', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('8', '日志管理', null, '1', 'log', null, 'setting-log', 'views/settings/Log', '7', '0', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('9', '添加', 'sys_user_add', '2', null, null, null, null, '1', '1', '1', '2018-08-31 14:55:03', '2018-08-31 14:55:03', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_menu` VALUES ('c3252495-859c-4c12-b103-0853ec07d26d', '测试', null, '1', 'test', null, null, 'views/layout/Layout', '1', '0', '0', '2018-09-11 16:57:57', '2018-09-11 17:39:02', null, null);
INSERT INTO `sys_menu` VALUES ('e2cd5360-8de9-4a40-8c8e-392198b4ef1b', '测试', null, '-1', null, null, null, null, '123', '1', '0', '2018-09-03 17:15:55', '2018-09-03 17:18:46', null, null);
INSERT INTO `sys_menu` VALUES ('ff80808165ebdb820165ebc13c740037', '测试菜单', '', '-1', null, null, '', null, '999', '0', '0', '2018-09-18 16:17:14', '2018-09-21 11:21:46', null, null);
INSERT INTO `sys_menu` VALUES ('ff80808165ebdb820165ebd02ff60038', '测试管理', '', '1', null, null, '', null, '999', '1', '0', '2018-09-18 16:33:34', '2018-09-21 11:22:02', null, null);
INSERT INTO `sys_menu` VALUES ('ff80808165ebdb820165ebd3ff250079', '查看', null, '2', null, null, '', null, '999', '1', '0', '2018-09-18 16:37:44', '2018-09-18 16:39:07', null, null);

-- ----------------------------
-- Table structure for `sys_org`
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` varchar(36) NOT NULL,
  `org_name` varchar(100) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `short_name` varchar(50) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '部门1', '-1', '100', null, '1', null, '部门1', '1', '2018-08-31 14:52:35', '2018-08-31 14:52:38', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_org` VALUES ('82f932d1-0fd7-480e-a6f5-9d2ffabf22c1', '测试1', '-1', null, null, '2', null, null, '0', '2018-09-03 18:18:33', '2018-09-03 18:27:03', null, null);
INSERT INTO `sys_org` VALUES ('98702c0d-3447-4c4a-99a7-d04b8b9f5f0d', '部门2', '-1', null, null, '4', null, null, '1', '2018-09-11 17:39:38', '2018-09-11 17:42:20', null, null);
INSERT INTO `sys_org` VALUES ('b9b3917d-1ab3-47bc-8e21-7876847f22f5', '部门2-1', '98702c0d-3447-4c4a-99a7-d04b8b9f5f0d', null, null, '1', null, null, '1', '2018-09-11 17:42:48', '2018-09-11 17:42:48', null, null);
INSERT INTO `sys_org` VALUES ('e0f2bc09-0eab-4f80-a3f2-ae65be7c00dc', '测试', '-1', null, null, '3', null, null, '0', '2018-09-03 18:19:03', '2018-09-03 18:27:11', null, null);
INSERT INTO `sys_org` VALUES ('ff80808165ebdb820165ea69111a0000', '测试新增2-1-1', 'b9b3917d-1ab3-47bc-8e21-7876847f22f5', null, '', '999', '', '', '0', '2018-09-18 10:01:19', '2018-09-21 11:17:44', null, null);
INSERT INTO `sys_org` VALUES ('ff80808165ebdb820165eace99d90009', '111', 'ff80808165ebdb820165ea69111a0000', null, null, '999', null, null, '0', '2018-09-18 11:52:13', '2018-09-18 11:52:18', null, null);
INSERT INTO `sys_org` VALUES ('ff80808165ebdb820165ead241d9000a', '1111111111', 'ff80808165ebdb820165ea69111a0000', null, null, '9994', null, null, '0', '2018-09-18 11:56:13', '2018-09-21 11:17:37', null, null);

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `role_code` varchar(50) DEFAULT NULL,
  `role_type` varchar(36) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL COMMENT '0:正常,1:删除',
  `status` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `is_super` char(1) DEFAULT '0' COMMENT '是否超级管理员，1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER_ADMIN', '1', '超级管理员角色', '1', '1', '1', '2018-08-31 14:50:11', '2018-08-31 14:50:15', 'aaaaaa', 'aaaaaa', '1');
INSERT INTO `sys_role` VALUES ('2', '超级管理员2', 'ADMIN', '2', '管理员', '2', '1', '0', '2018-09-03 16:15:12', '2018-09-20 20:16:40', 'aaaaaa', 'aaaaaa', '0');
INSERT INTO `sys_role` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', 'test', 'TEST', '3', '', null, '1', '1', '2018-09-11 17:24:41', '2018-09-20 20:17:02', null, null, '0');
INSERT INTO `sys_role` VALUES ('8d3e0098-addf-4edb-9d18-620a4267a318', 'test', 'TEST', '3', '', null, '0', '1', '2018-09-11 16:50:44', '2018-09-11 16:54:26', null, null, '0');
INSERT INTO `sys_role` VALUES ('b8fcf7ad-f360-47da-ac4f-48f1e1195c2c', 'Test', 'TEST', '3', 'test', null, '0', '1', '2018-09-11 16:52:40', '2018-09-11 16:54:29', null, null, '0');
INSERT INTO `sys_role` VALUES ('edd3f8e8-9384-4383-93a9-40bbbf5c5d87', '啊啊', 'TEST', '2', '啊啊', null, '0', '1', '2018-09-03 16:55:21', '2018-09-03 16:57:14', null, null, '0');
INSERT INTO `sys_role` VALUES ('ff80808165ebdb820165eb8dd430002d', '管理员', 'ASI', '2', '管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员管理员', null, '1', '1', '2018-09-18 15:21:05', '2018-09-20 20:16:48', null, null, '0');
INSERT INTO `sys_role` VALUES ('ff80808165ebdb820165eba5c8d40035', '操作员', 'ASD', '3', '', null, '0', '0', '2018-09-18 15:47:15', '2018-09-18 16:13:59', null, null, '0');
INSERT INTO `sys_role` VALUES ('ff80808165ebdb820165ec0f916b0117', '执行开发', 'ABC', '3', '测试', null, '1', '1', '2018-09-18 17:42:48', '2018-09-18 17:42:48', null, null, '0');
INSERT INTO `sys_role` VALUES ('ff80808165ebdb820165ec0ff3670118', '123', 'A', '1', '测试', null, '1', '1', '2018-09-18 17:43:13', '2018-09-18 17:43:13', null, null, '0');
INSERT INTO `sys_role` VALUES ('ff80808165ebdb820165ef9ab45d0119', '去问驱蚊器强强强强强强哇哇哇哇', 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', '3', '去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇去问驱蚊器强强强强强强哇哇哇哇', null, '0', '1', '2018-09-19 10:13:38', '2018-09-21 11:24:00', null, null, '0');

-- ----------------------------
-- Table structure for `sys_role_menus`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menus`;
CREATE TABLE `sys_role_menus` (
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `fk_r_s_r_a_r_sys_authority` (`menu_id`) USING BTREE,
  CONSTRAINT `sys_role_menus_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `sys_role_menus_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- ----------------------------
-- Records of sys_role_menus
-- ----------------------------
INSERT INTO `sys_role_menus` VALUES ('1', '1');
INSERT INTO `sys_role_menus` VALUES ('2', '1');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '1');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '1');
INSERT INTO `sys_role_menus` VALUES ('1', '10');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '10');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '10');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eba5c8d40035', '10');
INSERT INTO `sys_role_menus` VALUES ('1', '11');
INSERT INTO `sys_role_menus` VALUES ('2', '11');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '11');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '11');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eba5c8d40035', '11');
INSERT INTO `sys_role_menus` VALUES ('1', '12');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '12');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '12');
INSERT INTO `sys_role_menus` VALUES ('1', '13');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '13');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '13');
INSERT INTO `sys_role_menus` VALUES ('1', '14');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '14');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '14');
INSERT INTO `sys_role_menus` VALUES ('1', '15');
INSERT INTO `sys_role_menus` VALUES ('2', '15');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '15');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '15');
INSERT INTO `sys_role_menus` VALUES ('1', '16');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '16');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '16');
INSERT INTO `sys_role_menus` VALUES ('1', '17');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '17');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '17');
INSERT INTO `sys_role_menus` VALUES ('1', '18');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '18');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '18');
INSERT INTO `sys_role_menus` VALUES ('1', '19');
INSERT INTO `sys_role_menus` VALUES ('1', '2');
INSERT INTO `sys_role_menus` VALUES ('2', '2');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '2');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '2');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eba5c8d40035', '2');
INSERT INTO `sys_role_menus` VALUES ('1', '20');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '20');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '20');
INSERT INTO `sys_role_menus` VALUES ('1', '21');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '21');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '21');
INSERT INTO `sys_role_menus` VALUES ('1', '22');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '22');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '22');
INSERT INTO `sys_role_menus` VALUES ('1', '23');
INSERT INTO `sys_role_menus` VALUES ('2', '23');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '23');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '23');
INSERT INTO `sys_role_menus` VALUES ('1', '24');
INSERT INTO `sys_role_menus` VALUES ('2', '24');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '24');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '24');
INSERT INTO `sys_role_menus` VALUES ('1', '25');
INSERT INTO `sys_role_menus` VALUES ('2', '25');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '25');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '25');
INSERT INTO `sys_role_menus` VALUES ('1', '26');
INSERT INTO `sys_role_menus` VALUES ('2', '26');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '26');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '26');
INSERT INTO `sys_role_menus` VALUES ('1', '27');
INSERT INTO `sys_role_menus` VALUES ('2', '27');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '27');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '27');
INSERT INTO `sys_role_menus` VALUES ('1', '28');
INSERT INTO `sys_role_menus` VALUES ('2', '28');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '28');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '28');
INSERT INTO `sys_role_menus` VALUES ('1', '29');
INSERT INTO `sys_role_menus` VALUES ('2', '29');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '29');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '29');
INSERT INTO `sys_role_menus` VALUES ('1', '3');
INSERT INTO `sys_role_menus` VALUES ('2', '3');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '3');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '3');
INSERT INTO `sys_role_menus` VALUES ('1', '30');
INSERT INTO `sys_role_menus` VALUES ('2', '30');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '30');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '30');
INSERT INTO `sys_role_menus` VALUES ('1', '4');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '4');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '4');
INSERT INTO `sys_role_menus` VALUES ('1', '5');
INSERT INTO `sys_role_menus` VALUES ('2', '5');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '5');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '5');
INSERT INTO `sys_role_menus` VALUES ('1', '6');
INSERT INTO `sys_role_menus` VALUES ('2', '6');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '6');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '6');
INSERT INTO `sys_role_menus` VALUES ('1', '7');
INSERT INTO `sys_role_menus` VALUES ('2', '7');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '7');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '7');
INSERT INTO `sys_role_menus` VALUES ('1', '8');
INSERT INTO `sys_role_menus` VALUES ('2', '8');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '8');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '8');
INSERT INTO `sys_role_menus` VALUES ('1', '9');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', '9');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', '9');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eba5c8d40035', '9');
INSERT INTO `sys_role_menus` VALUES ('2', 'ff80808165ebdb820165ebc13c740037');
INSERT INTO `sys_role_menus` VALUES ('2', 'ff80808165ebdb820165ebd02ff60038');
INSERT INTO `sys_role_menus` VALUES ('20dbe80a-97fa-4fd3-9065-d7d3af8b188c', 'ff80808165ebdb820165ebd02ff60038');
INSERT INTO `sys_role_menus` VALUES ('ff80808165ebdb820165eb8dd430002d', 'ff80808165ebdb820165ebd02ff60038');

-- ----------------------------
-- Table structure for `sys_role_type_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_type_menu`;
CREATE TABLE `sys_role_type_menu` (
  `id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  `role_type` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reference_6` (`menu_id`) USING BTREE,
  CONSTRAINT `sys_role_type_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色类型资源';

-- ----------------------------
-- Records of sys_role_type_menu
-- ----------------------------
INSERT INTO `sys_role_type_menu` VALUES ('1129a605-7d47-4ea3-ad29-192f36b7b658', '6', '2');
INSERT INTO `sys_role_type_menu` VALUES ('16e8dfd4-2b30-4cce-8b0d-eeb1712ec546', '22', '3');
INSERT INTO `sys_role_type_menu` VALUES ('1aa92d4e-d9b1-41dc-ad69-1206dc5266a4', '1', '3');
INSERT INTO `sys_role_type_menu` VALUES ('4667b09d-77f4-4714-a215-b03a34a2c391', '19', '2');
INSERT INTO `sys_role_type_menu` VALUES ('4dac39a7-686c-4e41-85e9-6c5d982bbe98', '25', '3');
INSERT INTO `sys_role_type_menu` VALUES ('52a7d84b-136f-4945-b96a-cf5b9570bba5', '4', '3');
INSERT INTO `sys_role_type_menu` VALUES ('629bf98b-2e12-474a-9e6b-b5ef2a44ec9e', '23', '2');
INSERT INTO `sys_role_type_menu` VALUES ('62fc08e7-32fe-4a0a-bd3d-5db7ab099512', '16', '2');
INSERT INTO `sys_role_type_menu` VALUES ('64fcaace-044b-4c41-afbd-634b9d15da85', '5', '2');
INSERT INTO `sys_role_type_menu` VALUES ('6c88865c-00d1-4354-856a-fc6c1a4fa214', '24', '3');
INSERT INTO `sys_role_type_menu` VALUES ('8b394c06-111d-4940-b497-143ceeda8790', '18', '2');
INSERT INTO `sys_role_type_menu` VALUES ('9baf0aed-4da0-4106-9afb-a8fa5128faff', '9', '3');
INSERT INTO `sys_role_type_menu` VALUES ('9d8cfa35-7a9c-4b34-a5fc-31dd157f8a24', '1', '2');
INSERT INTO `sys_role_type_menu` VALUES ('a4622af4-3cc6-47d9-a419-d1b20d0a96ea', '21', '3');
INSERT INTO `sys_role_type_menu` VALUES ('a919ec96-5f89-427d-8215-5a4ac8efccad', '17', '2');
INSERT INTO `sys_role_type_menu` VALUES ('aa8602e0-c561-4e43-9724-9b3c4d111696', '20', '3');
INSERT INTO `sys_role_type_menu` VALUES ('afcc112f-6f04-481f-8293-70c0d5c0b485', '24', '2');
INSERT INTO `sys_role_type_menu` VALUES ('b960a68d-4187-4d1e-b019-a27d481f998a', '3', '2');
INSERT INTO `sys_role_type_menu` VALUES ('ba6aaea2-af05-49c9-a11a-b45a493847ce', '2', '3');
INSERT INTO `sys_role_type_menu` VALUES ('d84f8313-cb8f-4fd0-9462-16991aba34e6', '15', '2');
INSERT INTO `sys_role_type_menu` VALUES ('d94fc337-5f6f-4387-bab8-e1bdb544e09c', '6', '3');
INSERT INTO `sys_role_type_menu` VALUES ('e6e35702-c6bb-4460-90a0-c3e3241c55bc', '26', '3');
INSERT INTO `sys_role_type_menu` VALUES ('e88165a2-9752-4eb0-a7bc-11f1499b0ca0', '15', '3');
INSERT INTO `sys_role_type_menu` VALUES ('ebc15e91-66ca-426f-a5eb-cd7059331241', '3', '3');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce5f00f6', '1', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000f7', '2', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000f8', '9', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000f9', '10', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000fa', '11', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000fb', '12', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6000fc', '13', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6100fd', '14', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6100fe', '3', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce6100ff', '15', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce610100', '16', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce610101', '17', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce610102', '18', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620103', '19', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620104', '4', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620105', '20', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620106', '21', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620107', '22', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce620108', '5', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce630109', '23', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce63010a', '6', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce63010b', '24', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce63010c', '25', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce63010d', '26', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce63010e', '7', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce64010f', '27', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce640110', '28', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce640111', '29', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce640112', '30', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce640113', '8', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce640114', 'ff80808165ebdb820165ebd02ff60038', '1');
INSERT INTO `sys_role_type_menu` VALUES ('ff80808165ebdb820165ebd7ce650115', 'ff80808165ebdb820165ebc13c740037', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `account` varchar(50) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `profile_id` varchar(36) DEFAULT NULL,
  `fault_num` int(11) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `is_super` char(1) DEFAULT '0' COMMENT '是否超级管理员，1：是',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_k_u_sys_user_username` (`account`) USING BTREE,
  KEY `fk_reference_5` (`profile_id`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `sys_user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'god', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '1', '0', '1', '1', '1', '2018-08-31 14:51:03', '2018-09-20 11:04:52', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('1cddcf35-b797-4536-b76c-fdfef2cdf4de', 'sys-test', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '83d7c2da-282d-4620-9f15-f700efba88a3', null, '0', '1', '0', '2018-09-11 11:22:31', '2018-09-18 17:21:38', null, null);
INSERT INTO `sys_user` VALUES ('202c4712-b788-475e-ab01-08f1ddaa6275', 'test', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '10880d02-c5c1-41d8-b599-fea531a00e3b', null, '0', '1', '0', '2018-09-11 16:43:51', '2018-09-11 16:48:55', null, null);
INSERT INTO `sys_user` VALUES ('62b9ff1e-bd16-45cf-8802-86fc7356a987', 'tewww', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', null, null, '0', '0', '0', '2018-09-03 16:09:11', '2018-09-03 16:09:11', null, null);
INSERT INTO `sys_user` VALUES ('951327b0-cf06-4387-baa6-d309f5b3de62', 'test111', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '6d1e6f71-a627-4868-872c-7de5f61446ca', null, '0', '0', '0', '2018-09-11 17:24:11', '2018-09-18 17:51:14', null, null);
INSERT INTO `sys_user` VALUES ('a5e09f6a-ce0c-4b12-ae66-4bf3e0688b4f', 'sys-test1', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '49acf714-6267-4ea6-9140-952680a5c6b5', null, '1', '1', '0', '2018-09-11 11:23:43', '2018-09-18 17:51:21', null, null);
INSERT INTO `sys_user` VALUES ('c839439a-0883-484a-9f69-c53214fcc885', 'sys-user-test', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'd80f9777-88bd-496a-8c29-897e8fea5d9f', null, '1', '1', '0', '2018-09-11 11:28:05', '2018-09-11 17:05:11', null, null);
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db316', 'account1', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '3', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-09-18 17:23:30', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db326', 'account2', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '4', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-09-11 17:15:40', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db336', 'account3', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '5', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db346', 'account4', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '6', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db356', 'account5', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '7', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db366', 'account6', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '8', '1', '1', '1', '0', '2018-08-31 17:57:02', '2018-09-18 17:52:55', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db376', 'account0', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '2', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db386', 'account7', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '9', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db396', 'account8', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '10', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db426', 'account9', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', '11', '1', '1', '0', '0', '2018-08-31 17:57:02', '2018-08-31 17:57:02', 'aaaaaa', 'aaaaaa');
INSERT INTO `sys_user` VALUES ('ff80808165ebdb820165ea8aa4bf0002', 'test001', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'ff80808165ebdb820165ea8aa46e0001', null, '1', '1', '0', '2018-09-18 10:37:59', '2018-09-18 15:48:53', null, null);
INSERT INTO `sys_user` VALUES ('ff80808165ebdb820165ea8b34eb0004', 'test002', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'ff80808165ebdb820165ea8b34c40003', null, '1', '1', '0', '2018-09-18 10:38:36', '2018-09-18 15:49:57', null, null);
INSERT INTO `sys_user` VALUES ('ff80808165ebdb820165ea9464370008', 'test003', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'ff80808165ebdb820165ea9464210007', null, '1', '1', '0', '2018-09-18 10:48:38', '2018-09-20 14:23:30', null, null);
INSERT INTO `sys_user` VALUES ('ff80808165ebdb820165eb408135000c', 'test004', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'ff80808165ebdb820165eb408119000b', null, '0', '1', '0', '2018-09-18 13:56:38', '2018-09-18 15:51:45', null, null);
INSERT INTO `sys_user` VALUES ('ff80808165ebdb820165efd98775011b', 'test005', '$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey', 'ff80808165ebdb820165efd9875e011a', null, '1', '1', '0', '2018-09-19 11:22:15', '2018-09-20 14:48:04', null, null);

-- ----------------------------
-- Table structure for `sys_user_profile`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_profile`;
CREATE TABLE `sys_user_profile` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `sex` varchar(36) DEFAULT NULL,
  `position` varchar(36) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL COMMENT '冗余用户角色名称，多个以分号隔开',
  `org_id` varchar(36) DEFAULT NULL,
  `org_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reference_7` (`org_id`) USING BTREE,
  CONSTRAINT `sys_user_profile_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `sys_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详情';

-- ----------------------------
-- Records of sys_user_profile
-- ----------------------------
INSERT INTO `sys_user_profile` VALUES ('1', '超级管理员', 'https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2018-5/20185241432980733.gif', '123@163.com', '17600208420', '0', null, '超级管理员,超级管理员2', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('10', '超级管理员10', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('10880d02-c5c1-41d8-b599-fea531a00e3b', '测试', null, null, null, null, null, null, '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('11', '超级管理员11', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('2', '超级管理员2', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('3', '啊啊', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员,超级管理员2', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('4', '超级管理员4', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员,超级管理员2', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('48a6479c-0611-42fa-8caa-00628f1bbdd2', 'account', null, 'test@mail.taiji.com.cn', '13112345678', '1', '局长', null, null, null);
INSERT INTO `sys_user_profile` VALUES ('49acf714-6267-4ea6-9140-952680a5c6b5', 'sys-test1', null, 'suncla@mail.taiji.com.cn', '13146943790', '1', '1', '', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('5', '超级管理员5', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('51e428cb-aa56-4a34-a2cb-b559d6e8c174', 'account', null, 'test@mail.taiji.com.cn', '13112345678', '1', '局长', null, null, null);
INSERT INTO `sys_user_profile` VALUES ('6', '超级管理员6', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('60833cb6-402c-4e97-93e4-7f11ae6bbaf2', 'sys-test', null, 'suncla@mail.taiji.com.cn', '13146943790', '1', '1', null, '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('6b299d2a-56ab-4b49-b90f-3161e06a8365', 'sys-test', null, 'suncla@mail.taiji.com.cn', '13146943790', '1', '1', null, '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('6d1e6f71-a627-4868-872c-7de5f61446ca', '啊啊', null, null, null, null, null, '', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('7', '超级管理员7', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('8', '超级管理员8', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('83d7c2da-282d-4620-9f15-f700efba88a3', 'sys-test', null, 'suncla@mail.taiji.com.cn', '13146943790', '0', '123', '超级管理员', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('9', '超级管理员9', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '', '', '1', '', '超级管理员', '1', '组织机构');
INSERT INTO `sys_user_profile` VALUES ('d80f9777-88bd-496a-8c29-897e8fea5d9f', 'sys-user-test', null, 'suncla@mail.taiji.com.cn', '13146943790', '1', '1', null, '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('ff80808165ebdb820165ea8aa46e0001', '测试001', null, 'test001@mail.com', '13088888888', '1', '测试', '超级管理员,操作员', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('ff80808165ebdb820165ea8b34c40003', '测试002', null, null, null, null, null, '操作员', '1', '部门1');
INSERT INTO `sys_user_profile` VALUES ('ff80808165ebdb820165ea9464210007', '测试003', null, 'test003@mail.com', '13088888888', '0', '测试003测试003测试003测试003', '管理员', '98702c0d-3447-4c4a-99a7-d04b8b9f5f0d', '部门2');
INSERT INTO `sys_user_profile` VALUES ('ff80808165ebdb820165eb408119000b', '测试004', null, null, null, null, null, null, 'ff80808165ebdb820165ea69111a0000', '测试新增2-1-1');
INSERT INTO `sys_user_profile` VALUES ('ff80808165ebdb820165efd9875e011a', '测试005测试005', null, null, null, null, '测试005测试005测试005测试005测试005测试005', '', '98702c0d-3447-4c4a-99a7-d04b8b9f5f0d', '部门2');

-- ----------------------------
-- Table structure for `sys_user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_s_u_r_r_sys_role` (`role_id`) USING BTREE,
  CONSTRAINT `sys_user_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_user_roles_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色';

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
INSERT INTO `sys_user_roles` VALUES ('1', '1');
INSERT INTO `sys_user_roles` VALUES ('1cddcf35-b797-4536-b76c-fdfef2cdf4de', '1');
INSERT INTO `sys_user_roles` VALUES ('202c4712-b788-475e-ab01-08f1ddaa6275', '1');
INSERT INTO `sys_user_roles` VALUES ('c839439a-0883-484a-9f69-c53214fcc885', '1');
INSERT INTO `sys_user_roles` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db316', '1');
INSERT INTO `sys_user_roles` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db326', '1');
INSERT INTO `sys_user_roles` VALUES ('ff80808165ebdb820165ea8aa4bf0002', '1');
INSERT INTO `sys_user_roles` VALUES ('1', '2');
INSERT INTO `sys_user_roles` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db316', '2');
INSERT INTO `sys_user_roles` VALUES ('dbf43c61-f544-48ca-8c8a-bb77479db326', '2');
INSERT INTO `sys_user_roles` VALUES ('ff80808165ebdb820165ea9464370008', 'ff80808165ebdb820165eb8dd430002d');
INSERT INTO `sys_user_roles` VALUES ('ff80808165ebdb820165ea8aa4bf0002', 'ff80808165ebdb820165eba5c8d40035');
INSERT INTO `sys_user_roles` VALUES ('ff80808165ebdb820165ea8b34eb0004', 'ff80808165ebdb820165eba5c8d40035');
