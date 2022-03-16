package com.sacks.codeexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.repository.BuyerRepository;

@RestController
public class MessageController {

    @Autowired
    BuyerRepository buyerRepository;

    @GetMapping("/messages")
    public String getMessage() {
        return "Hello from Docker!";
    }

}
