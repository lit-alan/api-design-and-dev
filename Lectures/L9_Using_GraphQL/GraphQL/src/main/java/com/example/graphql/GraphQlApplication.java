package com.example.graphql;

import com.example.graphql.model.Author;
import com.example.graphql.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphQlApplication implements CommandLineRunner {


    @Autowired
    private AuthorRepository authorRepo;


    public static void main(String[] args) {
        SpringApplication.run(GraphQlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorRepo.save(new Author("Alex", "Watson", 1949));
        authorRepo.save(new Author("Gerry", "Clancy", 1939));
        authorRepo.save(new Author("Sharon", "Sheahan", 1965));
        authorRepo.save(new Author("Alan", "Doyle", 1972));
        authorRepo.save(new Author("Alan", "Doyle", 1994));
        authorRepo.save(new Author("Alan", "Shine", 1967));
        authorRepo.save(new Author("Alan", "O'Brien", 2022));
    }
}
