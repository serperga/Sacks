package com.sacks.codeexercise.model.entities;

import java.io.Serializable;
import java.util.Optional;

public class OrderUpdateInformation implements Serializable {
    private Optional<Integer> status;

    public OrderUpdateInformation() {
    }

    public OrderUpdateInformation(Optional<Integer> status) {
        this.status = status;
    }

    public Optional<Integer> getStatus() {
        return status;
    }

    public void setStatus(Optional<Integer> status) {
        this.status = status;
    }
}
