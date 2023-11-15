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
import org.springblade.mydata.data.BizDataFilter;
import org.springblade.mydata.job.bean.TaskInfo;
import org.springblade.mydata.job.cache.JobCache;
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

/**
 * 任务执行器
 *
 * @author LIEN
 * @since 2022/7/14
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
        if (result == null || !result.isSuccess()) {
            return;
        }
        List<Task> tasks = result.getData();
        log.info("tasks.size() = " + tasks.size());
        if (CollUtil.isNotEmpty(tasks)) {
            tasks.forEach(this::startTask);
        }
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
        startTask(task);
    }

    /**
     * 开始指定任务
     *
     * @param task 任务对象
     */
    public void startTask(Task task) {
        if (task == null) {
            return;
        }
        // 若任务是订阅模式 则无需启动（fix #707419847）
        if (MdConstant.TASK_IS_SUBSCRIBED.equals(task.getIsSubscribed())) {
            return;
        }

        TaskInfo taskInfo = this.build(task);
        cacheJob(taskInfo);
    }

    /**
     * 执行一次 指定任务
     *
     * @param id 任务id
     */
    public void executeOnce(Long id) {
        R<Task> result = taskClient.getTask(id);
        Assert.isTrue(result != null && result.isSuccess());
        Task task = result.getData();
        if (task == null) {
            return;
        }
        TaskInfo taskInfo = this.build(task);
        taskInfo.setTimes(1);
        taskInfo.setStartTime(new Date());
        executeJob(taskInfo);
    }

    /**
     * 停止指定任务
     *
     * @param id 任务id
     */
    public void stopTask(Long id) {
        jobCache.removeTask(id);
    }

    /**
     * 开始job
     *
     * @param taskInfo job
     */
    public void cacheJob(TaskInfo taskInfo) {
        taskInfo.setEndTime(null);
        // 清空已有数据
        taskInfo.setConsumeDataList(null);
        taskInfo.setProduceDataList(null);
        // 清空任务日志
        taskInfo.setLog(new StringBuffer());
        int i = 0;
        while (i < MdConstant.TASK_MAX_FAIL_COUNT) {
            try {
                // 设置开始时间
                taskInfo.setStartTime(new Date());

                // 计算Job的下次执行时间
                calculateNextRunTime(taskInfo);

                // 存入缓存
                jobCache.cacheJob(taskInfo);
                return;
            } catch (RuntimeException e) {
                i++;
                ThreadUtil.sleep(5000);
            }
        }

        taskInfo.setExecuteResult(MdConstant.TASK_RESULT_FAILED);
        fail(taskInfo);
    }

    /**
     * 执行订阅任务
     *
     * @param taskInfo 当前执行的任务
     */
    public void executeSubscribedTask(TaskInfo taskInfo) {
        // 当前任务不是 提供数据，则结束
        if (MdConstant.DATA_PRODUCER != taskInfo.getOpType()) {
            return;
        }

        List<Map> produceDataList = taskInfo.getProduceDataList();
        if (CollUtil.isEmpty(produceDataList)) {
            return;
        }
        // 查询相同数据的订阅任务
        R<List<Task>> listR = taskClient.getSubscribedTask(taskInfo.getDataId());
        if (listR.isSuccess()) {
            List<Task> subTasks = listR.getData();
            subTasks.forEach(task -> {
                TaskInfo subTaskInfo = build(task);
                subTaskInfo.setStartTime(new Date());
                subTaskInfo.setConsumeDataList(produceDataList);
                executeJob(subTaskInfo);
            });
        }
    }

    /**
     * 继续执行任务
     */
    public void continueJob(TaskInfo taskInfo) {
        // 执行任务
        cacheJob(taskInfo);
    }

    public void fail(TaskInfo taskInfo) {
        // 更新任务状态
        taskClient.fail(taskInfo.getId());
        // 删除任务缓存
        jobCache.removeTask(taskInfo.getId());
    }

    public void notify(String taskId) {
        TaskInfo taskInfo = jobCache.getTask(taskId);
        if (taskInfo == null) {
            log.error("notify taskJob is null, taskId = {}", taskId);
            return;
        }

        taskInfo.appendLog("缓存到期，任务存入队列");
        executeJob(taskInfo);
    }

    public void completeJob(TaskInfo taskInfo) {
        // 更新任务的 最后执行时间、最后成功时间
        Task task = new Task();
        task.setId(taskInfo.getId());
        task.setLastRunTime(taskInfo.getLastRunTime());
        task.setLastSuccessTime(taskInfo.getLastSuccessTime());
        taskClient.finishTask(task);

        // 保存日志
        taskClient.saveLog(getTaskLog(taskInfo));
    }

    /**
     * 执行任务
     *
     * @param taskInfo 任务
     */
    private void executeJob(TaskInfo taskInfo) {
        Runnable runnable = new JobThread(taskInfo);
        getThreadPoolExecutor().execute(runnable);
    }

    /**
     * 将配置的Task 构建为可执行的TaskJob
     *
     * @param task Task
     * @return TaskJob
     */
    private TaskInfo build(Task task) {
        TaskInfo taskInfo = new TaskInfo();

        // 任务基本信息
        taskInfo.setId(task.getId());
        taskInfo.setTaskName(task.getTaskName());
        taskInfo.setEnvId(task.getEnvId());
        taskInfo.setTaskPeriod(task.getTaskPeriod());
        taskInfo.setOpType(task.getOpType());
        taskInfo.setDataType(task.getDataType());
        taskInfo.setApiMethod(task.getApiMethod());
        taskInfo.setApiUrl(task.getApiUrl());

        // 所属租户
        taskInfo.setTenantId(task.getTenantId());
        // 字段层级前缀
        taskInfo.setApiFieldPrefix(task.getApiFieldPrefix());
        // 字段映射
        taskInfo.setFieldMapping(task.getFieldMapping());

        // 数据项id
        taskInfo.setDataId(task.getDataId());
        // 数据项编号
        taskInfo.setDataCode(task.getDataCode());

        // 唯一标识字段编号
        taskInfo.setIdFieldCode(task.getIdFieldCode());

        // 是否为订阅任务
        taskInfo.setIsSubscribed(task.getIsSubscribed());

        // header
        taskInfo.setReqHeaders(task.getReqHeaders());
        taskInfo.setOriginReqHeaders(task.getReqHeaders());
        // param
        Map<String, String> taskParams = task.getReqParams();
        if (CollUtil.isNotEmpty(taskParams)) {
            Map<String, Object> jobParams = MapUtil.newHashMap();
            jobParams.putAll(taskParams);
            taskInfo.setReqParams(jobParams);
            taskInfo.setOriginReqParams(jobParams);
        }
        // field var mapping
        taskInfo.setFieldVarMapping(task.getFieldVarMapping());

        // 数据过滤条件
        taskInfo.setDataFilters(parseBizDataCriteria(task.getDataFilter()));

        return taskInfo;
    }

    /**
     * 根据 任务的上次执行时间 和 设定间隔规则，计算任务的 下次执行时间
     *
     * @param taskInfo 定时任务
     */
    private void calculateNextRunTime(TaskInfo taskInfo) {
        Assert.notNull(taskInfo);
        Assert.notEmpty(taskInfo.getTaskPeriod());

        Date date = taskInfo.getStartTime();
        if (taskInfo.getFailCount() > 0) {
            date = taskInfo.getNextRunTime();
        }

        CronExpression cronExpression = new CronExpression(taskInfo.getTaskPeriod());
        Date nextRunTime = cronExpression.getNextValidTimeAfter(date);
        taskInfo.setNextRunTime(nextRunTime);

        taskInfo.appendLog("预计执行时间：{}", DateUtil.formatDateTime(taskInfo.getNextRunTime()));
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

    private TaskLog getTaskLog(TaskInfo taskInfo) {
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(taskInfo.getId());
        taskLog.setTaskStartTime(taskInfo.getStartTime());
        taskLog.setTaskEndTime(taskInfo.getEndTime());
        taskLog.setTaskResult(taskInfo.getExecuteResult());
        taskLog.setTaskDetail(taskInfo.getLog().toString());
        return taskLog;
    }

    /**
     * 将数据库中的过滤条件 转为封装类结构
     */
    private List<BizDataFilter> parseBizDataCriteria(List<Map<String, String>> dataFilterList) {
        if (CollUtil.isEmpty(dataFilterList)) {
            return null;
        }

        List<BizDataFilter> bizDataFilters = CollUtil.newArrayList();
        for (Map<String, String> map : dataFilterList) {
            BizDataFilter bizDataFilter = new BizDataFilter();
            bizDataFilter.setKey(map.get(MdConstant.DATA_KEY));
            bizDataFilter.setOp(map.get(MdConstant.DATA_OP));
            bizDataFilter.setValue(map.get(MdConstant.DATA_VALUE));
            bizDataFilters.add(bizDataFilter);
        }

        return bizDataFilters;
    }
}
