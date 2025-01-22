package com.example.lab_exercise_one_solution.controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RedirectController {

    //Implement Permanent Redirect (301) from "/api/old-users/{id}" to "/api/users/{id}"
    @GetMapping("/old-users/{id}")
    public ResponseEntity<Void> redirectToNewUsers(@PathVariable int id) {
        //redirect to the new user profile URL
        String newUrl = "/api/users/" + id;

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, newUrl)
                .build();
    }

    //Implement Temporary Redirect (302) from "/api/maintenance" to "/api/maintenance-info"
    @GetMapping("/maintenance")
    public ResponseEntity<Void> temporaryRedirect() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/api/maintenance-info")
                .build();
    }

    //The new user endpoint
    @GetMapping("/users/{id}")
    public ResponseEntity<String> newUserEndpoint(@PathVariable int id) {
        return ResponseEntity.ok("Welcome to the new user profile for user ID: " + id);
    }

    //The maintenance information endpoint
    @GetMapping("/maintenance-info")
    public ResponseEntity<String> maintenanceInfo() {
        return ResponseEntity.ok("The system is under maintenance. Please try again later.");
    }
}