package io.github.order.orderservice.services.impl;

import io.github.order.orderservice.services.DistributedCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisDistributedCacheService implements DistributedCacheService {

    private final CacheManager cacheManager;

    @Override
    public void set(final String name, final String key, final Object value) {

        var cache = cacheManager.getCache(name);

        if (cache == null) {
            log.error("[redis] could not found any cache by name={}", name);
            return;
        }

        cache.put(key, value);
    }
}