package com.example.l1_requests_and_responses.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that detects the user's browser based on the "User-Agent" header.
 *
 * <p><b>Endpoint:</b></p>
 * <ul>
 *     <li><b>GET /detect-browser</b> - Returns the detected browser name.</li>
 * </ul>
 *
 * <p><b>Example Response:</b></p>
 * <pre>
 * You are using: Chrome
 * </pre>
 *
 * @author Alan Ryan
 * @version 1.0
 */
@RestController
@RequestMapping("/detect-browser")
public class BrowserDetectionController {

    /**
     * Detects the browser type based on the "User-Agent" header.
     *
     * @param userAgent The "User-Agent" HTTP header sent by the client.
     * @return A response string indicating the detected browser.
     */
    @GetMapping
    public String detectBrowser(@RequestHeader("User-Agent") String userAgent) {
        String browser = getBrowserFromUserAgent(userAgent);
        return "You are using: " + browser;
    }

    /**
     * Determines the browser type from the provided User-Agent string.
     *
     * @param userAgent The "User-Agent" string extracted from the request header.
     * @return The detected browser name.
     */
    private String getBrowserFromUserAgent(String userAgent) {
        if (userAgent.contains("Chrome") && userAgent.contains("Safari") && !userAgent.contains("Edg")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Edg")) {
            return "Edge";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";
        } else if (userAgent.contains("OPR") || userAgent.contains("Opera")) {
            return "Opera";
        } else {
            return "Unknown";
        }
    }
}
