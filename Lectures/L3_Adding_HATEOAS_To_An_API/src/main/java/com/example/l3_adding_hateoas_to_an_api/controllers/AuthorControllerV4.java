package com.example.l3_adding_hateoas_to_an_api.controllers;

import com.example.l3_adding_hateoas_to_an_api.model.Author;
import com.example.l3_adding_hateoas_to_an_api.service.AuthorService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/")
public class AuthorControllerV4 {

    @Autowired
    private AuthorService authorService;


    @GetMapping("/authors")
    @Cacheable(value = "authors")
    public List<Author> getAll() {
        //simulate a v slow database call (only if not cached)
        simulateSlowService();
        return authorService.findAll();
    }

    //Simulate a 10-second delay
    private void simulateSlowService() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    //Evict the cache when authors are updated
    @PutMapping("/authors")
    @CacheEvict(value = "authors", allEntries = true)
    public ResponseEntity<?> updateAuthor(@RequestBody Author updatedAuthor) {
        long authorID = updatedAuthor.getAuthorID();

        //Validate the authorID
        if (authorID <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide an Author ID for an update");
        }

        try {
            //Check if author exists
            Author existingAuthor = authorService.findOne(authorID)
                    .orElseThrow(() -> new EntityNotFoundException("Author with ID " + authorID + " not found"));

            //Update the fields
            existingAuthor.setFirstName(updatedAuthor.getFirstName());
            existingAuthor.setLastName(updatedAuthor.getLastName());
            existingAuthor.setYearBorn(updatedAuthor.getYearBorn());

            //Save and return the updated author
            Author savedAuthor = authorService.saveAuthor(existingAuthor);
            return ResponseEntity.ok(savedAuthor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
