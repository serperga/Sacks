package com.sacks.codeexercise.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class OrderRefundInformation {

    @ApiModelProperty(notes = "order identifier",name="orderId",required=true,value="1")
    private long orderId;
    @ApiModelProperty(notes = "estimated time to finish this status and go to the next status",name="estimatedDays",required=true,value="1")
    private int estimatedDays;
    @ApiModelProperty(notes = "total amount to pay for the order",name="amount",required=true,value="150.12")
    private Double amount;
    @ApiModelProperty(notes = "customer username",name="buyer",required=true,value="customer 1")
    private String buyer;
    @ApiModelProperty(notes = "order Status. The values are: Ordered, ent to Warehouse, Packaged, Carrier picked up,"
        + " Out for delivery, Delivered, Cancelled. Not Enough stock, Cancelled. Not Enough money in customer wallet, "
        + "Cancelled. Not products in order after refund"
        ,name="orderStatus",required=true,value="Ordered")
    private String orderStatus;
    @ApiModelProperty(notes = "List of products in the order",name="products",required=true)
    private List<ProductInformation> products = new ArrayList<>();
    @ApiModelProperty(notes = "order amount after refund the product returned",name="amountInWalletAfterRefund",required=true,value="180.50")
    private Double amountInWalletAfterRefund;

    public OrderRefundInformation() {
    }

    public OrderRefundInformation(long orderId, int estimatedDays, Double amount, String buyer,
        String orderStatus, List<ProductInformation> products, Double amountInWalletAfterRefund) {
        this.orderId = orderId;
        this.estimatedDays = estimatedDays;
        this.amount = amount;
        this.buyer = buyer;
        this.orderStatus = orderStatus;
        this.products = products;
        this.amountInWalletAfterRefund = amountInWalletAfterRefund;
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

    public Double getAmountInWalletAfterRefund() {
        return amountInWalletAfterRefund;
    }

    public void setAmountInWalletAfterRefund(Double amountInWalletAfterRefund) {
        this.amountInWalletAfterRefund = amountInWalletAfterRefund;
    }
}
