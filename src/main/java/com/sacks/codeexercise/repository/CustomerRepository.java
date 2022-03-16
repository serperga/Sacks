package com.sacks.codeexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sacks.codeexercise.model.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByUsername(String username);
}
