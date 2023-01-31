package org.springblade.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.base.TenantEntity;

/**
 * 应用实体类
 *
 * @author LIEN
 * @since 2023-01-31
 */
@Data
@TableName(value = "md_app", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class App extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编号
     */
    private String appCode;
    /**
     * 应用名称
     */
    private String appName;

}
