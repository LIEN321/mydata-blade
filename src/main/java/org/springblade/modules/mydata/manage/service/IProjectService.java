package org.springblade.modules.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.mydata.manage.dto.ProjectDTO;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.vo.ProjectVO;

import java.util.List;

/**
 * 项目 服务类
 *
 * @author LIEN
 * @since 2023/12/5
 */
public interface IProjectService extends BaseService<Project> {

    /**
     * 自定义分页
     *
     * @param page
     * @param project
     * @return
     */
    IPage<ProjectVO> selectProjectPage(IPage<ProjectVO> page, ProjectVO project);

    /**
     * 新增或修改 项目
     *
     * @param projectDTO 项目
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(ProjectDTO projectDTO);

    /**
     * 删除单个项目
     *
     * @param id 项目id
     * @return 操作结果，true-成功，false-失败
     */
    boolean delete(Long id);

    /**
     * 删除多个项目
     *
     * @param ids 项目id
     * @return 操作结果，true-成功，false-失败
     */
    boolean delete(List<Long> ids);
}
