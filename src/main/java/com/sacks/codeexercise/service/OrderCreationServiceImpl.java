package com.sacks.codeexercise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Product;

@Service
public class OrderCreationServiceImpl implements OrderCreationService {

    @Override
    public void createOrder(Customer customer, List<Product> products) {

        //First Generate
    }
}
