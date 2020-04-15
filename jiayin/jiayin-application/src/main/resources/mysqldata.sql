
-- ----------------------------
-- Records of data_item
-- ----------------------------
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('27902fc1547c2ca610dd10ded1aceafa',0, NULL, '2019-04-04 10:28:37.000000', 1, NULL, NULL, '6f47faf5d0eb5003a0ea09ae1af6152a', '回族', '1', 1, '回族', '1');
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('487157640e863429a42ad50f3c17752b',0, NULL, '2019-04-04 10:28:37.000000', 1, NULL, NULL, 'c50aa804aa8000c9c474f930d794116e', '男', '1', 1, '男', '1');
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('6f47faf5d0eb5003a0ea09ae1af6152a',0, NULL, '2019-04-04 10:28:35.000000', 1, NULL, NULL, '', '56个民族', '1', 2, '56个民族', '1');
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('c50aa804aa8000c9c474f930d794116e',0, NULL, '2019-04-04 10:28:37.000000', 1, NULL, NULL, '', '性别', '2', 2, '性别', '1');
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('e5172d1a71334f42104b7f3760eabf04',0, NULL, '2019-04-04 10:28:37.000000', 1, NULL, NULL, 'c50aa804aa8000c9c474f930d794116e', '女', '2', 2, '女', '1');
INSERT INTO `data_item`(`id`,`version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `parent_id`, `remark`, `code`, `code_index`, `name`, `state`) VALUES ('f83e6cb1c434d1902a86aa31854b8644',0, NULL, '2019-04-04 10:28:37.000000', 1, NULL, NULL, '6f47faf5d0eb5003a0ea09ae1af6152a', '汉族', '1', 1, '汉族', '1');

-- ----------------------------
-- Records of user_info
-- ----------------------------

INSERT INTO `user_info` (`id`, `version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `address`, `avatar`, `birthday`, `blog`, `email`, `gender`, `id_number`, `integral`, `login_name`, `nickname`, `password`, `phone_num`, `remark`, `state`, `tag`, `user_index`, `user_name`, `avatar_content`) VALUES ('6613831cac9e4597abbd0138116a8f3c', 0,NULL, '2019-8-7 13:30:16', 1, NULL, '2019-7-15 15:25:52', '', 'user_admin.jpg', '2012-06-15 14:45', NULL, 'diamond@mail.diamond.com.cn', '0', '', NULL, 'admin', 'nickname', '$2a$10$puBHZpl2vUzGOVijjUssO.JQOijRVA6GqJDEgnCYCbROIzMMFQ.Ca', '', '', '1', '', 1, '管理员', NULL);

-- ----------------------------
-- Records of menu
-- ----------------------------

-- diamond
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('be87d0711de047efaffeef3c123b7f30', 0,'', '2019-6-28 08:15:14', '', '2019-6-28 08:15:14', '1', '', '', '', 0, '', 'Layout', '1', '', 'index', '1', 'menu', NULL);
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('be87d0711de047efaffeef3c123b7f00', 0,NULL, '2019-6-28 08:15:14', NULL, '2019-6-28 08:15:14', '1', NULL, NULL, '/index', 0, 'Diamond', 'diamond/views/dashboard/index', '1', NULL, 'index', '1', 'menu', 'be87d0711de047efaffeef3c123b7f30');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('90a127ce319d5d93b3b49c697cfa138f', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-23 10:48:40', '1', NULL, NULL, '/system', 1, '系统管理', 'Layout', NULL, NULL, 'setting', NULL, 'menu', NULL);
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('f33d83225bef590d81f61a5afcbbca14', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-30 10:02:02', '1', NULL, NULL, 'user-management', 0, '用户管理', 'Submenu', NULL, NULL, 'user-setting', NULL, 'menu', '90a127ce319d5d93b3b49c697cfa138f');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('3a7fd63c81304f5c82a7681fd892fdd6', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-30 10:01:42', '1', NULL, NULL, 'log', 7, '日志管理', 'Submenu', NULL, NULL, 'logs', NULL, 'menu', '90a127ce319d5d93b3b49c697cfa138f');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('7094928c2f4740c78ace4cd7bd3e733e', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-30 10:02:01', '1', NULL, NULL, 'user-dept', 1, '部门管理', 'Submenu', NULL, NULL, 'dept', NULL, 'menu', '90a127ce319d5d93b3b49c697cfa138f');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('ce1c23d861364795a5b455c1b2f6d6dd', 0,'', '2019-3-5 11:33:13', '', '2019-7-30 10:02:38', '1', '', '', 'parameter', 8, '参数管理', 'diamond/views/system-management/parameter/main', 'diamond/views/system-management/parameter/main', '', 'dictionary-multi', '', 'menu', '90a127ce319d5d93b3b49c697cfa138f');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('8f13fa7aa1dd40e181568e870a6a2d03', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 15:32:23', '1', NULL, NULL, 'user-role', 2, '权限', 'Submenu', NULL, NULL, 'user-role', NULL, 'menu', '90a127ce319d5d93b3b49c697cfa138f');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('da4c2695c36a50eab041735842b530e7', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-30 10:02:19', '1', NULL, NULL, '/dictionary-setting', 2, '资源管理', 'Layout', NULL, NULL, 'dictionary-setting', NULL, 'menu', NULL);
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('22fc466264e65171a7631b2079a2b7f6', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:48', '1', NULL, NULL, 'dictionary-single', 10, '单级业务字典', 'diamond/views/system-management/dictionary/singleMain', NULL, NULL, 'dictionary-single', NULL, 'menu', 'da4c2695c36a50eab041735842b530e7');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('6ecb9fd6e70f50b2be035485928b5cd6', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:40', '1', NULL, NULL, 'menu', 7, '菜单管理', 'diamond/views/system-management/menu/main', NULL, NULL, 'auth-menu', NULL, 'menu', 'da4c2695c36a50eab041735842b530e7');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('d9369152df124e1aae855f743d3eedc8', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:44', '1', NULL, NULL, 'dictionary-multi', 9, '多级业务字典', 'diamond/views/system-management/dictionary/multiMain', NULL, NULL, 'dictionary-multi', NULL, 'menu', 'da4c2695c36a50eab041735842b530e7');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('d9fab44477ba5d2caf1aab1b95281713', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:49', '1', NULL, NULL, 'dictionary-type', 11, '字典分类', 'diamond/views/system-management/dictionary-type/main', NULL, NULL, 'dictionary-type', NULL, 'menu', 'da4c2695c36a50eab041735842b530e7');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('183c6c91e0094459aefed0032e2f5c67', 0,NULL, '2019-9-30 08:48:59', NULL, '2019-9-30 08:48:59', '1', NULL, NULL, '/demo', 3, 'DEMO', 'Layout', '', NULL, 'logs', '', 'menu', NULL);
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('ce1c23d861364795a5b455c1b2f6d6cc', 0,'', '2019-3-5 11:33:13', '', '2019-8-7 10:55:23', '1', '', '', 'student', 9, '学生管理（测试用）', 'demo/views/student/main', 'demo/views/student/main', '', 'dictionary-multi', '', 'menu', '183c6c91e0094459aefed0032e2f5c67');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('323c76618c6b56109bd490baf0d00902', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:50', '1', NULL, NULL, 'dept', 12, '所有部门', 'diamond/views/system-management/dept/main', NULL, NULL, 'dept', NULL, 'menu', '7094928c2f4740c78ace4cd7bd3e733e');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('8bdc5038a6585fd2b5d3ef7b1e4bf4e1', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:53', '1', NULL, NULL, 'user', 14, '所有人员', 'diamond/views/system-management/user/main', NULL, NULL, 'user', NULL, 'menu', 'f33d83225bef590d81f61a5afcbbca14');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('3de22ff390ab5d06bafcce547ff780bb', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:51', '1', NULL, NULL, 'group', 3, '用户组', 'diamond/views/system-management/group/main', NULL, NULL, 'user-group', NULL, 'menu', 'f33d83225bef590d81f61a5afcbbca14');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('b837b164e8f1443b9a5dae27c8611a06', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:39', '1', NULL, NULL, 'log', 6, '日志列表', 'diamond/views/system-management/logs/main', NULL, NULL, 'logs', NULL, 'menu', '3a7fd63c81304f5c82a7681fd892fdd6');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('d2249f50a3235286b173663a6c45122d', 0,NULL, '2019-3-5 11:33:13', NULL, '2019-7-29 16:47:44', '1', NULL, NULL, 'role', 8, '角色管理', 'diamond/views/system-management/role/main', NULL, NULL, 'user-role', NULL, 'menu', '8f13fa7aa1dd40e181568e870a6a2d03');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('1813c625de4e4295a46b4c29f1ed9510', 0,NULL, '2019-8-21 15:24:29', NULL, '2019-8-21 15:24:29', '1', NULL, NULL, '', 1, '字典分类-按钮全部权限', NULL, '', NULL, '', 'dictionary-type/*', 'button', 'd9fab44477ba5d2caf1aab1b95281713');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('342d35053b4b4b92826b406c414ee1b8', 0,NULL, '2019-8-21 15:46:15', NULL, '2019-8-21 15:46:15', '1', NULL, NULL, '', 1, '参数管理-按钮所有权限', NULL, '', NULL, '', 'parameter/*', 'button', 'ce1c23d861364795a5b455c1b2f6d6dd');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('346c85e409b14e3f9517fbac7057476f', 0,NULL, '2019-8-21 14:50:05', NULL, '2019-8-21 14:50:05', '1', NULL, NULL, '', 1, '多级字典-按钮全部权限', NULL, '', NULL, 'setting', 'dictionary-multi/*', 'button', 'd9369152df124e1aae855f743d3eedc8');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('37b5c4f257ce4bfdbcf7d31a89d9fa2e', 0,NULL, '2019-8-21 15:44:50', NULL, '2019-8-22 14:42:42', '1', NULL, NULL, '', 1, '部门-按钮所有权限', NULL, '测试role-menu', NULL, '', 'dept/*', 'button', '323c76618c6b56109bd490baf0d00902');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('68b62115036e4bcf8a666e207aef2e33', 0,NULL, '2019-8-21 15:25:38', NULL, '2019-8-21 15:25:38', '1', NULL, NULL, '', 1, '日志列表-按钮全部权限', NULL, '', NULL, '', 'log/*', 'button', 'b837b164e8f1443b9a5dae27c8611a06');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('b56cc0c761504142b0cca3f0eb3b43a4', 0,NULL, '2019-8-21 15:45:46', NULL, '2019-8-21 15:45:46', '1', NULL, NULL, '', 1, '学生管理-按钮所有权限', NULL, '', NULL, '', 'student/*', 'button', 'ce1c23d861364795a5b455c1b2f6d6cc');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('d97e9384995841789b25914542c74f77', 0,NULL, '2019-8-21 15:23:24', NULL, '2019-8-21 15:23:24', '1', NULL, NULL, '', 1, '单级菜单-按钮全部权限', NULL, '', NULL, 'setting', 'dictionary-single/*', 'button', '22fc466264e65171a7631b2079a2b7f6');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('f06def714b63426ba08b7685811e17a5', 0,NULL, '2019-8-15 17:07:19', NULL, '2019-8-15 17:07:19', '1', NULL, NULL, '', 1, '角色-按钮全部权限', NULL, '', NULL, 'setting', 'role/*', 'button', 'd2249f50a3235286b173663a6c45122d');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('f57723e59a09451f9faaa3bc6b92ca88', 0,'', '2019-8-15 13:20:35', '', '2019-8-15 13:20:35', '1', '', '', '', 1, '菜单-按钮所有权限', '', '', '', 'setting', 'menu/*', 'button', '6ecb9fd6e70f50b2be035485928b5cd6');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('f69376890058460aa38f7581fa2f36da', 0,NULL, '2019-8-21 15:47:03', NULL, '2019-8-21 15:47:03', '1', NULL, NULL, '', 1, '用户组-按钮所有权限', NULL, '', NULL, '', 'group/*', 'button', '3de22ff390ab5d06bafcce547ff780bb');
INSERT INTO `menu` (`id`,`version`, `created_by`, `created_date`, `modified_by`, `modified_date`, `flag`, `controller_class`, `icon_path`, `menu_desc`, `menu_index`, `menu_name`, `menu_url`, `remark`, `mark`, `small_icon_path`, `authority_id`, `menu_classify`, `parent_id`) VALUES ('8eacf685bedb40519f5259209e4f6a8f', 0,NULL, '2019-8-21 15:47:33', NULL, '2019-8-21 15:47:33', '1', NULL, NULL, '', 1, '用户-按钮所有权限', NULL, '', NULL, '', 'user/*', 'button', '8bdc5038a6585fd2b5d3ef7b1e4bf4e1');

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` (`id`,`version`, `created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `remark`, `role_description`, `role_index`, `role_name`) VALUES ('5b66ecf45d634159a08468898b1b3217', 0,NULL, '2018-3-2 16:12:07', 1, NULL, '2019-7-29 13:56:58', NULL, NULL, NULL, 'ROLE_ADMIN');
INSERT INTO `role` (`id`,`version`, `created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `remark`, `role_description`, `role_index`, `role_name`) VALUES ('cc377e1b32e74e71953ddcd595d5498b', 0,NULL, '2019-8-7 13:34:58', 1, NULL, '2019-7-8 13:59:53', '1', NULL, 1, 'ROLE_USER');

-- ----------------------------
-- Records of dept_info
-- ----------------------------

INSERT INTO `dept_info` (`id`, `created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `dept_desc`, `dept_index`, `dept_name`, `dept_state`, `dept_type`, `dept_url`, `remark`, `parent_id`) VALUES ('227b237a8be04452abd223ed271f8189', NULL, '2018-4-26 13:20:13', 1, NULL, '2019-3-29 16:03:16', NULL, 1, 'hoioy计算机', '1', 'TDC', 'TDC', 'TDC', NULL);
INSERT INTO `dept_info` (`id`, `created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `dept_desc`, `dept_index`, `dept_name`, `dept_state`, `dept_type`, `dept_url`, `remark`, `parent_id`) VALUES ('cde254475a744d75ad3c24ba599f4890', NULL, '2019-3-18 17:27:01', 1, NULL, '2018-12-29 13:46:18', NULL, 12, '创新研究院', '1', 'TDC', NULL, 'TDC', '227b237a8be04452abd223ed271f8189');

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` (`id`, `version`,`created_by`, `created_date`, `flag`, `modified_by`, `modified_date`, `group_index`, `group_name`, `remark`, `state`) VALUES ('04de3dd663fd47cf9deea06a5a476614',0, NULL, '2019-7-8 09:36:44', 1, NULL, '2019-8-7 13:38:41', 1, '用户组', '1', '1');

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32171813c625de4e4295a46b4c29f1ed9510', '1813c625de4e4295a46b4c29f1ed9510', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b321722fc466264e65171a7631b2079a2b7f6', '22fc466264e65171a7631b2079a2b7f6', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b22fc466264e65171a7631b2079a2b7f6', '22fc466264e65171a7631b2079a2b7f6', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217323c76618c6b56109bd490baf0d00902', '323c76618c6b56109bd490baf0d00902', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b323c76618c6b56109bd490baf0d00902', '323c76618c6b56109bd490baf0d00902', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217342d35053b4b4b92826b406c414ee1b8', '342d35053b4b4b92826b406c414ee1b8', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217346c85e409b14e3f9517fbac7057476f', '346c85e409b14e3f9517fbac7057476f', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b321737b5c4f257ce4bfdbcf7d31a89d9fa2e', '37b5c4f257ce4bfdbcf7d31a89d9fa2e', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32173a7fd63c81304f5c82a7681fd892fdd6', '3a7fd63c81304f5c82a7681fd892fdd6', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b3a7fd63c81304f5c82a7681fd892fdd6', '3a7fd63c81304f5c82a7681fd892fdd6', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32173de22ff390ab5d06bafcce547ff780bb', '3de22ff390ab5d06bafcce547ff780bb', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b3de22ff390ab5d06bafcce547ff780bb', '3de22ff390ab5d06bafcce547ff780bb', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b321768b62115036e4bcf8a666e207aef2e33', '68b62115036e4bcf8a666e207aef2e33', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32176ecb9fd6e70f50b2be035485928b5cd6', '6ecb9fd6e70f50b2be035485928b5cd6', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b6ecb9fd6e70f50b2be035485928b5cd6', '6ecb9fd6e70f50b2be035485928b5cd6', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32177094928c2f4740c78ace4cd7bd3e733e', '7094928c2f4740c78ace4cd7bd3e733e', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b7094928c2f4740c78ace4cd7bd3e733e', '7094928c2f4740c78ace4cd7bd3e733e', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32178bdc5038a6585fd2b5d3ef7b1e4bf4e1', '8bdc5038a6585fd2b5d3ef7b1e4bf4e1', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b8bdc5038a6585fd2b5d3ef7b1e4bf4e1', '8bdc5038a6585fd2b5d3ef7b1e4bf4e1', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32178eacf685bedb40519f5259209e4f6a8f', '8eacf685bedb40519f5259209e4f6a8f', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b32178f13fa7aa1dd40e181568e870a6a2d03', '8f13fa7aa1dd40e181568e870a6a2d03', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b8f13fa7aa1dd40e181568e870a6a2d03', '8f13fa7aa1dd40e181568e870a6a2d03', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b321790a127ce319d5d93b3b49c697cfa138f', '90a127ce319d5d93b3b49c697cfa138f', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498b90a127ce319d5d93b3b49c697cfa138f', '90a127ce319d5d93b3b49c697cfa138f', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217b56cc0c761504142b0cca3f0eb3b43a4', 'b56cc0c761504142b0cca3f0eb3b43a4', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217b837b164e8f1443b9a5dae27c8611a06', 'b837b164e8f1443b9a5dae27c8611a06', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bb837b164e8f1443b9a5dae27c8611a06', 'b837b164e8f1443b9a5dae27c8611a06', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217be87d0711de047efaffeef3c123b7f00', 'be87d0711de047efaffeef3c123b7f00', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217ce1c23d861364795a5b455c1b2f6d6cc', 'ce1c23d861364795a5b455c1b2f6d6cc', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217ce1c23d861364795a5b455c1b2f6d6dd', 'ce1c23d861364795a5b455c1b2f6d6dd', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bce1c23d861364795a5b455c1b2f6d6dd', 'ce1c23d861364795a5b455c1b2f6d6dd', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217d2249f50a3235286b173663a6c45122d', 'd2249f50a3235286b173663a6c45122d', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bd2249f50a3235286b173663a6c45122d', 'd2249f50a3235286b173663a6c45122d', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217d9369152df124e1aae855f743d3eedc8', 'd9369152df124e1aae855f743d3eedc8', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bd9369152df124e1aae855f743d3eedc8', 'd9369152df124e1aae855f743d3eedc8', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217d97e9384995841789b25914542c74f77', 'd97e9384995841789b25914542c74f77', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217d9fab44477ba5d2caf1aab1b95281713', 'd9fab44477ba5d2caf1aab1b95281713', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bd9fab44477ba5d2caf1aab1b95281713', 'd9fab44477ba5d2caf1aab1b95281713', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217da4c2695c36a50eab041735842b530e7', 'da4c2695c36a50eab041735842b530e7', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bda4c2695c36a50eab041735842b530e7', 'da4c2695c36a50eab041735842b530e7', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217f06def714b63426ba08b7685811e17a5', 'f06def714b63426ba08b7685811e17a5', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217f33d83225bef590d81f61a5afcbbca14', 'f33d83225bef590d81f61a5afcbbca14', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('cc377e1b32e74e71953ddcd595d5498bf33d83225bef590d81f61a5afcbbca14', 'f33d83225bef590d81f61a5afcbbca14', 'cc377e1b32e74e71953ddcd595d5498b');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217f57723e59a09451f9faaa3bc6b92ca88', 'f57723e59a09451f9faaa3bc6b92ca88', '5b66ecf45d634159a08468898b1b3217');
INSERT INTO `role_menu`(`id`, `menu_id`, `role_id`) VALUES ('5b66ecf45d634159a08468898b1b3217f69376890058460aa38f7581fa2f36da', 'f69376890058460aa38f7581fa2f36da', '5b66ecf45d634159a08468898b1b3217');

-- ----------------------------
-- Records of role_user
-- ----------------------------

INSERT INTO `role_user` (`role_id`,`user_id`,`id`) VALUES ('5b66ecf45d634159a08468898b1b3217', '6613831cac9e4597abbd0138116a8f3c', '5b66ecf45d634159a08468898b1b32176613831cac9e4597abbd0138116a8f3c');