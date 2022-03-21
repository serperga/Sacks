package com.sacks.codeexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.error.WrongParameterException;
import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.OrderUpdateResponse;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.service.UpdateOrderService;
import io.swagger.annotations.Api;

@Api(value="UpdateOrderController", description="Update status and information of an existing order.The only value that can be changed in the order is the status.To be a "
    + "valid status has to be the next status on the order process sequence. This sequence is Ordered -> Sent to warehouse -> Packaged -> Carrier picked up "
    + "-> Out for delivery -> Delivered. The order can't be cancelled. In case the status is not the next in the sequence an error should be thrown-")
@RestController
public class UpdateOrderController {

    private final UpdateOrderService updateOrderService;

    private String errorMessage = "Order Status information not found";

    @Autowired
    public UpdateOrderController(UpdateOrderService updateOrderService) {
        this.updateOrderService = updateOrderService;
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderUpdateResponse> updateOrder(@PathVariable("orderId") long id,@RequestBody OrderUpdateInformation orderUpdateInformation){
        if (orderUpdateInformation.getStatus() == null){
            throw new WrongParameterException(errorMessage);
        }
        Order orderUpdated = updateOrderService.updateOrder(orderUpdateInformation, id);
        OrderUpdateResponse orderUpdateResponseResponse = new OrderUpdateResponse();

        orderUpdateResponseResponse.setOrderId(orderUpdated.getOrderId());
        orderUpdateResponseResponse.setOrderStatus(orderUpdated.getOrderStatus().getStatus());
        orderUpdateResponseResponse.setAmount(orderUpdated.getAmount());
        orderUpdateResponseResponse.setBuyer(orderUpdated.getBuyer().getUsername());
        orderUpdateResponseResponse.setProducts(orderUpdated.getProducts());
        orderUpdateResponseResponse.setEstimatedDays(orderUpdated.getEstimatedDays());

        return ResponseEntity.ok(orderUpdateResponseResponse);
    }
}
