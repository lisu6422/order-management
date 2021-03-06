package com.example.ordermanagement.controller;

import static com.example.ordermanagement.utils.mapper.OrderMapper.ORDER_MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.ordermanagement.controller.dto.CreateOrderDTO;
import com.example.ordermanagement.controller.dto.OrderResponse;
import com.example.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        String orderNumber = orderService.createOrder(ORDER_MAPPER.toOrderBO(createOrderDTO));
        return OrderResponse.builder().orderNo(orderNumber).build();
    }

    @PostMapping("/{orderNo}/cancellation")
    public void cancelOrder(@PathVariable String orderNo) {
        orderService.cancelOrder(orderNo);
    }
}
