INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545322605295689729, 0, 'mydata_manage', '数据融合', 'menu', '/manage', 'swap', 100, 1, 1, 1, '', 0);

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545323164115394561, 1545322605295689729, 'data', '数据管理', 'menu', '/manage/data', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545323164115394562, 1545323164115394561, 'data_add', '新增', 'add', '/manage/data/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545323164115394563, 1545323164115394561, 'data_edit', '修改', 'edit', '/manage/data/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545323164115394564, 1545323164115394561, 'data_delete', '删除', 'delete', '/api/mydata-manage/data/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545323164115394565, 1545323164115394561, 'data_view', '查看', 'view', '/manage/data/view', 'file-text', 4, 2, 2, 1, NULL, 0);

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545427898142810114, 1545322605295689729, 'api', 'API管理', 'menu', '/manage/api', '', 3, 1, 0, 1, '', 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545427898142810115, 1545427898142810114, 'api_add', '新增', 'add', '/manage/api/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545427898142810116, 1545427898142810114, 'api_edit', '修改', 'edit', '/manage/api/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545427898142810117, 1545427898142810114, 'api_delete', '删除', 'delete', '/api/mydata-manage/api/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1545427898142810118, 1545427898142810114, 'api_view', '查看', 'view', '/manage/api/view', 'file-text', 4, 2, 2, 1, NULL, 0);

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546341857284841474, 1545322605295689729, 'env', '环境管理', 'menu', '/manage/env', '', 5, 1, 0, 1, '', 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546341857284841475, 1546341857284841474, 'env_add', '新增', 'add', '/manage/env/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546341857284841476, 1546341857284841474, 'env_edit', '修改', 'edit', '/manage/env/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546341857284841477, 1546341857284841474, 'env_delete', '删除', 'delete', '/api/mydata-manage/env/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546341857284841478, 1546341857284841474, 'env_view', '查看', 'view', '/manage/env/view', 'file-text', 4, 2, 2, 1, NULL, 0);

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546392469737299969, 1545322605295689729, 'task', '任务管理', 'menu', '/manage/task', '', 4, 1, 0, 1, '', 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546392469737299970, 1546392469737299969, 'task_add', '新增', 'add', '/manage/task/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546392469737299971, 1546392469737299969, 'task_edit', '修改', 'edit', '/manage/task/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546392469737299972, 1546392469737299969, 'task_delete', '删除', 'delete', '/api/mydata-manage/task/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1546392469737299973, 1546392469737299969, 'task_view', '查看', 'view', '/manage/task/view', 'file-text', 4, 2, 2, 1, NULL, 0);

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620321974120275969, 1545322605295689729, 'app', '应用管理', 'menu', '/manage/app', NULL, 2, 1, 0, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620322693665710082, 1620321974120275969, 'app_add', '新增', 'add', '/manage/app/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620322881964793858, 1620321974120275969, 'app_edit', '修改', 'edit', '/manage/app/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620323080615419905, 1620321974120275969, 'app_delete', '删除', 'delete', '/api/mydata-manage/app/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620323332772782081, 1620321974120275969, 'app_view', '查看', 'view', '/manage/app/view', 'file-text', 4, 2, 2, 1, NULL, 0);
