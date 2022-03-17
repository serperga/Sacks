package com.sacks.codeexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sacks.codeexercise.model.entities.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Integer> {

}
