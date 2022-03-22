package com.sacks.codeexercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.model.OrderRefundInformation;
import com.sacks.codeexercise.service.ProductRefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="ProductRefundController", description="Returns a product a customer bought and refund the price of this product to the customer wallet")
@RestController
public class ProductRefundController {

    private ProductRefundService productRefundService;

    public ProductRefundController(ProductRefundService productRefundService) {
        this.productRefundService = productRefundService;
    }

    @PutMapping("/orders/{orderId}/products/{productId}/refund")
    @ApiOperation("Refund a customer a product returned from the order.")
    public ResponseEntity<OrderRefundInformation> refundProductToCustomer(@PathVariable("orderId") long orderId,@PathVariable("productId") long productId){
        OrderRefundInformation orderRefundInformation = productRefundService.returnProductAndGetRefund(orderId,productId);
        return ResponseEntity.ok(orderRefundInformation);
    }

}
