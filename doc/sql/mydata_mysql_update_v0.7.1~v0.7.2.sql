ALTER TABLE `md_task`
    ADD COLUMN `ref_env_id` bigint NULL COMMENT '跨环境任务的对应目标环境id',
    ADD COLUMN `ref_op_type` int NULL COMMENT '跨环境任务的对应操作类型';