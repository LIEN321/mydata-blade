package org.springblade.modules.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 应用 相关统计DTO
 *
 * @author LIEN
 * @since 2023/2/23
 */
@Data
@EqualsAndHashCode
public class AppStatDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用数量
     */
    private Long appCount;
}
