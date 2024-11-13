package io.github.order.orderservice.services.impl;

import io.github.order.orderservice.constants.DistributedCacheConstants;
import io.github.order.orderservice.services.DistributedCacheService;
import io.github.order.orderservice.services.OrderCancellationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCancellationServiceImpl implements OrderCancellationService {

    private final DistributedCacheService distributedCacheService;

    @Override
    @Async
    public void storeScheduledOrderCancellation(final long orderId) {
        log.info("[application] prepare to store scheduled order cancellation order_id={}", orderId);
        distributedCacheService.set(DistributedCacheConstants.ORDER_EXPIRATION_CACHE, String.valueOf(orderId), "");
    }
}