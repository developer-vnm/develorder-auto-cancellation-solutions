package io.github.order.orderservice.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.order.orderservice.constants.DistributedCacheConstants.*;

@Configuration
@Slf4j
public class RedisDistributedCacheConfig implements CachingConfigurer {

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisDistributedCacheErrorHandler();
    }

    @Bean
    public CacheManager redisCacheManager(final RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(createCacheConfiguration(DEFAULT_TTL))
                .withInitialCacheConfigurations(
                        createCustomCacheConfigurations(Map.of(ORDER_EXPIRATION_CACHE, ORDER_EXPIRATION_TTL)))
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory redisConnectionFactory) {

        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return template;
    }

    private static Map<String, RedisCacheConfiguration> createCustomCacheConfigurations(final Map<String, Long> specificCacheExpirations) {

        if (null == specificCacheExpirations) {
            return Map.of();
        }

        return specificCacheExpirations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> createCacheConfiguration(e.getValue())));
    }

    private static RedisCacheConfiguration createCacheConfiguration(final long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(timeoutInSeconds))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Slf4j
    @SuppressWarnings("all")
    static class RedisDistributedCacheErrorHandler implements CacheErrorHandler {

        @Override
        public void handleCacheGetError(final RuntimeException exception,
                                        final Cache cache,
                                        final Object key) {
            log.error("[redis] cache_get_error={}", exception.getMessage());
        }

        @Override
        public void handleCachePutError(final RuntimeException exception,
                                        final Cache cache,
                                        final Object key,
                                        final Object value) {
            log.error("[redis] cache_put_error={}", exception.getMessage());
        }

        @Override
        public void handleCacheEvictError(final RuntimeException exception,
                                          final Cache cache,
                                          final Object key) {
            log.error("[redis] cache_evict_error={}", exception.getMessage());
        }

        @Override
        public void handleCacheClearError(final RuntimeException exception,
                                          final Cache cache) {
            log.error("[redis] cache_clear_error={}", exception.getMessage());
        }
    }
}