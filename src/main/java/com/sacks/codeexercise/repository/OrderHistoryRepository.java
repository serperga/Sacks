package com.sacks.codeexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.OrderHistory;
import com.sacks.codeexercise.model.entities.OrderHistoryId;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, OrderHistoryId> {

}
