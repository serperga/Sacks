package com.sacks.codeexercise.model;

import java.util.ArrayList;
import java.util.List;

import com.sacks.codeexercise.model.entities.Product;


public class Order {

    private long orderId;
    private int estimatedDays;
    private Double amount;
    private String buyer;
    private String orderStatus;
    private List<Product> products = new ArrayList<>();

    public Order(){}

    public Order(long orderId, int estimatedDays, Double amount, String buyer, String orderStatus,
        List<Product> products) {
        this.orderId = orderId;
        this.estimatedDays = estimatedDays;
        this.amount = amount;
        this.buyer = buyer;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(int estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
