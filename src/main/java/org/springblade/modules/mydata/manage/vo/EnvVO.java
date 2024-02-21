package org.springblade.modules.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 环境配置视图实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode
public class EnvVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
    private List<Map<String, String>> globalHeaders;

    /**
     * 全局变量
     */
    private List<Map<String, String>> globalParams;

    /**
     * 同步到任务的时间
     */
    private Date syncTaskTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 所属项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    /**
     * 所属项目名称
     */
    private String projectName;

    /**
     * 排序
     */
    private Integer sort;
}
