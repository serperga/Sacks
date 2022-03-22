package com.sacks.codeexercise.model;

import io.swagger.annotations.ApiModelProperty;

public class ProductInformation {

    @ApiModelProperty(notes = "product name",name="productName",required=true,value="Product 1")
    String productName;
    @ApiModelProperty(notes = "product price",name="price",required=true,value="15.0")
    Double price;

    public ProductInformation() {
    }

    public ProductInformation(String productName, Double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
