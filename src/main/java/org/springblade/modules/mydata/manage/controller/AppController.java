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
import org.springblade.modules.mydata.manage.dto.AppDTO;
import org.springblade.modules.mydata.manage.entity.App;
import org.springblade.modules.mydata.manage.service.IAppService;
import org.springblade.modules.mydata.manage.vo.AppVO;
import org.springblade.modules.mydata.manage.wrapper.AppWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 应用 控制器
 *
 * @author LIEN
 * @since 2023-01-31
 */
@RestController
@AllArgsConstructor
@RequestMapping(MdConstant.API_PREFIX_MANAGE + "/app")
@Api(value = "应用", tags = "应用接口")
public class AppController extends BladeController {

    private final IAppService appService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入app")
    public R<AppVO> detail(App app) {
        App detail = appService.getOne(Condition.getQueryWrapper(app));
        return R.data(AppWrapper.build().entityVO(detail));
    }

    /**
     * 分页 应用
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入app")
    public R<IPage<AppVO>> list(App app, Query query) {
        LambdaQueryWrapper<App> queryWrapper = Wrappers.lambdaQuery();
        if (app != null) {
            queryWrapper.like(ObjectUtil.isNotNull(app.getAppCode()), App::getAppCode, app.getAppCode());
            queryWrapper.like(ObjectUtil.isNotNull(app.getAppName()), App::getAppName, app.getAppName());
        }
        IPage<App> pages = appService.page(Condition.getPage(query), queryWrapper);
        return R.data(AppWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页 应用
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入app")
    public R<IPage<AppVO>> page(AppVO app, Query query) {
        IPage<AppVO> pages = appService.selectAppPage(Condition.getPage(query), app);
        return R.data(pages);
    }

    /**
     * 新增 应用
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入app")
    public R save(@Valid @RequestBody App app) {
        boolean result = appService.save(app);
        if (result) {
            ManageCache.clearApp(app.getId());
        }
        return R.status(result);
    }

    /**
     * 修改 应用
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入app")
    public R update(@Valid @RequestBody App app) {
        boolean result = appService.updateById(app);
        if (result) {
            ManageCache.clearApp(app.getId());
        }
        return R.status(result);
    }

    /**
     * 新增或修改 应用
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入app")
    public R submit(@Valid @RequestBody AppDTO appDTO) {
        boolean result = appService.submit(appDTO);
        if (result) {
            ManageCache.clearApp(appDTO.getId());
        }
        return R.status(result);
    }


    /**
     * 删除 应用
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = appService.deleteApp(Func.toLongList(ids));
        if (result) {
            ManageCache.clearApp(Func.toLongArray(ids));
        }
        return R.status(result);
    }

    /**
     * 下拉数据源
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "传入post")
    public R<List<AppVO>> select() {
        LambdaQueryWrapper<App> queryWrapper = Wrappers.<App>lambdaQuery()
                .eq(App::getTenantId, SecureUtil.getTenantId());
        List<App> list = appService.list(queryWrapper);
        return R.data(AppWrapper.build().listVO(list));
    }

}
