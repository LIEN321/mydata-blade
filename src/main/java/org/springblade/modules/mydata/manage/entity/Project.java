package org.springblade.modules.mydata.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.mydata.manage.base.TenantEntity;

/**
 * 项目 实体类
 *
 * @author LIEN
 * @since 2023/12/5
 */
@Data
@TableName("md_project")
@EqualsAndHashCode(callSuper = true)
public class Project extends TenantEntity {
    private static final long serialVersionUID = 1894634589165990120L;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目描述
     */
    private String projectDesc;
}