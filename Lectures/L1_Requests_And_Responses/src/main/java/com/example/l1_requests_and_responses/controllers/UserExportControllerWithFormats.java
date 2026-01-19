package com.example.l1_requests_and_responses.controllers;

import com.example.l1_requests_and_responses.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller that generates and returns a user table in TSV (Excel-compatible), JSON, or XML format.
 *
 * <p><b>Endpoint:</b></p>
 * <ul>
 *     <li><b>GET /export-users/v2</b> - Returns a user list in TSV, JSON, or XML format.</li>
 * </ul>
 *
 * <p><b>Supported Formats:</b></p>
 * <ul>
 *     <li><b>TSV</b> - Tab-separated values (Excel-compatible, default)</li>
 *     <li><b>JSON</b> - JavaScript Object Notation</li>
 *     <li><b>XML</b> - Extensible Markup Language</li>
 * </ul>
 *
 * <p><b>Specify the format using the standard HTTP request header:</b></p>
 * <ul>
 *     <li><code>Accept: application/json</code></li>
 *     <li><code>Accept: application/xml</code></li>
 *     <li><code>Accept: text/tab-separated-values</code></li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.3
 */
@RestController
@RequestMapping("/export-users/v2")
public class UserExportControllerWithFormats {

    private static final List<User> USERS = List.of(
            new User("Alan", "Ryan", "alan.ryan@tus.ie"),
            new User("Brendan", "Watson", "brendan.watson@tus.ie"),
            new User("Gerry", "Guinane", "gerry.guinane@tus.ie"),
            new User("Mike", "Winterburn", "mike.winterburn@tus.ie"),
            new User("Sharon", "Byrne", "sharon.byrne@tus.ie")
    );

    /**
     * Exports users in the format specified by the standard HTTP Accept header.
     *
     * @param response     The HTTP response where headers are set for file export.
     * @param acceptHeader The requested response media type (e.g. application/json, application/xml).
     * @throws IOException If an error occurs while writing to the response.
     */

    @GetMapping
    public void exportUsers(HttpServletResponse response,
                            @RequestHeader(value = "Accept", defaultValue = "text/tab-separated-values")
                            String acceptHeader)
            throws IOException {

        if (acceptHeader.contains("application/json")) {
            exportAsJson(response);
        } else if (acceptHeader.contains("application/xml")) {
            exportAsXml(response);
        } else {
            exportAsTsv(response);
        }
    }

    ////////////////////////////////////////////////////////////
    //TSV Support
    ////////////////////////////////////////////////////////////

    private void exportAsTsv(HttpServletResponse response) throws IOException {
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

    ////////////////////////////////////////////////////////////
    //JSON Support
    ////////////////////////////////////////////////////////////

    private void exportAsJson(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=users.json");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(USERS));
    }

//    //JSON: Get Spring + Jackson to serialize List<User> automatically
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<User>> exportUsersAsJson() {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.json")
//                .body(USERS);
//    }



    ////////////////////////////////////////////////////////////
    //XML Support
    ////////////////////////////////////////////////////////////


    private void exportAsXml(HttpServletResponse response) throws IOException {
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "inline");
        
        XmlMapper xmlMapper = new XmlMapper();
        response.getWriter().write(xmlMapper.writeValueAsString(USERS));
    }

//    //XML: Get Spring to serialize automatically (requires jackson-dataformat-xml on the classpath)
//    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<List<User>> exportUsersAsXml() {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xml")
//                .body(USERS);
//    }

}
