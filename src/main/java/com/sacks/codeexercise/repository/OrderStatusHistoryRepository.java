package com.sacks.codeexercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.OrderStatusHistoryKey;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, OrderStatusHistoryKey> {
    Optional<List<OrderStatusHistory>> findOrderStatusHistoryByStatusId(int statusId);
    Optional<List<OrderStatusHistory>> findOrderStatusHistoryByOrderIdOrderByStatusIdAsc(Long orderId);
}
