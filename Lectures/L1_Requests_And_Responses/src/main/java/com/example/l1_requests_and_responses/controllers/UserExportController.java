package com.example.l1_requests_and_responses.controllers;

import com.example.l1_requests_and_responses.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Controller that generates and returns a user table in TSV (Tab-Separated Values) format,
 * designed for Excel file compatibility.
 *
 * <p><b>Endpoint:</b></p>
 * <ul>
 *     <li><b>GET /export-users</b> - Returns a TSV report of users.</li>
 * </ul>
 *
 * <p><b>Response Content-Type:</b> application/vnd.ms-excel</p>
 *
 * @author Alan Ryan
 * @version 1.0
 */
@RestController
@RequestMapping("/export-users/v1")
public class UserExportController {

    //List of users I am going to return in spreadsheet
    private static final List<User> USERS = List.of(
            new User("Alan", "Ryan", "alan.ryan@tus.ie"),
            new User("Brendan", "Watson", "brendan.watson@tus.ie"),
            new User("Gerry", "Guinane", "gerry.guinane@tus.ie"),
            new User("Mike", "Winterburn", "mike.winterburn@tus.ie"),
            new User("Sharon", "Byrne", "sharon.byrne@tus.ie")
    );

    @GetMapping
    public void exportUsers(HttpServletResponse response) throws IOException {
        //Set response headers to indicate an Excel-compatible file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=users.xls");
        response.getWriter().write(generateTsv());
    }

    private String generateTsv() {
        StringBuilder tsv = new StringBuilder("First Name\tLast Name\tEmail Address\n");
        for (User user : USERS) {
            tsv.append(user.firstName()).append("\t")
                    .append(user.lastName()).append("\t")
                    .append(user.emailAddress()).append("\n");
        }
        return tsv.toString();
    }
}


