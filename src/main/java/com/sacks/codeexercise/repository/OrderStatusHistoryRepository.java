package com.sacks.codeexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.OrderStatusHistoryKey;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, OrderStatusHistoryKey> {

}
