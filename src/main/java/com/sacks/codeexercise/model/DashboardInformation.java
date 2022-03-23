package com.sacks.codeexercise.model;

public class DashboardInformation {

    private String customer;
    private Double initialAmountInWallet;
    private Double currentAmountInWallet;
    private Long orderID;
    private String status;
    private int daysToCompleteOrderedStatus;
    private int daysToCompleteSentToWareHouseStatus;
    private int daysToCompleteCarrierPickedUpStatus;
    private int daysToCompleteOutForDeliveryStatus;

    public DashboardInformation() {
    }

    public DashboardInformation(String customer, Double initialAmountInWallet, Double currentAmountInWallet,
        Long orderID, String status, int daysToCompleteOrderedStatus, int daysToCompleteSentToWareHouseStatus,
        int daysToCompleteCarrierPickedUpStatus, int daysToCompleteOutForDeliveryStatus) {
        this.customer = customer;
        this.initialAmountInWallet = initialAmountInWallet;
        this.currentAmountInWallet = currentAmountInWallet;
        this.orderID = orderID;
        this.status = status;
        this.daysToCompleteOrderedStatus = daysToCompleteOrderedStatus;
        this.daysToCompleteSentToWareHouseStatus = daysToCompleteSentToWareHouseStatus;
        this.daysToCompleteCarrierPickedUpStatus = daysToCompleteCarrierPickedUpStatus;
        this.daysToCompleteOutForDeliveryStatus = daysToCompleteOutForDeliveryStatus;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getInitialAmountInWallet() {
        return initialAmountInWallet;
    }

    public void setInitialAmountInWallet(Double initialAmountInWallet) {
        this.initialAmountInWallet = initialAmountInWallet;
    }

    public Double getCurrentAmountInWallet() {
        return currentAmountInWallet;
    }

    public void setCurrentAmountInWallet(Double currentAmountInWallet) {
        this.currentAmountInWallet = currentAmountInWallet;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDaysToCompleteOrderedStatus() {
        return daysToCompleteOrderedStatus;
    }

    public void setDaysToCompleteOrderedStatus(int daysToCompleteOrderedStatus) {
        this.daysToCompleteOrderedStatus = daysToCompleteOrderedStatus;
    }

    public int getDaysToCompleteSentToWareHouseStatus() {
        return daysToCompleteSentToWareHouseStatus;
    }

    public void setDaysToCompleteSentToWareHouseStatus(int daysToCompleteSentToWareHouseStatus) {
        this.daysToCompleteSentToWareHouseStatus = daysToCompleteSentToWareHouseStatus;
    }

    public int getDaysToCompleteCarrierPickedUpStatus() {
        return daysToCompleteCarrierPickedUpStatus;
    }

    public void setDaysToCompleteCarrierPickedUpStatus(int daysToCompleteCarrierPickedUpStatus) {
        this.daysToCompleteCarrierPickedUpStatus = daysToCompleteCarrierPickedUpStatus;
    }

    public int getDaysToCompleteOutForDeliveryStatus() {
        return daysToCompleteOutForDeliveryStatus;
    }

    public void setDaysToCompleteOutForDeliveryStatus(int daysToCompleteOutForDeliveryStatus) {
        this.daysToCompleteOutForDeliveryStatus = daysToCompleteOutForDeliveryStatus;
    }
}
