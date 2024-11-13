package io.github.order.orderservice.services;

public interface OrderCancellationService {

    void storeScheduledOrderCancellation(long orderId);
}