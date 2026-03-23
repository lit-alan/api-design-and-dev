package com.example.graphql.service;


import com.example.graphql.model.Author;
import com.example.graphql.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepo;

    public Optional<Author> findOne(Long id) {
        return authorRepo.findById(id);
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public List<Author> findAllByLastName(String lastName) {
        return authorRepo.findByLastName(lastName);
    }

    public Author saveAuthor(String firstName, String lastName, Integer yearBorn) {
        Author author = new Author(firstName, lastName, yearBorn);
        return authorRepo.save(author);
    }

    public List<Author> getAuthorsByCriteria(String firstName, Integer maxYearBorn) {
        return authorRepo.findByFirstNameIgnoreCaseAndYearBornLessThan(firstName, maxYearBorn);
    }

    public Optional<Author> updateAuthor(Long id, String firstName, String lastName, Integer yearBorn) {
        return authorRepo.findById(id).map(existingAuthor -> {
            if (firstName != null) {
                existingAuthor.setFirstName(firstName);
            }
            if (lastName != null) {
                existingAuthor.setLastName(lastName);
            }
            if (yearBorn != null) {
                existingAuthor.setYearBorn(yearBorn);
            }
            return authorRepo.save(existingAuthor);
        });
    }

    public boolean deleteAuthor(Long id) {
        if (authorRepo.existsById(id)) {
            authorRepo.deleteById(id);
            return true;
        }
        return false;
    }
}

