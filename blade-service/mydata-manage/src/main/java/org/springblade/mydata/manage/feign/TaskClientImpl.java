package org.springblade.mydata.manage.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.entity.TaskLog;
import org.springblade.mydata.manage.service.ITaskLogService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 任务Feign 实现类
 *
 * @author LIEN
 * @since 2022/7/14
 */
@RestController
@AllArgsConstructor
public class TaskClientImpl implements ITaskClient {

    private final ITaskService taskService;
    private final ITaskLogService taskLogService;

    /**
     * 获取运行中的任务列表
     *
     * @return 任务列表
     */
    @Override
    @GetMapping(RUNNING_TASKS)
    public R<List<Task>> runningTasks() {
        return R.data(taskService.listRunningTasks());
    }

    @Override
    @GetMapping(GET_TASK)
    public R<Task> getTask(Long id) {
        return R.data(taskService.getById(id));
    }

    @Override
    @GetMapping(FAIL_TASK)
    public R fail(Long id) {
        return R.status(taskService.failTask(id));
    }

    @Override
    @PostMapping(SAVE_LOG)
    public R saveLog(TaskLog taskLog) {
        return R.status(taskLogService.save(taskLog));
    }

    @Override
    @GetMapping(SUB_TASK)
    public R<List<Task>> getSubscribedTask(Long dataId) {
        return R.data(taskService.listRunningSubTasks(dataId));
    }

    @Override
    @PostMapping(COMPLETE_TASK)
    public R finishTask(Task task) {
        return R.data(taskService.updateById(task));
    }
}
