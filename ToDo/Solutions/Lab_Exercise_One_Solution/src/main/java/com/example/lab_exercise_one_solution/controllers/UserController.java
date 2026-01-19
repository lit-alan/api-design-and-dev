package com.example.lab_exercise_one_solution.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    //Simulated users being stored in a database
    private static final Map<String, Map<String, String>> users = Map.of(
            "john_doe", Map.of("fullName", "John Doe", "email", "johndoe@example.com", "createdAt", "2022-05-01"),
            "jane_smith", Map.of("fullName", "Jane Smith", "email", "janesmith@example.com", "createdAt", "2023-02-10")
    );

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, String>> getUserProfile(@PathVariable String username) {

        //Check if the user exists, return 404 if not found
        if (!users.containsKey(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "404 - User Not Found: " + username));
        }

        Map<String, String> userData = users.get(username);
        String maskedEmail = maskEmail(userData.get("email")); //Mask the email address

        //Generate a unique request ID for tracking
        String requestId = UUID.randomUUID().toString();

        //Add custom headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Security-Policy", "No-Cache, Secure");
        headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        headers.add("X-Request-ID", requestId);

        //Return 200 OK with modified headers and user details
        return ResponseEntity.ok()
                .headers(headers)
                .body(Map.of(
                        "username", username,
                        "fullName", userData.get("fullName"),
                        "email", maskedEmail,
                        "createdAt", userData.get("createdAt")
                ));
    }

    //Helper method to mask email addresses
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email; //Return as is for invalid email addresses
        }

        String[] parts = email.split("@");
        String localPart = parts[0]; //Username before '@'
        String domain = parts[1]; //Domain after '@'

        //Ensure at least 1 visible character, mask the rest
        String maskedLocal = localPart.charAt(0) + "****";

        return maskedLocal + "@" + domain;
    }
}
