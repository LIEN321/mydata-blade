package org.springblade.mydata.manage.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.service.IApiService;
import org.springblade.mydata.manage.service.IAppService;
import org.springblade.mydata.manage.service.IDataService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springblade.mydata.manage.vo.WorkplaceStatVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作台 控制器
 *
 * @author LIEN
 * @date 2023/2/21
 */
@RestController
@RequestMapping("/workplace")
@AllArgsConstructor
@Api(value = "工作台", tags = "工作台接口")
public class WorkplaceController {

    private final IDataService dataService;
    private final IAppService appService;
    private final IApiService apiService;
    private final ITaskService taskService;

    @GetMapping("/stat")
    public R<WorkplaceStatVO> stat() {
        WorkplaceStatVO stat = new WorkplaceStatVO();

        // 统计数据
        stat.setDataCount(dataService.count());
        stat.setBizDataCount(dataService.sumBizDataCount());

        // 统计应用
        stat.setAppCount(appService.count());

        // 统计API
        stat.setApiCount(apiService.count());
        stat.setProducerCount(apiService.sumProducerCount());
        stat.setConsumerCount(apiService.sumConsumerCount());

        // 统计任务
        stat.setTaskCount(taskService.count());
        stat.setRunningCount(taskService.sumRunningCount());
        stat.setStoppedCount(taskService.sumStoppedCount());
        stat.setFailedCount(taskService.sumFailedCount());

        return R.data(stat);
    }
}
