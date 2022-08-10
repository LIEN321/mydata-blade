package org.springblade.mydata.job.redis;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.mydata.job.executor.JobCache;
import org.springblade.mydata.job.executor.JobExecutor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * redis key过期监听器
 *
 * @author LIEN
 * @date 2022/7/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisKeyExpiredListener implements MessageListener {

    private final JobExecutor jobExecutor;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if (StrUtil.startWith(expiredKey, JobCache.CACHE_JOB)) {
            String taskId = StrUtil.subSuf(expiredKey, JobCache.CACHE_JOB.length());
            jobExecutor.notify(taskId);
        }
    }
}
