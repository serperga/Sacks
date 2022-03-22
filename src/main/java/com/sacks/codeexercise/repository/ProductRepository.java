package com.sacks.codeexercise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.ProductStockInformation;
import com.sacks.codeexercise.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findProductByProductId(long productId);
}
