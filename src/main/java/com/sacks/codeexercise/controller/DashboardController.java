package com.sacks.codeexercise.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@Api(value="DashboardController", description="Gives information about order amount time in days of each stage of the process and average time for each stage of the process")
@RestController
public class DashboardController {

    @GetMapping(value = "/customers/dashboard", produces = "text/csv")
    public ResponseEntity<Resource> getDashboard(){
        String[] csvHeader = {
            "Customer", "Initial Amount in wallet", "Current Amount in Wallet","Order Identifier", "Order Status", "Products Ordered","Ordered","Sent to Warehouse", "Packaged", "Carrier picked up","Out for delivery"
        };

        List<List<String>> csvBody = new ArrayList<>();

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
            for (List<String> record : csvBody)
                csvPrinter.printRecord(record);

            // writing the underlying stream
            csvPrinter.flush();

            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

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
}
