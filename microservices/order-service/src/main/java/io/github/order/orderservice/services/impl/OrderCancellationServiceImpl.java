package io.github.order.orderservice.services.impl;

import io.github.order.orderservice.constants.DistributedCacheConstants;
import io.github.order.orderservice.services.OrderCancellationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCancellationServiceImpl implements OrderCancellationService {

    private final CacheManager cacheManager;

    @Override
    @Async
    public void storeScheduledOrderCancellation(final long orderId) {
        log.info("[application] prepare to store scheduled order cancellation order_id={}", orderId);
        Optional.ofNullable(cacheManager.getCache(DistributedCacheConstants.ORDER_EXPIRATION_CACHE))
                .ifPresent(cache -> cache.put(orderId, ""));
    }
}