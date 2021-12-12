package com.example.ordermanagement.infrastructure.repository;

import com.example.ordermanagement.infrastructure.repository.entity.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNo(String orderNo);
}
