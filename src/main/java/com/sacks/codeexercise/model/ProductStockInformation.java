package com.sacks.codeexercise.model;

import io.swagger.annotations.ApiModelProperty;

public class ProductStockInformation {

    @ApiModelProperty(notes = "product identifier",name="productId",required=true,value="15")
    private long productId;
    @ApiModelProperty(notes = "name of the product",name="name",required=true,value="example")
    private String name;
    @ApiModelProperty(notes = "quantity of the product",name="quantity",required=true,value="1")
    private int quantity;
    @ApiModelProperty(notes = "price of the product",name="orderAmount",required=true,value="150.35")
    private Double price;

    public ProductStockInformation() {
    }

    public ProductStockInformation(long productId, String name, int quantity, Double price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
