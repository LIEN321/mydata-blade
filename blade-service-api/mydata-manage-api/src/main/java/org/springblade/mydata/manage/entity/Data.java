package org.springblade.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.base.TenantEntity;

/**
 * 标准数据项实体类
 *
 * @author LIEN
 * @since 2022-07-08
 */
@lombok.Data
@TableName("md_data")
@EqualsAndHashCode(callSuper = true)
public class Data extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据编号
     */
    private String dataCode;
    /**
     * 数据名称
     */
    private String dataName;
    /**
     * 数据量
     */
    private Long dataCount;
}
