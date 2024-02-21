-- Sheet:`md_data`
-- TableName:标准数据项
-- Description:标准数据项
DROP TABLE if exists `md_data`;
CREATE TABLE `md_data`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `data_code` VARCHAR(64) comment '数据编号',
  `data_name` VARCHAR(64) comment '数据名称',
  `data_count` INT DEFAULT 0 comment '数据量',
  `project_id` BIGINT comment '所属项目',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '标准数据项';

-- Sheet:`md_data_field`
-- TableName:标准数据项字段
-- Description:标准数据项字段
DROP TABLE if exists `md_data_field`;
CREATE TABLE `md_data_field`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `data_id` BIGINT(64) comment '所属数据项',
  `field_code` VARCHAR(64) comment '字段编号',
  `field_name` VARCHAR(64) comment '字段名称',
  `is_id` INTEGER comment '是否标识，0-不是、1-是',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '标准数据项字段';

-- Sheet:`md_app`
-- TableName:应用
-- Description:应用
DROP TABLE if exists `md_app`;
CREATE TABLE `md_app`(
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

-- Sheet:`md_api`
-- TableName:应用接口
-- Description:应用接口
DROP TABLE if exists `md_api`;
CREATE TABLE `md_api`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `api_name` VARCHAR(64) comment '接口名称',
  `op_type` INT comment '操作类型，1-提供数据、2-消费数据',
  `api_method` VARCHAR(64) comment '接口请求方法',
  `api_uri` VARCHAR(1024) comment '接口相对路径',
  `data_type` VARCHAR(10) comment '接口数据类型：JSON',
  `req_headers` TEXT comment '接口请求Header',
  `req_params` TEXT comment '接口请求参数',
  `sync_task_time` DATETIME comment '同步到任务的时间',
  `app_id` BIGINT comment '所属应用id',
  `field_prefix` VARCHAR(64) comment '字段层级前缀',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '应用接口';

-- Sheet:`md_task`
-- TableName:集成任务
-- Description:集成任务
DROP TABLE if exists `md_task`;
CREATE TABLE `md_task`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `task_name` VARCHAR(64) comment '任务名称',
  `env_id` BIGINT(64) comment '所属环境',
  `api_id` BIGINT(64) comment '所属应用接口',
  `api_url` VARCHAR(1024) comment '接口完整地址',
  `op_type` INT comment '操作类型，1-提供数据、2-消费数据',
  `api_method` VARCHAR(64) comment '接口请求类型',
  `data_id` BIGINT(64) comment '所属数据',
  `task_period` VARCHAR(64) comment '任务周期',
  `api_field_prefix` VARCHAR(64) comment '字段层级前缀',
  `field_mapping` TEXT comment '字段映射',
  `task_status` INT DEFAULT 0 comment '运行状态：0-停止，1-运行，2-异常',
  `data_type` VARCHAR(10) comment '接口数据类型：JSON',
  `data_code` VARCHAR(64) comment '数据编号',
  `id_field_code` VARCHAR(64) comment '数据主键字段编号',
  `is_subscribed` INT DEFAULT 0 comment '是否为订阅任务：0-不订阅，1-订阅',
  `req_headers` TEXT comment '接口请求Header',
  `req_params` TEXT comment '接口请求参数',
  `data_filter` TEXT comment '数据的过滤条件',
  `last_run_time` DATETIME comment '最后执行时间',
  `last_success_time` DATETIME comment '最后成功时间',
  `field_var_mapping` TEXT comment '接口字段与变量名的映射',
  `app_id` BIGINT comment '所属应用id',
  `project_id` BIGINT comment '所属项目',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '集成任务';

-- Sheet:`md_task_log`
-- TableName:集成任务日志
-- Description:集成任务日志
DROP TABLE if exists `md_task_log`;
CREATE TABLE `md_task_log`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `task_id` BIGINT(64) comment '所属任务',
  `task_start_time` DATETIME comment '执行开始时间',
  `task_end_time` DATETIME comment '执行结束时间',
  `task_result` INTEGER comment '执行结果（0-不执行，1-成功）',
  `task_detail` LONGTEXT comment '执行内容',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '集成任务日志';

-- Sheet:`md_env`
-- TableName:环境配置
-- Description:环境配置
DROP TABLE if exists `md_env`;
CREATE TABLE `md_env`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `env_name` VARCHAR(64) comment '环境名称',
  `env_prefix` VARCHAR(128) comment '前置路径',
  `global_headers` TEXT comment '全局header参数',
  `global_params` TEXT comment '全局变量',
  `sync_task_time` DATETIME comment '同步到任务的时间',
  `project_id` BIGINT comment '所属项目',
  `sort` INT comment '排序',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '环境配置';

-- Sheet:`md_env_var`
-- TableName:环境变量
-- Description:环境变量
DROP TABLE if exists `md_env_var`;
CREATE TABLE `md_env_var`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `env_id` BIGINT(64) comment '所属环境id',
  `var_name` VARCHAR(64) comment '变量名',
  `var_value` VARCHAR(512) comment '变量值',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '环境变量';

-- Sheet:`md_project`
-- TableName:项目
-- Description:项目
DROP TABLE if exists `md_project`;
CREATE TABLE `md_project`(
  `id` BIGINT(64) NOT NULL comment '主键',
  `status` TINYINT UNSIGNED comment '业务状态',
  `is_deleted` TINYINT UNSIGNED NULL DEFAULT 0 comment '删除状态：0-未删除，1-已删除',
  `create_user` BIGINT(64) comment '创建人',
  `create_dept` BIGINT(64) comment '创建部门',
  `create_time` DATETIME comment '创建时间',
  `update_user` BIGINT(64) comment '更新人',
  `update_time` DATETIME comment '更新时间',
  `tenant_id` VARCHAR(12) comment '所属租户',
  `project_code` VARCHAR(64) comment '项目编号',
  `project_name` VARCHAR(64) comment '项目名称',
  `project_desc` VARCHAR(1024) DEFAULT '0' comment '项目描述',
  PRIMARY Key(`id`)
) DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci comment '项目';