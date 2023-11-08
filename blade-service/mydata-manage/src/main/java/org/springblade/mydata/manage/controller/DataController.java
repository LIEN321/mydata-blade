package org.springblade.mydata.manage.controller;

import cn.hutool.core.bean.BeanUtil;
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
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.mydata.manage.cache.ManageCache;
import org.springblade.mydata.manage.dto.DataDTO;
import org.springblade.mydata.manage.entity.Data;
import org.springblade.mydata.manage.entity.DataField;
import org.springblade.mydata.manage.service.IDataFieldService;
import org.springblade.mydata.manage.service.IDataService;
import org.springblade.mydata.manage.vo.DataFieldVO;
import org.springblade.mydata.manage.vo.DataVO;
import org.springblade.mydata.manage.wrapper.DataFieldWrapper;
import org.springblade.mydata.manage.wrapper.DataWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 标准数据项 控制器
 *
 * @author LIEN
 * @since 2022-07-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/data")
@Api(value = "标准数据项", tags = "标准数据项接口")
public class DataController extends BladeController {

    private final IDataService dataService;

    private final IDataFieldService dataFieldService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入data")
    public R<DataVO> detail(@RequestParam Long id) {
        Data data = dataService.getById(id);
        if (data == null) {
            return R.status(false);
        }
        List<DataField> dataFields = dataFieldService.findByData(id);

        DataVO dataVO = BeanUtil.copyProperties(data, DataVO.class);
        List<DataFieldVO> dataFieldVOList = BeanUtil.copyToList(dataFields, DataFieldVO.class);
        dataVO.setDataFields(dataFieldVOList);

        return R.data(dataVO);
    }

    /**
     * 分页 标准数据项
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入data")
    public R<IPage<DataVO>> list(Data data, Query query) {
        LambdaQueryWrapper<Data> queryWrapper = Wrappers.lambdaQuery();
        if (data != null) {
            queryWrapper.like(ObjectUtil.isNotNull(data.getDataCode()), Data::getDataCode, data.getDataCode());
            queryWrapper.like(ObjectUtil.isNotNull(data.getDataName()), Data::getDataName, data.getDataName());
        }
        IPage<Data> pages = dataService.page(Condition.getPage(query), queryWrapper);
        return R.data(DataWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页 标准数据项
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入data")
    public R<IPage<DataVO>> page(DataVO data, Query query) {
        IPage<DataVO> pages = dataService.selectDataPage(Condition.getPage(query), data);
        return R.data(pages);
    }

    /**
     * 新增 标准数据项
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入data")
    public R save(@Valid @RequestBody Data data) {
        boolean result = dataService.save(data);
        if (result) {
            ManageCache.clearData(data.getId());
        }
        return R.status(result);
    }

    /**
     * 修改 标准数据项
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入data")
    public R update(@Valid @RequestBody Data data) {
        boolean result = dataService.updateById(data);
        if (result) {
            ManageCache.clearData(data.getId());
        }
        return R.status(result);
    }

    /**
     * 新增或修改 标准数据项
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入data")
    public R submit(@NotNull @Valid @RequestBody DataDTO dataDTO) {
        boolean result = dataService.submit(dataDTO);
        if (result) {
            ManageCache.clearData(dataDTO.getId());
        }
        return R.status(result);
    }


    /**
     * 删除 标准数据项
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = dataService.remove(Func.toLongList(ids));
        if (result) {
            ManageCache.clearData(Func.toLongArray(ids));
        }
        return R.status(result);
    }

    /**
     * 下拉数据源
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "传入post")
    public R<List<DataVO>> select() {
        LambdaQueryWrapper<Data> queryWrapper = Wrappers.<Data>lambdaQuery()
                .eq(Data::getTenantId, SecureUtil.getTenantId());
        List<Data> list = dataService.list(queryWrapper);
        return R.data(DataWrapper.build().listVO(list));
    }

    /**
     * 数据项字段列表
     */
    @GetMapping("/dataFields")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "数据项字段列表", notes = "传入post")
    public R<List<DataFieldVO>> dataFields(Long dataId) {
        LambdaQueryWrapper<DataField> queryWrapper = Wrappers.<DataField>lambdaQuery()
                .eq(DataField::getDataId, dataId);
        List<DataField> list = dataFieldService.list(queryWrapper);
        return R.data(DataFieldWrapper.build().listVO(list));
    }
}
