package org.springblade.mydata.manage.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.dto.ApiStatDTO;
import org.springblade.mydata.manage.dto.AppStatDTO;
import org.springblade.mydata.manage.dto.DataStatDTO;
import org.springblade.mydata.manage.dto.TaskStatDTO;
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
        DataStatDTO dataStat = dataService.getDataStat();
        stat.setDataCount(dataStat.getDataCount());
        stat.setBizDataCount(dataStat.getBizDataCount());

        // 统计应用
        AppStatDTO appStat = appService.getAppStat();
        stat.setAppCount(appStat.getAppCount());

        // 统计API
        ApiStatDTO apiStat = apiService.getApiStat();
        stat.setApiCount(apiStat.getApiCount());
        stat.setProducerCount(apiStat.getProducerCount());
        stat.setConsumerCount(apiStat.getConsumerCount());

        // 统计任务
        TaskStatDTO taskStat = taskService.getTaskStat();
        stat.setTaskCount(taskStat.getTaskCount());
        stat.setRunningCount(taskStat.getRunningCount());
        stat.setStoppedCount(taskStat.getStoppedCount());
        stat.setFailedCount(taskStat.getFailedCount());

        return R.data(stat);
    }
}
