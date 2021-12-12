package com.example.ordermanagement.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.ordermanagement.OrderManagementApplication;
import com.example.ordermanagement.infrastructure.entity.Order;
import com.example.ordermanagement.infrastructure.entity.OrderItem;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderManagementApplication.class)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void should_save_order_success() {
        Order savedOrder = orderRepository.save(
                Order.builder().orderNo("O01").items(Collections.singletonList(
                        OrderItem.builder().code("A01").quantity(1).build())).build());

        assertNotNull(savedOrder.getId());
    }
}