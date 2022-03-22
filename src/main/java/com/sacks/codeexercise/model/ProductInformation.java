package com.sacks.codeexercise.model;

public class ProductInformation {

    String ProductName;
    Double price;

    public ProductInformation() {
    }

    public ProductInformation(String productName, Double price) {
        ProductName = productName;
        this.price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
