package org.springblade.modules.mydata.manage.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.tool.api.R;
import org.springblade.modules.mydata.manage.dto.ApiStatDTO;
import org.springblade.modules.mydata.manage.dto.AppStatDTO;
import org.springblade.modules.mydata.manage.dto.DataStatDTO;
import org.springblade.modules.mydata.manage.dto.ProjectStatDTO;
import org.springblade.modules.mydata.manage.dto.TaskStatDTO;
import org.springblade.modules.mydata.manage.entity.Task;
import org.springblade.modules.mydata.manage.service.IApiService;
import org.springblade.modules.mydata.manage.service.IAppService;
import org.springblade.modules.mydata.manage.service.IDataService;
import org.springblade.modules.mydata.manage.service.IProjectService;
import org.springblade.modules.mydata.manage.service.ITaskService;
import org.springblade.modules.mydata.manage.vo.WorkplaceStatVO;
import org.springblade.modules.mydata.manage.vo.WorkplaceTaskVO;
import org.springblade.modules.mydata.manage.wrapper.TaskWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 工作台 控制器
 *
 * @author LIEN
 * @since 2023/2/21
 */
@RestController
@RequestMapping(MdConstant.API_PREFIX_MANAGE + "/workplace")
@AllArgsConstructor
@Api(value = "工作台", tags = "工作台接口")
public class WorkplaceController {

    private final IDataService dataService;

    private final IAppService appService;

    private final IApiService apiService;

    private final ITaskService taskService;

    private final IProjectService projectService;

    @GetMapping("/stat")
    public R<WorkplaceStatVO> stat() {
        WorkplaceStatVO stat = new WorkplaceStatVO();

        // 统计项目
        ProjectStatDTO projectStat = projectService.getProjectStat();
        stat.setProjectCount(projectStat.getProjectCount());

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

    @GetMapping("/task")
    public R<WorkplaceTaskVO> task() {
        // 最近执行成功的任务
        List<Task> successTasks = taskService.listSuccessTasks();
        // 最近执行失败的任务
        List<Task> failedTasks = taskService.listFailedTasks();

        TaskWrapper taskWrapper = TaskWrapper.build();
        WorkplaceTaskVO workplaceTaskVO = new WorkplaceTaskVO();
        workplaceTaskVO.setSuccessTasks(taskWrapper.listVO(successTasks));
        workplaceTaskVO.setFailedTasks(taskWrapper.listVO(failedTasks));

        return R.data(workplaceTaskVO);
    }
}
