package com.sacks.codeexercise.service;

import java.util.List;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Product;

public interface OrderCreationService {

    void createOrder(Customer customer, List<Product> products);
}
