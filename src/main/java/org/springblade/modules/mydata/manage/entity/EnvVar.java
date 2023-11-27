package org.springblade.modules.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.mydata.manage.base.TenantEntity;

/**
 * 环境配置实体类
 *
 * @author LIEN
 * @since 2023-11-01
 */
@Data
@TableName(value = "md_env_var")
@EqualsAndHashCode(callSuper = true)
public class EnvVar extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 所属环境id
     */
    private Long envId;

    /**
     * 变量名
     */
    private String varName;

    /**
     * 变量值
     */
    private String varValue;
}
