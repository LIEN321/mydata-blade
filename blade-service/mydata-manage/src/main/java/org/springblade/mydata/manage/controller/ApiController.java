package org.springblade.mydata.manage.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.dto.ApiDTO;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.service.IApiService;
import org.springblade.mydata.manage.vo.ApiVO;
import org.springblade.mydata.manage.wrapper.ApiWrapper;
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
 * 应用接口 控制器
 *
 * @author LIEN
 * @since 2022-07-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@io.swagger.annotations.Api(value = "应用接口", tags = "应用接口接口")
public class ApiController extends BladeController {

    private final IApiService apiService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入api")
    public R<ApiVO> detail(Api api) {
        Api detail = apiService.getOne(Condition.getQueryWrapper(api));
        return R.data(ApiWrapper.build().entityVO(detail));
    }

    /**
     * 分页 应用接口
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入api")
    public R<IPage<Api>> list(Api api, Query query) {
        LambdaQueryWrapper<Api> queryWrapper = Wrappers.lambdaQuery();
        if (api != null) {
            queryWrapper.like(ObjectUtil.isNotNull(api.getApiName()), Api::getApiName, api.getApiName());
        }

        IPage<Api> pages = apiService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 自定义分页 应用接口
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入api")
    public R<IPage<ApiVO>> page(ApiVO api, Query query) {
        IPage<ApiVO> pages = apiService.selectApiPage(Condition.getPage(query), api);
        return R.data(pages);
    }

    /**
     * 新增 应用接口
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入api")
    public R save(@Valid @RequestBody Api api) {
        boolean result = apiService.save(api);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 修改 应用接口
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入api")
    public R update(@Valid @RequestBody Api api) {
        boolean result = apiService.updateById(api);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 新增或修改 应用接口
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入api")
    public R submit(@Valid @RequestBody ApiDTO apiDTO) {
        boolean result = apiService.submit(apiDTO);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }


    /**
     * 删除 应用接口
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = apiService.deleteApi(Func.toLongList(ids));
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 下拉数据源
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "传入post")
    public R<List<ApiVO>> select() {
        LambdaQueryWrapper<Api> queryWrapper = Wrappers.<Api>lambdaQuery()
                .eq(Api::getTenantId, SecureUtil.getTenantId());
        List<Api> list = apiService.list(queryWrapper);
        return R.data(ApiWrapper.build().listVO(list));
    }

    @PutMapping("/syncTask")
    public R syncTask(Long id) {
        boolean result = apiService.syncTask(id);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }
}
