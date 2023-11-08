package org.springblade.mydata.manage.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础的实体类
 * 补充id属性
 *
 * @author LIEN
 * @since 2022/7/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends org.springblade.core.mp.base.BaseEntity {
    private static final long serialVersionUID = -5377579759274292959L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
}
