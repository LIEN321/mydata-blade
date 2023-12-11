package org.springblade.modules.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.vo.ProjectVO;

import java.util.List;

/**
 * 项目 Mapper 接口
 *
 * @author LIEN
 * @since 2023/12/5
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 自定义分页
     *
     * @param page
     * @param project
     * @return
     */
    List<ProjectVO> selectProjectPage(IPage page, ProjectVO project);
}
