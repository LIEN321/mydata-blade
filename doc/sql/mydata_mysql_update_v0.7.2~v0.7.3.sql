ALTER TABLE `blade_tenant` ADD COLUMN `tenant_code` varchar(255) NULL COMMENT '租户编号';
ALTER TABLE `md_env_var` ADD COLUMN `is_hidden` int NULL DEFAULT 0 COMMENT '是否隐藏明文，0-不隐藏、1-隐藏';