package com.example.l1_requests_and_responses.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Controller for handling an auto-refreshing page.
 *
 * <p><b>Endpoint:</b></p>
 * <ul>
 *     <li><b>GET /auto-refresh</b> - Displays the current date/time and refreshes every 5 seconds.</li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.1
 */
@RestController
@RequestMapping("/auto-refresh")
public class AutoRefreshController {

    /**
     * Returns an auto-refreshing page that updates every 5 seconds.
     *
     * @param response The HTTP response, where the "Refresh" header is set.
     * @return A simple HTML response with the current date/time.
     */
    @GetMapping
    public String processRequest(HttpServletResponse response) {
        //Set refresh time to 5 seconds
        response.setHeader("Refresh", "5");

        //Return HTML content directly as a String
        return "<h3>Auto Refresh Page Using Header</h3><br>" + new Date();
    }
}

