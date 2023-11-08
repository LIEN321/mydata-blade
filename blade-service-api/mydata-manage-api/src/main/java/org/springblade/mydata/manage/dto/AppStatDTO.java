package org.springblade.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用 相关统计DTO
 *
 * @author LIEN
 * @since 2023/2/23
 */
@Data
@EqualsAndHashCode
public class AppStatDTO {
    /**
     * 应用数量
     */
    private Long appCount;
}
