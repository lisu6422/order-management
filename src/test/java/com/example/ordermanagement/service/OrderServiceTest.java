package com.example.ordermanagement.service;

import static com.example.ordermanagement.infrastructure.client.dto.FoodStatus.ON_SHELVE;
import static com.example.ordermanagement.infrastructure.client.dto.FoodStatus.UNDER_CARRIAGE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.ordermanagement.bo.OrderBO;
import com.example.ordermanagement.bo.OrderItemBO;
import com.example.ordermanagement.exception.FoodsIsUnderCarriageException;
import com.example.ordermanagement.exception.FoodsNotFoundException;
import com.example.ordermanagement.infrastructure.client.FoodClient;
import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import com.example.ordermanagement.infrastructure.entity.Order;
import com.example.ordermanagement.infrastructure.repository.OrderRepository;
import com.rabbitmq.http.client.HttpException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

    @Mock
    private FoodClient foodClient;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_order_success() {
        when(foodClient.findByCodes(any())).thenReturn(
                Collections.singletonList(FoodDTO.builder().code("A01").status(ON_SHELVE).build()));
        when(orderRepository.save(any())).thenReturn(Order.builder().orderNo("O01").build());

        OrderBO orderBO = OrderBO.builder()
                .items(Collections.singletonList(
                        OrderItemBO.builder().code("A01").quantity(1).build()))
                .build();

        String orderNumber = orderService.createOrder(orderBO);
        assertNotNull(orderNumber);
    }

    @Test
    void should_create_order_failed_when_food_not_found() {
        when(foodClient.findByCodes(any())).thenReturn(Collections.EMPTY_LIST);

        OrderBO orderBO = OrderBO.builder()
                .items(Collections.singletonList(
                        OrderItemBO.builder().code("A01").quantity(1).build()))
                .build();

        assertThrows(FoodsNotFoundException.class, () -> orderService.createOrder(orderBO));
    }

    @Test
    void should_create_order_failed_when_food_is_UNDER_CARRIAGE() {
        when(foodClient.findByCodes(any())).thenReturn(
                Collections.singletonList(
                        FoodDTO.builder().code("A01").status(UNDER_CARRIAGE).build()));

        OrderBO orderBO = OrderBO.builder()
                .items(Collections.singletonList(
                        OrderItemBO.builder().code("A01").quantity(1).build()))
                .build();

        assertThrows(FoodsIsUnderCarriageException.class, () -> orderService.createOrder(orderBO));
    }

    @Test
    void should_create_order_failed_when_food_server_is_not_available() {
        when(foodClient.findByCodes(any())).thenThrow(
                new HttpException("503 food server not available"));

        OrderBO orderBO = OrderBO.builder()
                .items(Collections.singletonList(
                        OrderItemBO.builder().code("A01").quantity(1).build()))
                .build();

        assertThrows(HttpException.class, () -> orderService.createOrder(orderBO));
    }
}