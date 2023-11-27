package org.springblade.modules.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.mydata.manage.base.TenantEntity;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 环境配置实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@TableName(value = "md_env", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class Env extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 环境名称
     */
    private String envName;

    /**
     * 前置路径
     */
    private String envPrefix;

    /**
     * 全局header参数
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String, String> globalHeaders;

    /**
     * 全局变量
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String, String> globalParams;

    /**
     * 同步到任务的时间
     */
    private Date syncTaskTime;

}
