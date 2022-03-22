package com.sacks.codeexercise.model;

import java.io.Serializable;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;

public class OrderUpdateInformation implements Serializable {
    @ApiModelProperty(notes = "order status. Value are from 1 to 8. The values are: 0 -> Ordered, 1 -> Sent to Warehouse, 2 -> Packaged, 3 -> Carrier picked up,"
        + "4 -> Out for delivery, 5 -> Delivered, 6 -> Cancelled. Not Enough stock, 7 -> Cancelled. Not Enough money in customer wallet, "
        + "8 -> Cancelled. Not products in order after refund",name="status",required=true,value="1")
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
