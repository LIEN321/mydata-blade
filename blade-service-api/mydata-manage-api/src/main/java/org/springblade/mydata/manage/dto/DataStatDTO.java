package org.springblade.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据项 相关统计DTO
 *
 * @author LIEN
 * @date 2023/2/23
 */
@Data
@EqualsAndHashCode
public class DataStatDTO {
    /**
     * 登记的数据项数量
     */
    private Long dataCount;

    /**
     * 业务数据的数量
     */
    private Long bizDataCount;
}
