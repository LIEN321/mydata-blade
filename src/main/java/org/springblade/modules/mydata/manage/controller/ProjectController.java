package org.springblade.modules.mydata.manage.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.mydata.manage.cache.ManageCache;
import org.springblade.modules.mydata.manage.dto.ProjectDTO;
import org.springblade.modules.mydata.manage.entity.Project;
import org.springblade.modules.mydata.manage.service.IProjectService;
import org.springblade.modules.mydata.manage.vo.ProjectVO;
import org.springblade.modules.mydata.manage.wrapper.ProjectWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 项目 控制器
 *
 * @author LIEN
 * @since 2023/12/5
 */
@RestController
@AllArgsConstructor
@RequestMapping(MdConstant.API_PREFIX_MANAGE + "/project")
@Api(value = "项目", tags = "项目接口")
public class ProjectController extends BladeController {

    private final IProjectService projectService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入project")
    public R<ProjectVO> detail(Project project) {
        Project detail = projectService.getOne(Condition.getQueryWrapper(project));
        return R.data(ProjectWrapper.build().entityVO(detail));
    }

    /**
     * 分页 项目
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入project")
    public R<IPage<ProjectVO>> list(Project project, Query query) {
        LambdaQueryWrapper<Project> queryWrapper = Wrappers.lambdaQuery();
        if (project != null) {
            queryWrapper.like(ObjectUtil.isNotNull(project.getProjectName()), Project::getProjectName, project.getProjectName());
        }
        IPage<Project> pages = projectService.page(Condition.getPage(query), queryWrapper);
        return R.data(ProjectWrapper.build().pageVO(pages));
    }

    /**
     * 新增或修改 项目
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入project")
    public R submit(@Valid @RequestBody ProjectDTO projectDTO) {
        boolean result = projectService.submit(projectDTO);
        if (result) {
            ManageCache.clearProject(projectDTO.getId());
        }
        return R.status(result);
    }


    /**
     * 删除 项目
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = projectService.delete(Func.toLongList(ids));
        if (result) {
            ManageCache.clearProject(Func.toLongArray(ids));
        }
        return R.status(result);
    }

}
