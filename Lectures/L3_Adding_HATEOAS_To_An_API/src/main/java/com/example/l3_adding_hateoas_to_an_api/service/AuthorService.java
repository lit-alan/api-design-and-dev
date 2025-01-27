package com.example.l3_adding_hateoas_to_an_api.service;


import com.example.l3_adding_hateoas_to_an_api.model.Author;
import com.example.l3_adding_hateoas_to_an_api.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorService  {

    @Autowired
    private AuthorRepository authorRepo;

    public Optional<Author> findOne(Long id) {
        return authorRepo.findById(id);
    }

    public Page<Author> findAllWithPagination(Pageable pageable) {
        return authorRepo.findAll(pageable);
    }


    public Page<Author> findAllByLastName(String lastName, Pageable pageable) {
        return authorRepo.findAllByLastName(lastName, pageable);
    }


    public Page<Author> findAllByYearBorn(int yearBorn, Pageable pageable) {
        return authorRepo.findAllByYearBorn(yearBorn, pageable);
    }

    public Page<Author> searchAuthors(String firstName, String lastName, Pageable pageable) {
        return authorRepo.findAllByFirstNameContainingAndLastNameContainingAllIgnoreCase(
                firstName, lastName, pageable);
    }

    public List<Author> findAll() {
        return (List<Author>) authorRepo.findAll();
    }

    public long count() {
        return authorRepo.count();
    }

    public void deleteByID(long authorID) {
        authorRepo.deleteById(authorID);
    }

    public Author saveAuthor(Author author) {
        if (author.getAuthorID() != 0) {
            boolean exists = authorRepo.existsById(author.getAuthorID());
            if (!exists) {
                throw new IllegalArgumentException("Author with ID " + author.getAuthorID() + " does not exist. Use POST to create new Author.");
            }
        } else {
            System.out.println("Creating a new Author: " + author);
        }

        return authorRepo.save(author);
    }



    public Page<Author> findByYearBornBetween(Integer startYear, Integer endYear, Pageable pageable) {
        return authorRepo.findByYearBornBetween(startYear, endYear, pageable);
    }

    public List<Author> findByFirstNameNameStartingWith(String prefix) {
        return (List<Author>) authorRepo.findAllByFirstNameStartingWith(prefix);
    }

    public List<Author> findByFirstNameNameEndingWith(String suffix) {
        return (List<Author>) authorRepo.findAllByFirstNameEndingWith(suffix);
    }

    public List<Author> findByfirstNameContaining(String infix) {
        return (List<Author>) authorRepo.findAllByFirstNameContaining(infix);
    }

    public List<Author> findAuthorByYearBornBetweenOrderByFirstName(Integer start, Integer end) {
        return (List<Author>) authorRepo.findAuthorByYearBornBetweenOrderByFirstName(start, end);
    }

    public List<Author> findByyearBornLessThanEqual(int year) {
        return (List<Author>) authorRepo.findByyearBornLessThanEqual(year);
    }

        public Integer getAvgYearBorn() {
        return authorRepo.getAverageYearBorn();
    }
}//end class



//    public Integer getAvgYearBorn() {
//        return authorRepo.getAverageYearBorn();
//    }
//
//    public List<Author> findAuthorByYearBornBetweenOrderByFirstName(Integer start, Integer end) {
//        return (List<Author>) authorRepo.findAuthorByYearBornBetweenOrderByFirstName(start, end);
//    }
//
//    public List<Author> findByyearBornLessThanEqual(int year) {
//        return (List<Author>) authorRepo.findByyearBornLessThanEqual(year);
//    }
//
//    public List<Author> findByFirstNameNameStartingWith(String prefix) {
//        return (List<Author>) authorRepo.findByfirstNameStartingWith(prefix);
//    }
//
//    public List<Author> findByfirstNameContaining(String infix) {
//        return (List<Author>) authorRepo.findByfirstNameContaining(infix);
//    }
//
//    public List<Author> findBylastNameEndingWith(String suffix) {
//        return (List<Author>) authorRepo.findBylastNameEndingWith(suffix);
//    }
