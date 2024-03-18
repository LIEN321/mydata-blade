package org.springblade.modules.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.modules.mydata.manage.entity.EnvVar;
import org.springblade.modules.mydata.manage.vo.EnvVarVO;

/**
 * 环境配置包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-11
 */
public class EnvVarWrapper extends BaseEntityWrapper<EnvVar, EnvVarVO> {

    public static EnvVarWrapper build() {
        return new EnvVarWrapper();
    }

    @Override
    public EnvVarVO entityVO(EnvVar envVar) {
        EnvVarVO envVarVO = BeanUtil.copyProperties(envVar, EnvVarVO.class);
        if (envVar.getIsHidden()) {
            envVarVO.setVarValue(MdConstant.SECURE_STRING);
        }
        return envVarVO;
    }

}
