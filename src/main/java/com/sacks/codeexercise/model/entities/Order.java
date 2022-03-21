package com.sacks.codeexercise.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "seq_customer")
    @SequenceGenerator(
        name = "seq_customer",
        allocationSize = 1
    )
    @ApiModelProperty(notes = "order identifier",name="orderId",required=true,value="10000")
    private long orderId;
    @ApiModelProperty(notes = "Estimated days to move the order to the next status. There is no value if the order is cancelled",name="estimatedDays",required=false,value="2")
    private int estimatedDays;
    @ApiModelProperty(notes = "Total amount of the order. . There is no value if the order is cancelled",name="amount",required=true,value="250.35")
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ApiModelProperty(notes = "Customer who placed the order",name="buyer",required=true,value="")
    private Customer buyer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ApiModelProperty(notes = "Status of the order (Delivered,Ordered,Cancelled etc ...)",name="orderStatus",required=true,value="")
    private OrderStatus orderStatus;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_products",
        joinColumns = {
            @JoinColumn(name = "order_id", nullable = false, updatable = false)},
        inverseJoinColumns = {
            @JoinColumn(name = "product_id", nullable = false, updatable = false)})
    @ApiModelProperty(notes = "Products ordered",name="products",required=true,value="")
    private List<Product> products = new ArrayList<>();

    public Order(){}

    public Order(long orderId, int estimatedDays, Double amount, Customer buyer) {
        this.orderId = orderId;
        this.estimatedDays = estimatedDays;
        this.amount = amount;
        this.buyer = buyer;
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

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
