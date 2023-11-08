package org.springblade.mydata.job.feign;

import lombok.AllArgsConstructor;
import org.springblade.mydata.job.executor.JobExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度任务Feign 实现类
 *
 * @author LIEN
 * @since 2022/7/16
 */
@RestController
@AllArgsConstructor
public class JobClientImpl implements IJobClient {

    private final JobExecutor jobExecutor;

    @Override
    @GetMapping(START_TASK)
    public boolean startTask(Long id) {
        jobExecutor.startTask(id);
        return true;
    }

    @Override
    @GetMapping(STOP_TASK)
    public boolean stopTask(Long id) {
        jobExecutor.stopTask(id);
        return true;
    }

    @Override
    @GetMapping(RESTART_TASK)
    public boolean restartTask(Long id) {
        return stopTask(id) && startTask(id);
    }
}
