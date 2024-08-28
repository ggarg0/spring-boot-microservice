package com.demo.microservices.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.microservices.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}