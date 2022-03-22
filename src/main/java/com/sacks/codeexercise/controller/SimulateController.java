package com.sacks.codeexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.service.SimulateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="SimulateteSystem", description="This endpoint create the Database schema and data for the exercise")
@RestController
public class SimulateController {

    private final SimulateService simulateService;

    @Autowired
    public SimulateController(SimulateService simulateService){
        this.simulateService = simulateService;
    }

    @ApiOperation(value = "Create the database schema and all the information needed for the initial store.")
    @PostMapping("/simulate")
    public ResponseEntity<String> simulateSystemCreation() {
        simulateService.simulateSystem();
        return ResponseEntity.ok("Initial system created");
    }

}
