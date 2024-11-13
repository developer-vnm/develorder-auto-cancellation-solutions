package io.github.order.orderservice.services;

import io.github.order.orderservice.dtos.OrderRequestDTO;
import io.github.order.orderservice.dtos.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO requestDTO);
}