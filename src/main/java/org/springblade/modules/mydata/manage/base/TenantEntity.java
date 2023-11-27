package org.springblade.modules.mydata.manage.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户的基础类
 *
 * @author LIEN
 * @since 2022/7/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    private static final long serialVersionUID = -3225465072759074463L;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
}
