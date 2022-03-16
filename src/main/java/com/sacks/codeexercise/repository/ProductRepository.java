package com.sacks.codeexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
