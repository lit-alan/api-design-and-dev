package com.example.l8_using_swagger;

import com.example.l8_using_swagger.model.Author;
import com.example.l8_using_swagger.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class L8_Using_Swagger implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepo;

    public static void main(String[] args) {
        SpringApplication.run(L8_Using_Swagger.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorRepo.save(new Author("Alex", "Watson", 1949));
        authorRepo.save(new Author("Gerry", "Guinane", 1939));
        authorRepo.save(new Author("Sharon", "Byrne", 1965));
        authorRepo.save(new Author("Alan", "Doyle", 1972));
        authorRepo.save(new Author("Barry", "Coyle", 1972));


    }
}
