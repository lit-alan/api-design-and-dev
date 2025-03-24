
package com.example.graphql.repositories;

import com.example.graphql.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByLastName(String lastName);
    List<Author> findByFirstNameIgnoreCaseAndYearBornLessThan(String firstName, Integer maxYearBorn);

}

