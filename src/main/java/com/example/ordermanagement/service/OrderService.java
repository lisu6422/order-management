package com.example.ordermanagement.service;

import static com.example.ordermanagement.infrastructure.client.dto.FoodStatus.UNDER_CARRIAGE;
import static com.example.ordermanagement.utils.mapper.OrderMapper.ORDER_MAPPER;
import static java.util.stream.Collectors.toList;

import com.example.ordermanagement.service.bo.OrderBO;
import com.example.ordermanagement.exception.FoodsIsUnderCarriageException;
import com.example.ordermanagement.exception.FoodsNotFoundException;
import com.example.ordermanagement.exception.OrderCanNotCancelException;
import com.example.ordermanagement.exception.OrderNoFoundException;
import com.example.ordermanagement.infrastructure.client.FoodClient;
import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import com.example.ordermanagement.infrastructure.repository.entity.Order;
import com.example.ordermanagement.infrastructure.mq.OrderCancelRabbitSender;
import com.example.ordermanagement.infrastructure.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final FoodClient foodClient;
    private final OrderRepository orderRepository;
    private final OrderCancelRabbitSender orderCancelRabbitSender;

    public String createOrder(OrderBO orderBO) {
        List<String> foodCodes = orderBO.getItems().stream()
                .map(item -> item.getCode())
                .collect(toList());
        List<FoodDTO> foodDTOS = foodClient.findByCodes(foodCodes);
        List<String> selectedFoodCodes = foodDTOS.stream()
                .map(food -> food.getCode())
                .collect(toList());
        List foodsNotExisted = foodCodes.stream().filter(item -> !selectedFoodCodes.contains(item))
                .collect(toList());
        if (!foodsNotExisted.isEmpty()) {
            log.error("Foods not found: {}", foodsNotExisted);
            throw new FoodsNotFoundException();
        }
        foodDTOS.stream().forEach(food -> {
            if (food.getStatus() == UNDER_CARRIAGE) {
                log.error("Foods is UNDER_CARRIAGE: {}", food.getCode());
                throw new FoodsIsUnderCarriageException();
            }
        });
        Order createdOrder = orderRepository.save(ORDER_MAPPER.toOrder(orderBO));
        return createdOrder.getOrderNo();
    }

    public void cancelOrder(String orderNo) {
        Optional<Order> orderOptional = orderRepository.findByOrderNo(orderNo);
        if (orderOptional.isEmpty()) {
            throw new OrderNoFoundException();
        }
        Order order = orderOptional.get();
        if (!order.canBeCancel()) {
            throw new OrderCanNotCancelException();
        }
        order.cancel();
        Order canceledOrder = orderRepository.save(order);

        orderCancelRabbitSender.send(canceledOrder, null);
    }
}
