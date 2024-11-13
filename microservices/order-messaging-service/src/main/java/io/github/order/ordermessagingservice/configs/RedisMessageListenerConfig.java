package io.github.order.ordermessagingservice.configs;

import io.github.order.ordermessagingservice.constants.DistributedCacheConstants;
import io.github.order.ordermessagingservice.listeners.RedisExpiredKeyEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class RedisMessageListenerConfig {

    private final RedisExpiredKeyEventListener redisExpiredKeyEventListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(final RedisConnectionFactory redisConnectionFactory) {

        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // handle expired key events
        container.addMessageListener(
                redisExpiredKeyEventListener,
                new PatternTopic(DistributedCacheConstants.REDIS_EXPIRE_KEY_CHANNEL_PATTERN));

        return container;
    }
}