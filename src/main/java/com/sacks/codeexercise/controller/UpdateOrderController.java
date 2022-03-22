package com.sacks.codeexercise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.error.WrongParameterException;
import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.OrderInformation;
import com.sacks.codeexercise.model.ProductInformation;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.service.UpdateOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
    @ApiOperation("Update an order in the store based in its identifier.")
    public ResponseEntity<OrderInformation> updateOrder(@PathVariable("orderId") long id,@RequestBody OrderUpdateInformation orderUpdateInformation){
        if (orderUpdateInformation.getStatus() == null){
            throw new WrongParameterException(errorMessage);
        }
        Order orderUpdated = updateOrderService.updateOrder(orderUpdateInformation, id);
        OrderInformation orderInformation = createResponse(orderUpdated);

        return ResponseEntity.ok(orderInformation);
    }

    private OrderInformation createResponse(Order orderUpdated){
        OrderInformation orderUpdateInformationResponse = new OrderInformation();

        orderUpdateInformationResponse.setOrderId(orderUpdated.getOrderId());
        orderUpdateInformationResponse.setOrderStatus(orderUpdated.getOrderStatus().getStatus());
        orderUpdateInformationResponse.setAmount(orderUpdated.getAmount());
        orderUpdateInformationResponse.setBuyer(orderUpdated.getBuyer().getUsername());
        orderUpdateInformationResponse.setEstimatedDays(orderUpdated.getEstimatedDays());

        List<Product> productsInOrder = orderUpdated.getProducts();
        List<ProductInformation> productInformationList = new ArrayList<>();

        productsInOrder.forEach(product -> {
            ProductInformation productInformation = new ProductInformation(product.getName(),product.getPrice());
            productInformationList.add(productInformation);
        });

        orderUpdateInformationResponse.setProducts(productInformationList);

        return orderUpdateInformationResponse;
    }
}
