package com.sacks.codeexercise.repository;

import org.springframework.data.repository.CrudRepository;

import com.sacks.codeexercise.model.entities.Buyers;

public interface BuyerRepository extends CrudRepository<Buyers, String> {
    Buyers findByUsername(String username);
}
