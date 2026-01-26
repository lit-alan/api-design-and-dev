package com.example.l3_adding_hateoas_to_an_api.service;

import com.example.l3_adding_hateoas_to_an_api.model.Book;
import com.example.l3_adding_hateoas_to_an_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class    BookService {

    @Autowired
    private BookRepository bookRepo;

    public Optional<Book> findOne(Long id) {
        return bookRepo.findById(id);
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepo.findAll();
    }
    
     public List<Book> findBooksByAuthor(Long authorID) {
        return bookRepo.getAllByAuthorID(authorID);
    }

    public long count() {
        return bookRepo.count();
    }

    public void deleteByID(long authorID) {
        bookRepo.deleteById(authorID);
    }

    public void saveBook(Book a) {
        bookRepo.save(a);
    }  
    
    
}//end class

