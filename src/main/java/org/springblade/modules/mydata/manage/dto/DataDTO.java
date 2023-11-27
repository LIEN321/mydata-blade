package org.springblade.modules.mydata.manage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 标准数据项数据传输对象实体类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@lombok.Data
@EqualsAndHashCode
public class DataDTO {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 数据编号
     */
    private String dataCode;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 数据项的字段列表
     */
    private List<DataFieldDTO> dataFields;
}
