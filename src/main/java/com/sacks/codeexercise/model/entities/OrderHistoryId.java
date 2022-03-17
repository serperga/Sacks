package com.sacks.codeexercise.model.entities;

import java.io.Serializable;

public class OrderHistoryId implements Serializable {
    private long orderId;
    private int orderStatus;

    public OrderHistoryId() {
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
}
