package com.example.l1_requests_and_responses.controllers;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller that handles HTTP requests by extracting request metadata, headers,
 * and setting a response cookie. Returns the request details in JSON format.
 *
 * <p>This controller provides an endpoint to inspect incoming HTTP requests,
 * including the request method, URI, URL, protocol, and headers. Additionally,
 * it sets a sample cookie in the response.</p>
 *
 * <p><b>Endpoint:</b></p>
 * <ul>
 *     <li><b>GET /request-info</b> - Returns request details in JSON format.</li>
 * </ul>
 *
 * <p><b>Response Structure:</b></p>
 * <pre>
 * {
 *     "Request Method": "GET",
 *     "Requested Resource": "/request-info",
 *     "Requested URL": "http://localhost:8080/request-info",
 *     "Protocol": "HTTP/1.1",
 *     "Headers": {
 *         "User-Agent": "Mozilla/5.0",
 *         "Accept": "text/html"
 *     }
 * }
 * </pre>
 *
 * @author Alan Ryan
 * @version 1.0
 */

@RestController
@RequestMapping("/request-info")
public class RequestInfoController {

    /*
    The method returns a JSON object that contains key-value pairs representing the request details.
    Using a Map automatically serializes into JSON when returned from a @RestController.
    Spring Boot's built-in Jackson library converts it into the structured JSON format you see in the browser
    */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> displayRequestInfo(HttpServletRequest request, HttpServletResponse response) {
        //Set a cookie to include in the response
        Cookie cookie = new Cookie("EmailCookie", "alan.ryan@tus.ie");
        response.addCookie(cookie);

        //Store general request metadata (HTTP method, URI, URL, protocol)
        Map<String, Object> responseData = new HashMap();
        responseData.put("Request Method", request.getMethod());
        responseData.put("Requested Resource", request.getRequestURI());
        responseData.put("Requested URL", request.getRequestURL().toString());
        responseData.put("Protocol", request.getProtocol());

        /*
         * Collect HTTP headers from the request and store them in a map.
         * - The key (String) represents the header name.
         * - The value (String) contains the corresponding header value.
         */
        Map<String, String> headers = new HashMap();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        responseData.put("Headers", headers);

        return responseData;
    }
}


