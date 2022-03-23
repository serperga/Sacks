package com.sacks.codeexercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sacks.codeexercise.model.entities.Order;

public interface OrdersRepository extends JpaRepository<Order,Long> {
    Optional<Order> findOrderByOrderId(long orderId);
    @Query(value = "SELECT * FROM ORDERS WHERE status_id = ?1 and username = ?2",
        nativeQuery = true)
    Optional<List<Order>> findOrdersByStatusAndCustomer(int statusId, String username);
}
