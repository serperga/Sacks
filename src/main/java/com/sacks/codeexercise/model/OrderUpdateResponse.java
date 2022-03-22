package com.sacks.codeexercise.model;

import java.util.ArrayList;
import java.util.List;


public class OrderUpdateResponse {

    private long orderId;
    private int estimatedDays;
    private Double amount;
    private String buyer;
    private String orderStatus;
    private List<ProductInformation> products = new ArrayList<ProductInformation>();

    public OrderUpdateResponse(){}

    public OrderUpdateResponse(long orderId, int estimatedDays, Double amount, String buyer, String orderStatus,
        List<ProductInformation> products) {
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

    public List<ProductInformation> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInformation> products) {
        this.products = products;
    }
}
