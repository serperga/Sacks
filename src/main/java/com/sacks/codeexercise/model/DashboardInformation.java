package com.sacks.codeexercise.model;

public class DashboardInformation {

    private String customer;
    private String initialAmountInWallet;
    private String currentAmountInWallet;
    private String orderID;
    private String status;
    private String productsInOrder;
    private String daysToCompleteOrderedStatus;
    private String daysToCompleteSentToWareHouseStatus;
    private String daysToCompleteCarrierPickedUpStatus;
    private String daysToCompleteOutForDeliveryStatus;

    public DashboardInformation() {
    }

    public DashboardInformation(String customer, String initialAmountInWallet, String currentAmountInWallet,
        String orderID, String status, String productsInOrder, String daysToCompleteOrderedStatus,
        String daysToCompleteSentToWareHouseStatus, String daysToCompleteCarrierPickedUpStatus,
        String daysToCompleteOutForDeliveryStatus) {
        this.customer = customer;
        this.initialAmountInWallet = initialAmountInWallet;
        this.currentAmountInWallet = currentAmountInWallet;
        this.orderID = orderID;
        this.status = status;
        this.productsInOrder = productsInOrder;
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

    public String getInitialAmountInWallet() {
        return initialAmountInWallet;
    }

    public void setInitialAmountInWallet(String initialAmountInWallet) {
        this.initialAmountInWallet = initialAmountInWallet;
    }

    public String getCurrentAmountInWallet() {
        return currentAmountInWallet;
    }

    public void setCurrentAmountInWallet(String currentAmountInWallet) {
        this.currentAmountInWallet = currentAmountInWallet;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(String productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public String getDaysToCompleteOrderedStatus() {
        return daysToCompleteOrderedStatus;
    }

    public void setDaysToCompleteOrderedStatus(String daysToCompleteOrderedStatus) {
        this.daysToCompleteOrderedStatus = daysToCompleteOrderedStatus;
    }

    public String getDaysToCompleteSentToWareHouseStatus() {
        return daysToCompleteSentToWareHouseStatus;
    }

    public void setDaysToCompleteSentToWareHouseStatus(String daysToCompleteSentToWareHouseStatus) {
        this.daysToCompleteSentToWareHouseStatus = daysToCompleteSentToWareHouseStatus;
    }

    public String getDaysToCompleteCarrierPickedUpStatus() {
        return daysToCompleteCarrierPickedUpStatus;
    }

    public void setDaysToCompleteCarrierPickedUpStatus(String daysToCompleteCarrierPickedUpStatus) {
        this.daysToCompleteCarrierPickedUpStatus = daysToCompleteCarrierPickedUpStatus;
    }

    public String getDaysToCompleteOutForDeliveryStatus() {
        return daysToCompleteOutForDeliveryStatus;
    }

    public void setDaysToCompleteOutForDeliveryStatus(String daysToCompleteOutForDeliveryStatus) {
        this.daysToCompleteOutForDeliveryStatus = daysToCompleteOutForDeliveryStatus;
    }
}
