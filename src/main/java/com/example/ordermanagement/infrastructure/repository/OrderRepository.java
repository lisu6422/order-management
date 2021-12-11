package com.example.ordermanagement.infrastructure.repository;

import com.example.ordermanagement.infrastructure.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
