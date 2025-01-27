
package com.example.l3_adding_hateoas_to_an_api.repository;

import com.example.l3_adding_hateoas_to_an_api.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    

    public List<Book> getAllByAuthorID(Long id);
}
