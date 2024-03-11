package org.springblade.modules.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 环境下拉列表 视图实体类
 *
 * @author LIEN
 * @since 2024-01-13
 */
@Data
@EqualsAndHashCode
public class EnvSelectVO implements Serializable {
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
     * 环境任务数量
     */
    private Long taskCount;

    /**
     * 所属项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
}
