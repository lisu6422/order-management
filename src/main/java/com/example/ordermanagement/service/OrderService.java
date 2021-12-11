package com.example.ordermanagement.service;

import static com.example.ordermanagement.mapper.OrderMapper.ORDER_MAPPER;

import com.example.ordermanagement.bo.OrderBO;
import com.example.ordermanagement.infrastructure.client.FoodClient;
import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import com.example.ordermanagement.infrastructure.entity.Order;
import com.example.ordermanagement.infrastructure.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final FoodClient foodClient;
    private final OrderRepository orderRepository;

    public String createOrder(OrderBO orderBO) {
        List<String> foodCodes = orderBO.getItems().stream()
                .map(item -> item.getCode())
                .collect(Collectors.toList());
        List<FoodDTO> foodDTOS = foodClient.findByCodes(foodCodes);
        Order createdOrder = orderRepository.save(ORDER_MAPPER.toOrder(orderBO));
        return createdOrder.getOrderNo();
    }
}
