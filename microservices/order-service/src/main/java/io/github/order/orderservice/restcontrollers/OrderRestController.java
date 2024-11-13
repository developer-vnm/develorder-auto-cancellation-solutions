package io.github.order.orderservice.restcontrollers;

import io.github.order.orderservice.dtos.OrderRequestDTO;
import io.github.order.orderservice.dtos.OrderResponseDTO;
import io.github.order.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO requestDTO) {
        log.info("[server-request] create order order_request={}", requestDTO);
        var createdOrder = orderService.createOrder(requestDTO);
        log.info("[server-response] created order order_response={}", createdOrder);
        return createdOrder;
    }
}