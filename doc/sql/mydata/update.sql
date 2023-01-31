-- v0.3
DROP TABLE if exists `mydata`.`md_app`;
CREATE TABLE `mydata`.`md_app`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `app_code` VARCHAR(64) comment '应用编号',
  `app_name` VARCHAR(64) comment '应用名称',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '应用';

ALTER TABLE `mydata`.`md_api` ADD COLUMN `api_id` bigint NULL COMMENT '所属应用id';

INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620321974120275969, 1545322605295689729, 'app', '应用管理', 'menu', '/manage/app', NULL, 2, 1, 0, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620322693665710082, 1620321974120275969, 'app_add', '新增', 'add', '/manage/app/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620322881964793858, 1620321974120275969, 'app_edit', '修改', 'edit', '/manage/app/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620323080615419905, 1620321974120275969, 'app_delete', '删除', 'delete', '/api/mydata-manage/app/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mydata`.`blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1620323332772782081, 1620321974120275969, 'app_view', '查看', 'view', '/manage/app/view', 'file-text', 4, 2, 2, 1, NULL, 0);
