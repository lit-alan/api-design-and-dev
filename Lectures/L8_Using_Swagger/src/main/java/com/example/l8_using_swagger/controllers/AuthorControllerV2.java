
package com.example.l8_using_swagger.controllers;

import com.example.l8_using_swagger.model.Author;
import com.example.l8_using_swagger.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing authors in the system.
 */
@Tag(name = "Author Management", description = "API for managing authors")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping(value = "/api/v2/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AuthorControllerV2 {

    @Autowired
    private AuthorService authorService;

    /**
     * Retrieves a list of all authors.
     *
     * @return A list of authors.
     */
    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of authors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/authors", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Author> getAll() {
        return authorService.findAll();
    }

    /**
     * Retrieves a single author by their ID.
     *
     * @param id The ID of the author to retrieve.
     * @return The author object if found, or 404 Not Found if the author doesn't exist.
     */
    @Operation(summary = "Get author by ID", description = "Retrieve a single author based on their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })

    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<Author> getOne(@Parameter(description = "ID of the author to retrieve") @PathVariable long id) {
        Optional<Author> o = authorService.findOne(id);

        if (!o.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(o.get());
    }

    /**
     * Retrieves the count of all authors in the system.
     *
     * @return The count of authors.
     */
    @Operation(summary = "Get author count", description = "Retrieve the total count of authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the count of authors")
    })
    @GetMapping(value = "/authors/count", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public long getCount() {
        return authorService.count();
    }

    /**
     * Deletes an author by their ID.
     *
     * @param id The ID of the author to delete.
     * @return A response indicating success or failure.
     */
    @Operation(summary = "Delete author by ID", description = "Delete an author based on their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping(value = "/authors/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the author to delete") @PathVariable long id) {
        authorService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds a new author.
     *
     * @param a The author object to add.
     * @return A response indicating success or failure.
     */
    @Operation(
            summary = "Add a new author",
            description = "Creates a new author in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Author created",
                            content = {
                                    @Content(mediaType = "application/json"),
                                    @Content(mediaType = "application/xml")
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping(value = "/authors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> add(@RequestBody @Parameter(description = "Author object to be added") Author a) {
        if (a.getAuthorID() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("New authors should not have an ID. Please omit the authorID field.");
        }

        authorService.saveAuthor(a);
        return ResponseEntity.status(HttpStatus.CREATED).body("Author created successfully");
    }

    /**
     * Updates an existing author.
     *
     * @param updatedAuthor The updated author object.
     * @return The updated author object if successful, or an error message.
     */
    @Operation(summary = "Update an existing author", description = "Update the details of an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the author"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid Author ID"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping(value = "/authors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> updateAuthor(@RequestBody @Parameter(description = "Updated author object") Author updatedAuthor) {
        long authorID = updatedAuthor.getAuthorID();

        if (authorID <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide an Author ID for an update");
        }

        try {
            Author existingAuthor = authorService.findOne(authorID)
                    .orElseThrow(() -> new EntityNotFoundException("Author with ID " + authorID + " not found"));

            existingAuthor.setFirstName(updatedAuthor.getFirstName());
            existingAuthor.setLastName(updatedAuthor.getLastName());
            existingAuthor.setYearBorn(updatedAuthor.getYearBorn());

            Author savedAuthor = authorService.saveAuthor(existingAuthor);
            return ResponseEntity.ok(savedAuthor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}