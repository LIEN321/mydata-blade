package org.springblade.mydata.manage.feign;

import org.springblade.common.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.entity.TaskLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 任务Feign接口
 *
 * @author LIEN
 * @date 2022/7/14
 */
@FeignClient(value = AppConstant.APPLICATION_MYDATA_MANAGE)
public interface ITaskClient {

    String API_PREFIX = "/api/task";
    String RUNNING_TASKS = API_PREFIX + "/runningTasks";
    String GET_TASK = API_PREFIX + "/getTask";
    String FAIL_TASK = API_PREFIX + "/fail";
    String SAVE_LOG = API_PREFIX + "/saveLog";
    String SUB_TASK = API_PREFIX + "/subTask";

    /**
     * 获取运行中的任务列表
     *
     * @return 任务列表
     */
    @GetMapping(RUNNING_TASKS)
    R<List<Task>> runningTasks();

    /**
     * 根据任务id 获取任务
     *
     * @param id 任务id
     * @return 任务
     */
    @GetMapping(GET_TASK)
    R<Task> getTask(@RequestParam("id") Long id);

    /**
     * 更新任务状态为异常
     *
     * @param id 任务id
     * @return 操作结果
     */
    @GetMapping(FAIL_TASK)
    R fail(@RequestParam("id") Long id);

    /**
     * 新增任务日志
     *
     * @param taskLog 任务日志
     * @return 操作结果
     */
    @PostMapping(SAVE_LOG)
    R saveLog(@RequestBody TaskLog taskLog);

    /**
     * 根据数据项 获取启动的订阅任务
     *
     * @param dataId 数据项id
     * @return 订阅任务列表
     */
    @GetMapping(SUB_TASK)
    R<List<Task>> getSubscribedTask(@RequestParam("dataId") Long dataId);
}
