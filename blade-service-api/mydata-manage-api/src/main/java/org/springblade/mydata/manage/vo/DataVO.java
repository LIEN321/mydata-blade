package org.springblade.mydata.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.entity.Data;

import java.util.List;

/**
 * 标准数据项视图实体类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@lombok.Data
@EqualsAndHashCode(callSuper = true)
public class DataVO extends Data {
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
     * 数据量
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataCount;

    /**
     * 数据项的字段列表
     */
    private List<DataFieldVO> dataFields;

}
