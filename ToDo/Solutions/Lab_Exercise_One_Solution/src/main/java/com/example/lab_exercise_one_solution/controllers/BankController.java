package com.example.lab_exercise_one_solution.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    //Simulated account balances
    private static final Map<String, Double> accountBalances = Map.of(
            "12345", 1000.50,
            "67890", 250.75
    );

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Object> getBalance(@PathVariable String accountNumber) {

        //Validate if the account number contains only digits and is exactly 5 digits long
        if (!accountNumber.matches("\\d{5}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "400 - Invalid Account Number Format: " + accountNumber
            ));
        }

        //Check if the account exists in accountBalances map
        if (!accountBalances.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "404 - Account Not Found");
        }

        //Return 200 OK with balance details
        return ResponseEntity.ok(
                "accountNumber: " + accountNumber +
                        " balance :" + accountBalances.get(accountNumber));
        //alternatively
        //Return 200 OK with balance details
//        return ResponseEntity.ok(Map.of(
//                "accountNumber", accountNumber,
//                "balance", accountBalances.get(accountNumber)
//        ));



    }
}
