package io.github.order.orderservice.services.impl;

import io.github.order.orderservice.dtos.OrderRequestDTO;
import io.github.order.orderservice.dtos.OrderResponseDTO;
import io.github.order.orderservice.services.OrderCancellationService;
import io.github.order.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderCancellationService orderCancellationService;

    @Override
    public OrderResponseDTO createOrder(final OrderRequestDTO requestDTO) {

        // database processing
        var orderId = System.currentTimeMillis();

        orderCancellationService.storeScheduledOrderCancellation(orderId);

        return new OrderResponseDTO(orderId);
    }
}