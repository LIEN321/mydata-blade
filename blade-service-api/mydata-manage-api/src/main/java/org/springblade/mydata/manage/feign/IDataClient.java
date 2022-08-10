package org.springblade.mydata.manage.feign;

import org.springblade.common.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据Feign接口
 *
 * @author LIEN
 * @date 2022/7/22
 */
@FeignClient(value = AppConstant.APPLICATION_MYDATA_MANAGE)
public interface IDataClient {

    String API_PREFIX = "/api/data";
    String UPDATE_COUNT = API_PREFIX + "/updateCount";

    /**
     * 更新业务数据量
     *
     * @param id 数据项id
     * @return 任务列表
     */
    @GetMapping(UPDATE_COUNT)
    R updateDataCount(@RequestParam Long id);

}
