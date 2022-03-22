package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.ProductStockInformation;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductStockInformation> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductStockInformation> productStockInformationList = new ArrayList<>();
        productList.forEach(product -> {
            ProductStockInformation productInformation = new ProductStockInformation(product.getProductId(),product.getName(),product.getQuantity(),product.getPrice());
            productStockInformationList.add(productInformation);
        });

        return productStockInformationList;
    }

    @Override
    public ProductStockInformation getProduct(Long productId) {

        Optional<Product> productInStockInformation = productRepository.findProductByProductId(productId);
        ProductStockInformation productInformation = new ProductStockInformation();

        if(productInStockInformation.isPresent()){
            productInformation.setProductId(productInStockInformation.get().getProductId());
            productInformation.setPrice(productInStockInformation.get().getPrice());
            productInformation.setName(productInStockInformation.get().getName());
            productInformation.setQuantity(productInStockInformation.get().getQuantity());
        }else{
            throw new ProductNotFoundException("Product is not found");
        }
        return productInformation;
    }
}
