package org.springblade.modules.mydata.job.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * redis 配置类
 *
 * @author LIEN
 * @since 2022/7/15
 */
@Configuration
public class RedisConfiguration {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private RedisKeyExpiredListener redisKeyExpiredListener;
    @Value("${spring.redis.database}")
    private Integer db;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(redisKeyExpiredListener, new PatternTopic("__keyevent@" + db + "__:expired"));
        return redisMessageListenerContainer;
    }

}
