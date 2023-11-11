package org.springblade.mydata.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.dto.BizDataDTO;
import org.springblade.mydata.manage.service.IBizDataService;
import org.springblade.mydata.manage.service.IDataFieldService;
import org.springblade.mydata.manage.vo.DataFieldVO;
import org.springblade.mydata.manage.wrapper.DataFieldWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 业务数据 控制器
 *
 * @author LIEN
 * @since 2022/7/22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/biz_data")
@Api(value = "标准数据项", tags = "标准数据项接口")
public class BizDataController {

    private final IDataFieldService dataFieldService;
    private final IBizDataService bizDataService;

    /**
     * 根据数据项 查询字段列表
     *
     * @param dataId 数据项id
     * @return 字段列表
     */
    @GetMapping("/field_list")
    public R<List<DataFieldVO>> listDataFields(Long dataId) {
        return R.data(DataFieldWrapper.build().listVO(dataFieldService.findByData(dataId)));
    }

    @GetMapping("/data_list")
    public R<IPage<Map>> list(BizDataDTO bizDataDTO, Query query) {
        return R.data(bizDataService.bizDataPage(Condition.getPage(query), bizDataDTO));
    }

}
