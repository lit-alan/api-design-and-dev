package sd4.com.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sd4.com.application.model.Book;
import sd4.com.application.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    public void saveBook(Book a) {
        bookRepo.save(a);
    }


    public Page<Book> findAll(Pageable pageable) {
        return bookRepo.findAll(pageable); //call the provided findAll
    }


    public Optional<Book> findOne(Long id) {
        return bookRepo.findById(id);
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepo.findAll();
    }
    
    public long count() {
        return bookRepo.count();
    }

    public void deleteByID(long authorID) {
        bookRepo.deleteById(authorID);
    }



    public Book updateBook(Book updatedBook) {
        Optional<Book> existingBook = bookRepo.findById(updatedBook.getBookId());

        if (existingBook.isPresent()) {
            Book book = existingBook.get();

            // Set new values for the book attributes you want to change
            book.setTitle(updatedBook.getTitle());
            book.setPublisher(updatedBook.getPublisher());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublicationDate(updatedBook.getPublicationDate());
            book.setPrice(updatedBook.getPrice());

            // Save the book back to the database
            bookRepo.save(book);

            return book;
        } else {
            // Handle the case where the book is not found
            throw new EntityNotFoundException("Book not found with id " + updatedBook.getBookId());
        }
    }

}//end class

