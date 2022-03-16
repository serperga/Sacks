package com.sacks.codeexercise.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "buyers")
public class Customer {

    @Id
    private String username;
    private Double currentAmountInWallet;
    private Double initialAmountInWallet;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Customer(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getCurrentAmountInWallet() {
        return currentAmountInWallet;
    }

    public void setCurrentAmountInWallet(Double currentAmountInWallet) {
        this.currentAmountInWallet = currentAmountInWallet;
    }

    public Double getInitialAmountInWallet() {
        return initialAmountInWallet;
    }

    public void setInitialAmountInWallet(Double initialAmountInWallet) {
        this.initialAmountInWallet = initialAmountInWallet;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
