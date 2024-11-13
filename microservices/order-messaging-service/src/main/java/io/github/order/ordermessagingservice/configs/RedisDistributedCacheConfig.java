package io.github.order.ordermessagingservice.configs;

import io.github.order.ordermessagingservice.constants.DistributedCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
@Slf4j
public class RedisDistributedCacheConfig implements CachingConfigurer {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory redisConnectionFactory) {

        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(final RedisConnectionFactory redisConnectionFactory,
                                                                       final MessageListenerAdapter messageListenerAdapter) {
        var container = new RedisMessageListenerContainer();

        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(DistributedCacheConstants.REDIS_EXPIRE_KEY_CHANNEL_PATTERN));

        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(final MessageListener messageListener) {
        return new MessageListenerAdapter(messageListener);
    }
}