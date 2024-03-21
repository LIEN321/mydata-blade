ALTER TABLE `md_task`
    ADD COLUMN `batch_status` int(0) NULL DEFAULT 0 COMMENT '分批启用状态：0-不启用，1-启用',
    ADD COLUMN `batch_interval` int(0) NULL DEFAULT 2 COMMENT '分批间隔（秒）',
    ADD COLUMN `batch_params` text NULL COMMENT '分批参数';