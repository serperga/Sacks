package com.sacks.codeexercise.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class OrderStatusHistoryKey implements Serializable {

    private long orderId;
    private int statusId;

    public OrderStatusHistoryKey() {
    }

    public OrderStatusHistoryKey(long orderId, int statusId) {
        this.orderId = orderId;
        this.statusId = statusId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderStatusHistoryKey that = (OrderStatusHistoryKey) o;
        return orderId == that.orderId && statusId == that.statusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, statusId);
    }
}
