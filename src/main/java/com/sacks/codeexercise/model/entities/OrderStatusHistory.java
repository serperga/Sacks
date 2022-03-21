package com.sacks.codeexercise.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "order_status_history")
@IdClass(OrderStatusHistoryKey.class)
public class OrderStatusHistory {

    @Id
    @ApiModelProperty(notes = "order identifier",name="orderId",required=true,value="1")
    private long orderId;
    @ApiModelProperty(notes = "status identifier",name="statusId",required=true,value="1")
    @Id
    private int statusId;

    @ApiModelProperty(notes = "customer",name="username",required=true,value="test user")
    private String username;
    @ApiModelProperty(notes = "status completed in days",name="completedStatusInDays",required=false,value="1")
    private int completedStatusInDays;
    @ApiModelProperty(notes = "price of the order",name="orderAmount",required=false,value="150.35")
    private double orderAmount;

    public OrderStatusHistory() {
    }

    public OrderStatusHistory(long orderId, int statusId, String username, int completedStatusInDays, Double orderAmount) {
        this.orderId = orderId;
        this.statusId = statusId;
        this.username = username;
        this.completedStatusInDays = completedStatusInDays;
        this.orderAmount = orderAmount;
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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
