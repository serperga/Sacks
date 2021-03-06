package com.sacks.codeexercise.model.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @ApiModelProperty(notes = "status identifier",name="statusId",required=true,value="1")
    private int statusId;
    @ApiModelProperty(notes = "status of the order (Delivered,Ordered, Cancelled, ...)",name="status",required=true,value="1")
    private String status;

    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public OrderStatus(){
    }

    public OrderStatus(int statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public OrderStatus(int statusId, String status,
        Set<OrderStatusHistory> orderStatusHistories) {
        this.statusId = statusId;
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
