package com.example.ordermanagement.service;

import static com.example.ordermanagement.infrastructure.client.dto.FoodStatus.ON_SHELVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.ordermanagement.bo.OrderBO;
import com.example.ordermanagement.bo.OrderItemBO;
import com.example.ordermanagement.dto.CreateOrderDTO;
import com.example.ordermanagement.dto.CreateOrderItemDTO;
import com.example.ordermanagement.infrastructure.client.FoodClient;
import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import com.example.ordermanagement.infrastructure.entity.Order;
import com.example.ordermanagement.infrastructure.repository.OrderRepository;
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
                Collections.singletonList(FoodDTO.builder().status(ON_SHELVE).build()));
        when(orderRepository.save(any())).thenReturn(Order.builder().orderNo("O01").build());

        OrderBO orderBO = OrderBO.builder()
                .items(Collections.singletonList(
                        OrderItemBO.builder().code("A01").quantity(1).build()))
                .build();

        String orderNumber = orderService.createOrder(orderBO);
        assertNotNull(orderNumber);
    }
}