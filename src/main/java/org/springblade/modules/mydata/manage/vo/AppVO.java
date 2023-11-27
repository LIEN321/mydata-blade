package org.springblade.modules.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用视图实体类
 *
 * @author LIEN
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode
public class AppVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;
}
