package com.example.l7_ssl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    /**
     * Displays the login form.
     * @return the login page view
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login_form";
    }

    /**
     * Handles form-based login submission.
     * @param formData a map containing the submitted username and password
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping("/login")
    @ResponseBody
    public String processLogin(@RequestParam Map<String, String> formData) {
        String username = formData.get("username");
        String password = formData.get("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return "Login successful! Welcome, " + username;
        } else {
            return "Invalid username or password";
        }
    }
}