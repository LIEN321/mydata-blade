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
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.mydata.manage.cache.ManageCache;
import org.springblade.modules.mydata.manage.dto.EnvDTO;
import org.springblade.modules.mydata.manage.entity.Env;
import org.springblade.modules.mydata.manage.service.IEnvService;
import org.springblade.modules.mydata.manage.vo.EnvVO;
import org.springblade.modules.mydata.manage.wrapper.EnvWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 环境配置 控制器
 *
 * @author LIEN
 * @since 2022-07-11
 */
@RestController
@AllArgsConstructor
@RequestMapping(MdConstant.API_PREFIX_MANAGE + "/env")
@Api(value = "环境配置", tags = "环境配置接口")
public class EnvController extends BladeController {

    private final IEnvService envService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入env")
    public R<EnvVO> detail(Env env) {
        Env detail = envService.getOne(Condition.getQueryWrapper(env));
        return R.data(EnvWrapper.build().entityVO(detail));
    }

    /**
     * 分页 环境配置
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入env")
    public R<IPage<EnvVO>> list(Env env, Query query) {
        LambdaQueryWrapper<Env> queryWrapper = Wrappers.lambdaQuery();
        if (env != null) {
            queryWrapper.like(ObjectUtil.isNotNull(env.getEnvName()), Env::getEnvName, env.getEnvName());
        }
        queryWrapper.orderByAsc(Env::getProjectId);
        IPage<Env> pages = envService.page(Condition.getPage(query), queryWrapper);
        return R.data(EnvWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页 环境配置
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入env")
    public R<IPage<EnvVO>> page(EnvVO env, Query query) {
        IPage<EnvVO> pages = envService.selectEnvPage(Condition.getPage(query), env);
        return R.data(pages);
    }

    /**
     * 新增 环境配置
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入env")
    public R save(@Valid @RequestBody Env env) {
        boolean result = envService.save(env);
        if (result) {
            ManageCache.clearEnv(env.getId());
        }
        return R.status(result);
    }

    /**
     * 修改 环境配置
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入env")
    public R update(@Valid @RequestBody Env env) {
        boolean result = envService.updateById(env);
        if (result) {
            ManageCache.clearEnv(env.getId());
        }
        return R.status(result);
    }

    /**
     * 新增或修改 环境配置
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入env")
    public R submit(@Valid @RequestBody EnvDTO envDTO) {
        boolean result = envService.submit(envDTO);
        if (result) {
            ManageCache.clearEnv(envDTO.getId());
        }
        return R.status(result);
    }


    /**
     * 删除 环境配置
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = envService.deleteEnv(Func.toLongList(ids));
        if (result) {
            ManageCache.clearEnv(Func.toLongArray(ids));
        }
        return R.status(result);
    }

    /**
     * 下拉数据源
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "传入post")
    public R<List<Env>> select() {
        LambdaQueryWrapper<Env> queryWrapper = Wrappers.<Env>lambdaQuery()
                .eq(Env::getTenantId, SecureUtil.getTenantId());
        List<Env> list = envService.list(queryWrapper);
        return R.data(list);
    }

    @PutMapping("/syncTask")
    public R syncTask(Long id) {
        boolean result = envService.syncTask(id);
        if (result) {
            ManageCache.clearEnv(id);
        }
        return R.status(result);
    }
}
