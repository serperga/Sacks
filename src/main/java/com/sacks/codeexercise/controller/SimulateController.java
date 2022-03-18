package com.sacks.codeexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.service.SimulateService;

@RestController
public class SimulateController {

    private final SimulateService simulateService;

    @Autowired
    public SimulateController(SimulateService simulateService){
        this.simulateService = simulateService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> simulateSystemCreation() {
        simulateService.simulateSystem();
        return ResponseEntity.ok("Hello from Docker!");
    }

}
