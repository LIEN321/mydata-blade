package org.springblade.modules.mydata.manage.wrapper;

import cn.hutool.core.bean.BeanUtil;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.mydata.manage.cache.SystemCache;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.vo.ProjectVO;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;

/**
 * 应用包装类,返回视图层所需的字段
 *
 * @author LIEN
 * @since 2023/12/5
 */
public class ProjectWrapper extends BaseEntityWrapper<Project, ProjectVO> {

    private static IUserService userService;

    static {
        userService = SpringUtil.getBean(IUserService.class);
    }

    public static ProjectWrapper build() {
        return new ProjectWrapper();
    }

    @Override
    public ProjectVO entityVO(Project project) {
        ProjectVO projectVO = BeanUtil.copyProperties(project, ProjectVO.class);

        User user = SystemCache.getUser(project.getCreateUser());
        if (user != null) {
            projectVO.setCreateUser(user.getRealName());
        }

        return projectVO;
    }

}
