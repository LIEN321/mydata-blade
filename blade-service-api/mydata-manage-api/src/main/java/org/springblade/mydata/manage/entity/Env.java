package org.springblade.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.base.TenantEntity;

import java.util.Date;

/**
 * 环境配置实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@TableName("md_env")
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
    private String globalHeaders;
    /**
     * 全局变量
     */
    private String globalParams;
    /**
     * 同步到任务的时间
     */
    private Date syncTaskTime;

}
