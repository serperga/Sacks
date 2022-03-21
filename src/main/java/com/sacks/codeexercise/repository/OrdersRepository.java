package com.sacks.codeexercise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.Order;

public interface OrdersRepository extends JpaRepository<Order,Long> {
    Optional<Order> findOrderByOrderId(long orderId);
}
