package org.springblade.mydata.manage.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.mydata.manage.dto.EnvVarDTO;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.service.IEnvVarService;
import org.springblade.mydata.manage.vo.EnvVarVO;
import org.springblade.mydata.manage.wrapper.EnvVarWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 环境变量 控制器
 *
 * @author LIEN
 * @since 2023/11/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/env_var")
@Api(value = "环境变量", tags = "环境变量接口")
public class EnvVarController extends BladeController {

    private final IEnvVarService envVarService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入envVar")
    public R<EnvVarVO> detail(EnvVar envVar) {
        EnvVar detail = envVarService.getOne(Condition.getQueryWrapper(envVar));
        return R.data(EnvVarWrapper.build().entityVO(detail));
    }

    /**
     * 分页 环境变量
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入env")
    public R<IPage<EnvVarVO>> list(EnvVar envVar, Query query) {
        LambdaQueryWrapper<EnvVar> queryWrapper = Wrappers.lambdaQuery();
        if (envVar != null) {
            queryWrapper.like(ObjectUtil.isNotNull(envVar.getVarName()), EnvVar::getVarName, envVar.getVarName());
            queryWrapper.eq(ObjectUtil.isNotNull(envVar.getEnvId()), EnvVar::getEnvId, envVar.getEnvId());
        }
        IPage<EnvVar> pages = envVarService.page(Condition.getPage(query), queryWrapper);
        return R.data(EnvVarWrapper.build().pageVO(pages));
    }

    /**
     * 自定义分页 环境变量
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入envVar")
    public R<IPage<EnvVarVO>> page(EnvVarVO envVar, Query query) {
        IPage<EnvVarVO> pages = envVarService.selectEnvVarPage(Condition.getPage(query), envVar);
        return R.data(pages);
    }

    /**
     * 新增 环境变量
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入envVar")
    public R save(@Valid @RequestBody EnvVar envVar) {
        boolean result = envVarService.save(envVar);
        return R.status(result);
    }

    /**
     * 修改 环境变量
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入envVar")
    public R update(@Valid @RequestBody EnvVar envVar) {
        boolean result = envVarService.updateById(envVar);
        return R.status(result);
    }

    /**
     * 新增或修改 环境变量
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入envVar")
    public R submit(@Valid @RequestBody EnvVarDTO envVarDTO) {
        boolean result = envVarService.submit(envVarDTO);
        return R.status(result);
    }

    /**
     * 删除 环境变量
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = envVarService.deleteEnvVar(Func.toLongList(ids));
        return R.status(result);
    }
}