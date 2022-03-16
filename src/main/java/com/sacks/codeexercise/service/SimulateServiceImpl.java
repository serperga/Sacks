package com.sacks.codeexercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.ProductRepository;

@Service
public class SimulateServiceImpl implements SimulateService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SimulateServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository){
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void simulateSystem() {
        for (int i = 0; i < 200; i++) {
            Customer customer = new Customer();
            customer.setUsername("user"+i);
            customer.setInitialAmountInWallet(200.0);
            customer.setCurrentAmountInWallet(200.0);

            customer = customerRepository.save(customer);
        }

        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setName("Product" + i);
            product.setQuantity(15);
            product.setPrice(100.0);

            product = productRepository.save(product);
        }
    }
}
