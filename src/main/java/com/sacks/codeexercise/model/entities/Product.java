package com.sacks.codeexercise.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "seq_products")
    @SequenceGenerator(
        name = "seq_products",
        allocationSize = 1
    )
    @ApiModelProperty(notes = "product identifier",name="productId",required=true,value="15")
    private long productId;
    @ApiModelProperty(notes = "name of the product",name="name",required=true,value="example")
    private String name;
    @ApiModelProperty(notes = "quantity of the product",name="quantity",required=true,value="1")
    private int quantity;
    @ApiModelProperty(notes = "price of the product",name="orderAmount",required=true,value="150.35")
    private Double price;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public Product() {
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
