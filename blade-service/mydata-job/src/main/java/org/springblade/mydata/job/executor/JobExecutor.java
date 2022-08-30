package org.springblade.mydata.job.executor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.executor.CronExpression;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.job.bean.TaskJob;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.entity.TaskLog;
import org.springblade.mydata.manage.feign.ITaskClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 任务执行器
 *
 * @author LIEN
 * @date 2022/7/14
 */
@Slf4j
@Component
public class JobExecutor implements ApplicationRunner {

    @Resource
    private ITaskClient taskClient;
    @Resource
    private JobCache jobCache;

    /**
     * 线程池 阻塞队列
     */
    private final BlockingQueue<Runnable> bq = new LinkedBlockingQueue<>();
    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;
    /**
     * 线程数量
     */
    @Value("${datacenter.job.threadCount:10}")
    private int jobThreadCount;

    @Override
    public void run(ApplicationArguments args) {
        // 移除已有缓存
        jobCache.removeAll();

        // 查询已启动的任务
        R<List<Task>> result = taskClient.runningTasks();
        Assert.isTrue(result != null && result.isSuccess());
        List<Task> tasks = result.getData();
        log.info("tasks.size() = " + tasks.size());

        // 将任务封装为Job对象
        List<TaskJob> taskJobs = tasks.stream().map(this::build).collect(Collectors.toList());

        taskJobs.forEach(this::cacheJob);
    }

    /**
     * 开始指定任务
     *
     * @param id 任务id
     */
    public void startTask(Long id) {
        R<Task> result = taskClient.getTask(id);
        Assert.isTrue(result != null && result.isSuccess());
        Task task = result.getData();
        if (task == null) {
            return;
        }
        // 若任务是订阅模式 则无需启动（fix #707419847）
        if (MdConstant.TASK_IS_SUBSCRIBED.equals(task.getIsSubscribed())) {
            return;
        }

        TaskJob taskJob = this.build(task);
        cacheJob(taskJob);
    }

    /**
     * 停止指定任务
     *
     * @param id 任务id
     */
    public void stopTask(Long id) {
        jobCache.removeJob(id);
    }

    /**
     * 开始job
     *
     * @param taskJob job
     */
    public void cacheJob(TaskJob taskJob) {
        taskJob.setEndTime(null);
        // 清空已有数据
        taskJob.setConsumeDataList(null);
        taskJob.setProduceDataList(null);
        // 清空任务日志
        taskJob.setLog(new StringBuffer());
        int i = 0;
        while (i < MdConstant.TASK_MAX_FAIL_COUNT) {
            try {
                // 设置开始时间
                taskJob.setStartTime(new Date());

                // 计算Job的下次执行时间
                calculateNextRunTime(taskJob);

                // 存入缓存
                jobCache.cacheJob(taskJob);
                return;
            } catch (RuntimeException e) {
                i++;
                ThreadUtil.sleep(5000);
            }
        }

        taskJob.setExecuteResult(MdConstant.TASK_RESULT_FAILED);
        fail(taskJob);
    }

    /**
     * 执行订阅任务
     *
     * @param taskJob 当前执行的任务
     */
    public void executeSubscribedTask(TaskJob taskJob) {
        // 当前任务不是 提供数据，则结束
        if (MdConstant.DATA_PRODUCER != taskJob.getOpType()) {
            return;
        }

        // 查询相同数据的订阅任务
        R<List<Task>> listR = taskClient.getSubscribedTask(taskJob.getDataId());
        if (listR.isSuccess()) {
            List<Task> subTasks = listR.getData();
            subTasks.forEach(task -> {
                TaskJob subTaskJob = build(task);
                subTaskJob.setStartTime(new Date());
                subTaskJob.setConsumeDataList(taskJob.getProduceDataList());
                executeJob(subTaskJob);
            });
        }
    }

    /**
     * 继续执行任务
     */
    public void continueJob(TaskJob taskJob) {
        // 执行任务
        cacheJob(taskJob);
    }

    public void fail(TaskJob taskJob) {
        // 更新任务状态
        taskClient.fail(taskJob.getId());
        // 删除任务缓存
        jobCache.removeJob(taskJob.getId());
    }

    public void notify(String taskId) {
        TaskJob taskJob = jobCache.getJob(taskId);
        if (taskJob == null) {
            log.error("notify taskJob is null, taskId = {}", taskId);
            return;
        }

        taskJob.appendLog("缓存到期，任务存入队列");
        executeJob(taskJob);
    }

    public void saveLog(TaskJob taskJob) {
        // 保存日志
        taskClient.saveLog(getTaskLog(taskJob));
    }

    /**
     * 执行任务
     *
     * @param taskJob 任务
     */
    private void executeJob(TaskJob taskJob) {
        Runnable runnable = new JobThread(taskJob);
        getThreadPoolExecutor().execute(runnable);
    }

    /**
     * 将配置的Task 构建为可执行的TaskJob
     *
     * @param task Task
     * @return TaskJob
     */
    private TaskJob build(Task task) {
        TaskJob taskJob = new TaskJob();

        // 任务基本信息
        taskJob.setId(task.getId());
        taskJob.setTaskName(task.getTaskName());
        taskJob.setTaskPeriod(task.getTaskPeriod());
        taskJob.setOpType(task.getOpType());
        taskJob.setDataType(task.getDataType());
        taskJob.setApiMethod(task.getApiMethod());
        taskJob.setApiUrl(task.getApiUrl());

        // 所属租户
        taskJob.setTenantId(task.getTenantId());
        // 字段层级前缀
        taskJob.setApiFieldPrefix(task.getApiFieldPrefix());
        // 字段映射
        taskJob.setFieldMapping(task.getFieldMapping());

        // 数据项id
        taskJob.setDataId(task.getDataId());
        // 数据项编号
        taskJob.setDataCode(task.getDataCode());

        // 唯一标识字段编号
        taskJob.setIdFieldCode(task.getIdFieldCode());

        // 是否为订阅任务
        taskJob.setIsSubscribed(task.getIsSubscribed());

        // header
        taskJob.setReqHeaders(task.getReqHeaders());
        // param
        Map<String, String> taskParams = task.getReqParams();
        if (CollUtil.isNotEmpty(taskParams)) {
            Map<String, Object> jobParams = MapUtil.newHashMap();
            jobParams.putAll(taskParams);
            taskJob.setReqParams(jobParams);
        }

        return taskJob;
    }

    /**
     * 根据 任务的上次执行时间 和 设定间隔规则，计算任务的 下次执行时间
     *
     * @param taskJob 定时任务
     */
    private void calculateNextRunTime(TaskJob taskJob) {
        Assert.notNull(taskJob);
        Assert.notEmpty(taskJob.getTaskPeriod());

        Date date = taskJob.getStartTime();
        if (taskJob.getFailCount() > 0) {
            date = taskJob.getNextRunTime();
        }

        CronExpression cronExpression = new CronExpression(taskJob.getTaskPeriod());
        Date nextRunTime = cronExpression.getNextValidTimeAfter(date);
        taskJob.setNextRunTime(nextRunTime);

        taskJob.appendLog("预计执行时间：{}", DateUtil.formatDateTime(taskJob.getNextRunTime()));
    }

    /**
     * 从线程池获取执行器
     *
     * @return
     */
    private ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    jobThreadCount
                    , Integer.MAX_VALUE
                    , 60L
                    , TimeUnit.SECONDS
                    , bq
                    , ThreadFactoryBuilder.create().setNamePrefix("data-task").build()
            );
        }

        return threadPoolExecutor;
    }

    private TaskLog getTaskLog(TaskJob taskJob) {
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(taskJob.getId());
        taskLog.setTaskStartTime(taskJob.getStartTime());
        taskLog.setTaskEndTime(taskJob.getEndTime());
        taskLog.setTaskResult(taskJob.getExecuteResult());
        taskLog.setTaskDetail(taskJob.getLog().toString());
        return taskLog;
    }
}
