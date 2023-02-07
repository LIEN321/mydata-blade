package org.springblade.mydata.job.executor;

import lombok.extern.slf4j.Slf4j;
import org.springblade.common.constant.MdConstant;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.mydata.data.BizDataDAO;
import org.springblade.mydata.job.bean.TaskJob;
import org.springblade.mydata.job.util.ApiUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 执行任务的线程
 *
 * @author LIEN
 * @date 2022/07/16
 */
@Slf4j
public class JobThread implements Runnable {

    private final TaskJob taskJob;
    private final JobDataFilter jobDataFilter = new JobDataFilter();

    public JobThread(TaskJob taskJob) {
        this.taskJob = taskJob;
    }

    @Override
    public void run() {
        taskJob.appendLog("任务开始执行");

        JobDataService jobDataService = SpringUtil.getBean(JobDataService.class);
        BizDataDAO bizDataDAO = SpringUtil.getBean(BizDataDAO.class);
        JobExecutor jobExecutor = SpringUtil.getBean(JobExecutor.class);

        // 标记本次执行是否成功
        boolean isJobSuccess = false;

        // 获取任务操作类型
        int opType = taskJob.getOpType();

        try {
            // 根据操作类型 执行读或写
            switch (opType) {
                case MdConstant.DATA_PRODUCER:
                    // 调用api 获取json
                    String json = ApiUtil.read(taskJob);
                    // 将json按字段映射 解析为业务数据
                    jobDataService.parseData(taskJob, json);
                    // 根据条件过滤数据
                    jobDataFilter.doFilter(taskJob);
                    // 保存业务数据
                    jobDataService.saveTaskData(taskJob);

                    break;
                case MdConstant.DATA_CONSUMER:
                    // 根据过滤条件 查询数据
                    List<Map> dataList = bizDataDAO.list(taskJob.getTenantId(), taskJob.getDataCode(), taskJob.getDataFilters());
                    taskJob.setConsumeDataList(dataList);
                    // 根据字段映射转换为api参数
                    jobDataService.convertData(taskJob);
                    // 调用api传输数据
                    ApiUtil.write(taskJob);
                    break;
                default:
                    throw new RuntimeException("不支持的任务类型：" + opType);
            }

            // 任务执行成功
            isJobSuccess = true;
        } catch (Exception e) {
            taskJob.appendLog("任务执行失败，异常：{}", e.getMessage());
        }

        // 标记任务失败次数是否到达上限
        boolean isTaskFailed = false;

        if (isJobSuccess) {
            taskJob.setExecuteResult(MdConstant.TASK_RESULT_SUCCESS);
            // 调用API成功后，重置错误次数
            taskJob.setFailCount(0);
            taskJob.appendLog("任务执行成功");
        } else {
            taskJob.setExecuteResult(MdConstant.TASK_RESULT_FAILED);
            // 累加失败次数
            int failCount = taskJob.getFailCount();
            failCount++;

            // 小于失败上限，可继续执行
            if (failCount < MdConstant.TASK_MAX_FAIL_COUNT) {
                taskJob.setFailCount(failCount);
                taskJob.appendLog("任务第{}次失败", failCount);
            } else {
                // 标记任务异常
                isTaskFailed = true;
                taskJob.appendLog("任务失败达到{}次，将终止且不再执行", failCount);
            }
        }

        taskJob.setEndTime(new Date());

        jobExecutor.saveLog(taskJob);

        if (isTaskFailed) {
            // 更新任务状态为异常
            jobExecutor.fail(taskJob);
        } else {
            if (!MdConstant.TASK_IS_SUBSCRIBED.equals(taskJob.getIsSubscribed())) {
                // 继续执行任务
                jobExecutor.continueJob(taskJob);
                // 执行订阅任务
                jobExecutor.executeSubscribedTask(taskJob);
            }
        }
    }
}