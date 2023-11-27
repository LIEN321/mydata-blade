package org.springblade.modules.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用接口调试视图实体类
 *
 * @author LIEN
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode
public class ApiDebugVO {
    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     */
    private Integer status;
    /**
     * 响应耗时
     */
    private Long time;
    /**
     * 响应内容
     */
    private String body;
}
