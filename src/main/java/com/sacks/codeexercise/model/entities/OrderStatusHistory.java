package com.sacks.codeexercise.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "order_status_history")
@IdClass(OrderStatusHistoryKey.class)
public class OrderStatusHistory {

    @Id
    private long orderId;
    @Id
    private int statusId;

    private String username;
    private int completedStatusInDays;

    public OrderStatusHistory() {
    }

    public OrderStatusHistory(long orderId, int statusId, String username, int completedStatusInDays) {
        this.orderId = orderId;
        this.statusId = statusId;
        this.username = username;
        this.completedStatusInDays = completedStatusInDays;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCompletedStatusInDays() {
        return completedStatusInDays;
    }

    public void setCompletedStatusInDays(int completedStatusInDays) {
        this.completedStatusInDays = completedStatusInDays;
    }
}
