package org.springblade.mydata.manage.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.vo.EnvVO;

/**
 * 环境配置包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2022-07-11
 */
public class EnvWrapper extends BaseEntityWrapper<Env, EnvVO> {

    public static EnvWrapper build() {
        return new EnvWrapper();
    }

    @Override
    public EnvVO entityVO(Env env) {
        EnvVO envVO = BeanUtil.copy(env, EnvVO.class);

        return envVO;
    }

}
