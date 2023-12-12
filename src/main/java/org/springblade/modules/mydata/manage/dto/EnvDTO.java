package org.springblade.modules.mydata.manage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 环境配置数据传输对象实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode
public class EnvDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 接口请求Header
     */
    private List<Map<String, String>> globalHeaders;

    /**
     * 接口请求参数
     */
    private List<Map<String, String>> globalParams;
}
