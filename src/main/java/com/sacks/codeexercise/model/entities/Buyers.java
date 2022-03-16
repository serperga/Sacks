package com.sacks.codeexercise.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sergio Perez Gago <sergio.perez01@globant.com>
 */
@Entity
@Table(name = "buyers")
public class Buyers {

    @Id
    private String username;
    private Double currentAmountInWallet;
    private Double initialAmountInWallet;

    public Buyers(){}

    public Buyers(String username, Double currentAmountInWallet, Double initialAmountInWallet) {
        this.username = username;
        this.currentAmountInWallet = currentAmountInWallet;
        this.initialAmountInWallet = initialAmountInWallet;
    }

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
}
