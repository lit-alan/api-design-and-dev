package com.example.graphql.controllers;

import com.example.graphql.model.Author;
import com.example.graphql.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public Optional<Author> authorById(@Argument Long id) {
        return authorService.findOne(id);
    }

    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.findAll();
    }

    @QueryMapping
    public List<Author> findByFirstNameAndYearBorn(@Argument String firstName,@Argument Integer yearBorn) {
        return authorService.getAuthorsByCriteria(firstName, yearBorn);
    }

    @QueryMapping
    public List<Author> findAuthorsByLastName(@Argument String lastName) {
        return authorService.findAllByLastName(lastName);
    }

    @MutationMapping
    public Author createAuthor(@Argument String firstName, @Argument String lastName, @Argument Integer yearBorn) {
        return authorService.saveAuthor(firstName, lastName, yearBorn);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument String firstName, @Argument String lastName, @Argument Integer yearBorn) {
        return authorService.updateAuthor(id, firstName, lastName, yearBorn).orElse(null);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        return authorService.deleteAuthor(id);
    }
}

