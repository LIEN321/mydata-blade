package org.springblade.modules.mydata.manage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 环境变量 数据传输对象实体类
 *
 * @author LIEN
 * @since 2023/11/1
 */
@Data
public class EnvVarDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long envId;

    private String varName;

    private String varValue;
}
