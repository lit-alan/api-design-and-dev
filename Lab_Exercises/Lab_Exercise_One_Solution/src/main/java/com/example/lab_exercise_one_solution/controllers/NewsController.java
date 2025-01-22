package com.example.lab_exercise_one_solution.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    //simulated news data
    private static final Map<Integer, String> articles = Map.of(
            1, "Breaking News: Semester 1 Exam Results are due on Feb 6th....",
            2, "Tech News: Students agree not to use Gen AI for this Lab Exercise....",
            3, "Football: Arsenal clinch Premier League title after historic Liverpool collapse...."
    );

    /*
    This endpoint supports multiple response formats (JSON, XML, Plain Text).
    It automatically determines the response format based on the Accept header sent by the client.
    Spring checks the produces attribute to match the best available format.
   */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> getNewsArticle(
            @PathVariable int id,
            @RequestHeader(value = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        //if the article isn't found, return a 404 error
        if (!articles.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - News Article Not Found");
        }

        String news = articles.get(id);

        //creates a Map containing the article's ID and content. This map is used as the response body
        Map<String, Object> response = Map.of("id", id, "article", news);

        //return JSON if the Accept header is "application/json" (default)
        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().body(response);
        }

        //return XML automatically using Jackson/JAXB
        if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(response);
        }

        //return plain text if Accept header is "text/plain"
        if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(news);
        }

        //if no acceptable format is found, return 406 Not Acceptable
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("406 - Not Acceptable");
    }
}