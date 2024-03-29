package org.springblade.modules.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.common.util.MdUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.modules.mydata.manage.cache.ManageCache;
import org.springblade.modules.mydata.manage.entity.Env;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.vo.EnvVO;

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
        EnvVO envVO = BeanUtil.copyProperties(env, EnvVO.class, "globalHeaders", "globalParams");

        envVO.setGlobalHeaders(MdUtil.switchMapToList(env.getGlobalHeaders()));
        envVO.setGlobalParams(MdUtil.switchMapToList(env.getGlobalParams()));

        Project project = ManageCache.getProject(env.getProjectId());
        if (project != null) {
            envVO.setProjectName(project.getProjectName());
        }

        return envVO;
    }

}
