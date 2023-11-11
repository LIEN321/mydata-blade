package org.springblade.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 环境配置视图实体类
 *
 * @author LIEN
 * @since 2023-11-01
 */
@Data
@EqualsAndHashCode
public class EnvVarVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 所属环境id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long envId;
    /**
     * 变量名
     */
    private String varName;
    /**
     * 变量值
     */
    private String varValue;
    /**
     * 更新时间
     */
    private Date updateTime;
}
