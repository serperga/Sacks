package com.sacks.codeexercise.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sacks.codeexercise.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@Api(value="DashboardController", description="Gives information about order amount time in days of each stage of the process and average time for each stage of the process")
@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping(value = "/customers/{username}/dashboard", produces = "text/csv")
    @ApiOperation("Create a CSV File with the dashboard information for one user.")
    public ResponseEntity<Resource> getDashboardForOneCustomer(@PathVariable ("username") String username){
        List<List<String>> dashboardInformation = dashboardService.createDashboardForUser(username);

        ByteArrayInputStream byteArrayOutputStream = createCSVDashboardFile(dashboardInformation);

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

        String csvFileName = username + "Dashboard.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
            fileInputStream,
            headers,
            HttpStatus.OK
        );
    }

    @GetMapping(value = "/customers/dashboard", produces = "text/csv")
    @ApiOperation("Create a CSV File with the dashboard information for all users in store.")
    public ResponseEntity<Resource> getDashboardForAllCustomers(){

        List<List<String>> dashboardInformation = dashboardService.createDashboardForUsers();
        ByteArrayInputStream byteArrayOutputStream = createCSVDashboardFile(dashboardInformation);

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

        String csvFileName = "customersDashboard.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
            fileInputStream,
            headers,
            HttpStatus.OK
        );
    }

    private ByteArrayInputStream createCSVDashboardFile(List<List<String>> dashboardInformation){
        String[] csvHeader = {
            "Customer", "Initial Amount in wallet", "Current Amount in Wallet","Order Identifier", "Order Status", "Products Ordered","Ordered","Sent to Warehouse", "Packaged", "Carrier picked up","Out for delivery"
        };

        ByteArrayInputStream byteArrayOutputStream;

        try (
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // defining the CSV printer
            CSVPrinter csvPrinter = new CSVPrinter(
                new PrintWriter(out),
                // withHeader is optional
                CSVFormat.DEFAULT.withHeader(csvHeader)
            );
        ) {
            // populating the CSV content
            for (List<String> record : dashboardInformation)
                csvPrinter.printRecord(record);

            // writing the underlying stream
            csvPrinter.flush();

            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return byteArrayOutputStream;
    }

}
