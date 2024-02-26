-- 环境增加排序字段
ALTER TABLE `md_env` ADD COLUMN `sort` int NULL COMMENT '排序' AFTER `project_id`;

-- API表增加字段层级前缀字段
ALTER TABLE `md_api` ADD COLUMN `field_prefix` varchar(64) NULL COMMENT '字段层级前缀' AFTER `app_id`;

-- 复制原任务中的字段层级前缀到API表
update md_api a, (SELECT api_id, api_field_prefix FROM `md_task` where api_field_prefix is not null and api_field_prefix != '' group by api_id, api_field_prefix) t
	set a.field_prefix = t.api_field_prefix
	where a.id = t.api_id;