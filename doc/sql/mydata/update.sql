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