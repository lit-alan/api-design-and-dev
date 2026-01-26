
package com.example.l3_adding_hateoas_to_an_api.controllers;

import com.example.l3_adding_hateoas_to_an_api.model.Author;
import com.example.l3_adding_hateoas_to_an_api.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class AuthorControllerV1 {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> getAll() {
        return authorService.findAll();
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getOne(@PathVariable long id) {
        Optional<Author> o = authorService.findOne(id);

        if (!o.isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(o.get());
    }

    @GetMapping("/authors/count")
    public long getCount() {
        return authorService.count();
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        authorService.deleteByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<String> add(@RequestBody Author a) {
        //check if the author ID is provided (it shouldn't be for new authors)
        if (a.getAuthorID() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("New authors should not have an ID. Please omit the authorID field.");
        }

        //Save the new author
        authorService.saveAuthor(a);

        //Return a CREATED response
        return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
    }

    @PutMapping("/authors")
    public ResponseEntity<?> updateAuthor(@RequestBody Author updatedAuthor) {
        long authorID = updatedAuthor.getAuthorID();

        //Validate the authorID
        if (authorID <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide an Author ID for an update");
        }

        try {
            //Check if the author exists
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