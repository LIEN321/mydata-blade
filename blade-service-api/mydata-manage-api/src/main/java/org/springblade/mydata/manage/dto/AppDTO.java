package org.springblade.mydata.manage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用数据传输对象实体类
 *
 * @author LIEN
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode
public class AppDTO {
    private static final long serialVersionUID = 1L;

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