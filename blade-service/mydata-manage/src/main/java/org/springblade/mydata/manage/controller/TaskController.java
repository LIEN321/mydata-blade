package org.springblade.mydata.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.mydata.manage.cache.MdCache;
import org.springblade.mydata.manage.dto.TaskDTO;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.entity.TaskLog;
import org.springblade.mydata.manage.service.ITaskLogService;
import org.springblade.mydata.manage.service.ITaskService;
import org.springblade.mydata.manage.vo.TaskLogVO;
import org.springblade.mydata.manage.vo.TaskVO;
import org.springblade.mydata.manage.wrapper.TaskLogWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 集成任务 控制器
 *
 * @author LIEN
 * @since 2022-07-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task")
@Api(value = "集成任务", tags = "集成任务接口")
public class TaskController extends BladeController {

    private final ITaskService taskService;
    private final ITaskLogService taskLogService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入task")
    public R<TaskVO> detail(@RequestParam Long id) {
        return R.data(taskService.detail(id));
    }

    /**
     * 分页 集成任务
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入task")
    public R<IPage<TaskVO>> list(Task task, Query query) {
        return R.data(taskService.taskPage(Condition.getPage(query), Condition.getQueryWrapper(task)));
    }


    /**
     * 自定义分页 集成任务
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入task")
    public R<IPage<TaskVO>> page(TaskVO task, Query query) {
        IPage<TaskVO> pages = taskService.selectTaskPage(Condition.getPage(query), task);
        return R.data(pages);
    }

    /**
     * 新增 集成任务
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入task")
    public R save(@Valid @RequestBody Task task) {
        boolean result = taskService.save(task);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 修改 集成任务
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入task")
    public R update(@Valid @RequestBody Task task) {
        boolean result = taskService.updateById(task);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 新增或修改 集成任务
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入task")
    public R submit(@Valid @RequestBody TaskDTO taskDTO) {
        boolean result = taskService.submit(taskDTO);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }


    /**
     * 删除 集成任务
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        boolean result = taskService.delete(Func.toLongList(ids));
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }


    /**
     * 启动任务
     *
     * @param id 任务id
     */
    @PostMapping("/start/{id}")
    public R startTask(@PathVariable Long id) {
        boolean result = taskService.startTask(id);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 停止任务
     *
     * @param id 任务id
     */
    @PostMapping("/stop/{id}")
    public R stopTask(@PathVariable Long id) {
        boolean result = taskService.stopTask(id);
        if (result) {
            MdCache.clear();
        }
        return R.status(result);
    }

    /**
     * 任务 日志列表
     */
    @GetMapping("/logs")
    public R<IPage<TaskLogVO>> listLogs(TaskLog taskLog, Query query) {
        IPage<TaskLog> tasks = taskLogService.listTaskLogPage(Condition.getPage(query), taskLog);
        return R.data(TaskLogWrapper.build().pageVO(tasks));
    }
}