package com.sacks.codeexercise.service;

import java.util.List;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.repository.CustomerRepository;

public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;

    public DashboardServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void createDashboardForUser(String username) {
        List<Customer> customers = getAllCustomers();

        final Customer customer = customers.get(0);


    }

    private List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }
}
