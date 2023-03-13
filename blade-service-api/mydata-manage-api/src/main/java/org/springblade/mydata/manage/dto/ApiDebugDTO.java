package org.springblade.mydata.manage.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 应用接口调试传输对象实体类
 *
 * @author LIEN
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode
public class ApiDebugDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 请求方法
     */
    private String httpMethod;
    /**
     * 请求地址
     */
    private String httpUri;
    /**
     * 请求头
     */
    private Map<String, String> httpHeaders;
    /**
     * 请求参数
     */
    private Map<String, Object> httpParams;
}
