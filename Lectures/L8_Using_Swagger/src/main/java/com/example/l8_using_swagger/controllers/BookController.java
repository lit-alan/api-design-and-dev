package com.example.l8_using_swagger.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books", produces = {"application/json", "application/xml"})
public class BookController {

    @GetMapping
    public String getBooks(
            @RequestHeader(value = "Accept", required = false) String acceptHeader,
            @RequestHeader(value = "X-API-Version", required = false) String apiVersion
    ) {
        return "Books API requested format: " + acceptHeader + ", Version: " + apiVersion;
    }
}

