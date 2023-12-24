package org.springblade.modules.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * API 相关统计DTO
 *
 * @author LIEN
 * @since 2023/2/23
 */
@Data
@EqualsAndHashCode
public class ApiStatDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
