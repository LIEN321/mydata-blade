package org.springblade.mydata.job.feign;

import org.springblade.common.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调度任务Feign接口
 *
 * @author LIEN
 * @since 2022/7/16
 */
@FeignClient(value = AppConstant.APPLICATION_MYDATA_JOB)
public interface IJobClient {

    String API_PREFIX = "/api/job";
    String START_TASK = API_PREFIX + "/start";
    String STOP_TASK = API_PREFIX + "/stop";
    String RESTART_TASK = API_PREFIX + "/restart";

    /**
     * 启动任务
     *
     * @param id 任务id
     * @return 操作结果，true-成功，false-失败
     */
    @GetMapping(START_TASK)
    boolean startTask(@RequestParam("id") Long id);

    /**
     * 停止任务
     *
     * @param id 任务id
     * @return 操作结果，true-成功，false-失败
     */
    @GetMapping(STOP_TASK)
    boolean stopTask(@RequestParam("id") Long id);

    /**
     * 重启任务
     *
     * @param id 任务id
     * @return 操作结果，true-成功，false-失败
     */
    @GetMapping(RESTART_TASK)
    boolean restartTask(@RequestParam("id") Long id);
}
