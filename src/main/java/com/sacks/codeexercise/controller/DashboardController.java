package com.sacks.codeexercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value="DashboardController", description="Gives information about order amount time in days of each stage of the proccess and average time for each stage of the process")
@RestController
public class DashboardController {

    @GetMapping("/customers/{username}/dashboard")
    public ResponseEntity<String> getDashboard(@PathVariable("username") String username){
        return ResponseEntity.ok("");
    }
}
