package com.sacks.codeexercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.model.ProductStockInformation;
import com.sacks.codeexercise.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="ProductInformationController", description="Gives information about products in store (quantity, price, etc ...).")
@RestController
public class ProductInformationController {

    private final ProductService productService;

    @Autowired
    public ProductInformationController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @ApiOperation("Returns list of all Products in the system.")
    public ResponseEntity<List<ProductStockInformation>> getAllProduct(){
        List<ProductStockInformation> productInformationList = productService.getAllProducts();
        return ResponseEntity.ok(productInformationList);
    }

    @GetMapping("/products/{productId}")
    @ApiOperation("Returns the product in the system using its id.")
    public ResponseEntity<ProductStockInformation> getProductInformation(@PathVariable("productId") long id){
        ProductStockInformation productStockInformation = productService.getProduct(id);
        return ResponseEntity.ok(productStockInformation);
    }
}
