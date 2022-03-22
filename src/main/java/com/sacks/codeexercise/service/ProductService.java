package com.sacks.codeexercise.service;

import java.util.List;

import com.sacks.codeexercise.model.ProductStockInformation;

public interface ProductService {
    List<ProductStockInformation> getAllProducts();
    ProductStockInformation getProduct(Long productId);
}
