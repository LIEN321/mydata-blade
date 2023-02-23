package org.springblade.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API 相关统计DTO
 *
 * @author LIEN
 * @date 2023/2/23
 */
@Data
@EqualsAndHashCode
public class ApiStatDTO {
    /**
     * API数量
     */
    private Long apiCount;
    /**
     * 提供数据的API数量
     */
    private Long producerCount;
    /**
     * 消费数据的API数量
     */
    private Long consumerCount;
}
