package org.springblade.mydata.manage.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.mydata.manage.entity.Env;

/**
 * 环境配置视图实体类
 *
 * @author LIEN
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EnvVO extends Env {
    private static final long serialVersionUID = 1L;

}
