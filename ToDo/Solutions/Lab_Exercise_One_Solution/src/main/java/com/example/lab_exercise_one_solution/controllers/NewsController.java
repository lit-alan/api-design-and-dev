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


    //the above endpoint can be rewitten as follows (although i am only suporting JSON and XML):

    //Note: We use ResponseEntity<Map<String, Object>> instead of ResponseEntity<?>
    //because this endpoint always returns a structured response with a fixed shape.
    //Restricting the response type improves type safety, documents the API contract,
    //and avoids returning different body types (e.g. String vs Map) from the same endpoint.



    //As above it uses a GET request for a news article identified by its ID.
    //As above the `produces` attribute declares which response formats this endpoint supports and Spring uses this
    //for content negotiation based on the client's Accept header.
    @GetMapping(value = "/v2/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})

    public ResponseEntity<Map<String, Object>> getNewsArticle(@PathVariable int id) {

        //the guard clause is the same as above - if the article isn't found, return a 404 error
        if (!articles.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        //Build a simple, structured response body using a Map.
        //Spring will serialize this Map automatically:
        //to JSON via Jackson when Accept: application/json
        //to XML via Jackson XML when Accept: application/xml
        //
        // No manual serialization or header inspection is required.
        return ResponseEntity.ok(
                Map.of(
                        "id", id,
                        "article", articles.get(id)
                )
        );
    }

    /*
        This endpoint does not support text/plain because it returns a structured object (Map).
        Spring can automatically serialize structured data to JSON or XML, but has no standard way to convert a Map into plain text.
        Supporting text/plain would require explicitly returning a String or a separate handler.
     */


}