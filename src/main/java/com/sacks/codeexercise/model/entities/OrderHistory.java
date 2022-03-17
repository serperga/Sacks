package com.sacks.codeexercise.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "order_history")
@IdClass(OrderHistoryId.class)
public class OrderHistory {

    @Id
    private long orderId;
    @Id
    private int orderStatus;
    private int orderStatusCompletedInDays;

    public OrderHistory() {
    }

    public OrderHistory(long orderId, int orderStatus, int orderStatusCompletedInDays) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderStatusCompletedInDays = orderStatusCompletedInDays;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatusCompletedInDays() {
        return orderStatusCompletedInDays;
    }

    public void setOrderStatusCompletedInDays(int orderStatusCompletedInDays) {
        this.orderStatusCompletedInDays = orderStatusCompletedInDays;
    }
}
