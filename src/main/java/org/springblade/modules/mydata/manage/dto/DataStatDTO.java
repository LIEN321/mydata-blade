package org.springblade.modules.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 数据项 相关统计DTO
 *
 * @author LIEN
 * @since 2023/2/23
 */
@Data
@EqualsAndHashCode
public class DataStatDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登记的数据项数量
     */
    private Long dataCount;

    /**
     * 业务数据的数量
     */
    private Long bizDataCount;
}
