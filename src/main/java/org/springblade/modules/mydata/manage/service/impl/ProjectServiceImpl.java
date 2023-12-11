package org.springblade.modules.mydata.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.mydata.manage.dto.ProjectDTO;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.mapper.ProjectMapper;
import org.springblade.modules.mydata.manage.service.IProjectService;
import org.springblade.modules.mydata.manage.vo.ProjectVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目 服务实现类
 *
 * @author LIEN
 * @since 2023/12/5
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectMapper, Project> implements IProjectService {
    @Override
    public IPage<ProjectVO> selectProjectPage(IPage<ProjectVO> page, ProjectVO project) {
        return page.setRecords(baseMapper.selectProjectPage(page, project));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submit(ProjectDTO projectDTO) {
        // 参数校验
        checkProject(projectDTO);

        // 复制提交信息
        Project project = BeanUtil.copyProperties(projectDTO, Project.class);

        return saveOrUpdate(project);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            ids.forEach(this::delete);
            return true;
        }
        return false;
    }

    private void checkProject(ProjectDTO projectDTO) {
        Assert.notNull(projectDTO, "提交失败：参数无效！");
        Long id = projectDTO.getId();
        String projectCode = projectDTO.getProjectCode();
        String projectName = projectDTO.getProjectName();
        String projectDesc = projectDTO.getProjectDesc();

        // 项目编号 不能为空
        Assert.notBlank(projectCode, "提交失败：项目编号 不能为空！");
        // 项目编号 长度不能超过限制
        Assert.isTrue(projectCode.length() <= MdConstant.MAX_CODE_LENGTH, "提交失败：项目编号 不能超过{}位！", MdConstant.MAX_CODE_LENGTH);

        // 项目名称 不能为空
        Assert.notBlank(projectName, "提交失败：项目名称 不能为空！");
        // 项目名称 长度不能超过限制
        Assert.isTrue(projectName.length() <= MdConstant.MAX_NAME_LENGTH, "提交失败：项目名称 不能超过{}位！", MdConstant.MAX_NAME_LENGTH);

        // 项目描述 长度不能超过限制
        Assert.isTrue(projectDesc.length() <= MdConstant.MAX_DESC_LENGTH, "提交失败：项目描述 不能超过{}位！", MdConstant.MAX_DESC_LENGTH);

        Project check = checkCode(id, projectCode);
        Assert.isNull(check, "提交失败：项目编号 {} 已存在，请更换！");
    }

    private Project checkCode(Long id, String code) {
        LambdaQueryWrapper<Project> queryWrapper = Wrappers.<Project>lambdaQuery()
                .eq(Project::getProjectCode, code)
                .ne(Func.notNull(id), Project::getId, id);
        return getOne(queryWrapper);
    }
}
