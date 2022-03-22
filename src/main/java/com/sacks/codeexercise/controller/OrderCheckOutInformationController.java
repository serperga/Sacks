package com.sacks.codeexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.model.OrderInformation;
import com.sacks.codeexercise.service.OrderCheckoutInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="OrderCheckOutInformationController", description="Gives information about order checkout information. The order should be in process and not delivered")
@RestController
public class OrderCheckOutInformationController {

    private final OrderCheckoutInformationService orderCheckoutInformationService;

    @Autowired
    public OrderCheckOutInformationController(
        OrderCheckoutInformationService orderCheckoutInformationService) {
        this.orderCheckoutInformationService = orderCheckoutInformationService;
    }

    @GetMapping("/orders/{orderId}")
    @ApiOperation("Returns all the checkout information about an order.")
    public ResponseEntity<OrderInformation> retrieveAllCheckoutInformationAboutAnOrder(@PathVariable("productId") long id){
        OrderInformation orderUpdateInformation = orderCheckoutInformationService.retrieveOrderCheckoutInformation(id);
        return ResponseEntity.ok(orderUpdateInformation);
    }
}
