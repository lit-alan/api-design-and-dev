package com.example.l3_adding_hateoas_to_an_api.application;

import com.example.l3_adding_hateoas_to_an_api.model.Author;
import com.example.l3_adding_hateoas_to_an_api.model.Book;
import com.example.l3_adding_hateoas_to_an_api.repository.AuthorRepository;
import com.example.l3_adding_hateoas_to_an_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.example.l3_adding_hateoas_to_an_api.service", "com.example.l3_adding_hateoas_to_an_api.controllers"})
@EntityScan("com.example.l3_adding_hateoas_to_an_api.model")
@EnableJpaRepositories("com.example.l3_adding_hateoas_to_an_api.repository")
public class L3AddingHateoasToAnApiApplication implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(L3AddingHateoasToAnApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorRepo.save(new Author("Alex", "Watson", 1949));
        authorRepo.save(new Author("Gerry", "Guinane", 1939));
        authorRepo.save(new Author("Sharon", "Byrne", 1965));
        authorRepo.save(new Author("Alan", "Doyle", 1972));
        authorRepo.save(new Author("Barry", "Coyle", 1972));


        Date now = new Date();
        bookService.saveBook(new Book("Hitting The High Notes", "LIT Publishing", new Date(now.getTime() - (1000 * 60 * 60 * 24 * 5)) , 1.25, "BR78569524" ,1));
        bookService.saveBook(new Book("Life, Love and LIT", "LIT Publishing",  new Date(now.getTime() - (1000 * 60 * 60 * 24 * 10)), 16.25, "PP98765431",4));
        bookService.saveBook(new Book("How To Survive In A World Gone Mad", "LIT Publishing",  new Date(now.getTime() - (1000 * 60 * 60 * 24 * 15)), 19.99, "XDW34234123",3));
        bookService.saveBook(new Book("The Running Man", "LIT Publishing",  new Date(now.getTime() - (1000 * 60 * 60 * 24 * 15)), 9.99, "PD404234123",1));
        bookService.saveBook(new Book("Snamhai Sasta", "Dark Work Publishing",  new Date(now.getTime() - (1000 * 60 * 60 * 24 * 25)), 16.99, "MJ7y234123",2));
        bookService.saveBook(new Book("No More Monkey Business", "IB Publishing", new Date(now.getTime() - (1000 * 60 * 60 * 24 * 25)), 16.99, "MJ7y234123",1));

    }
}
