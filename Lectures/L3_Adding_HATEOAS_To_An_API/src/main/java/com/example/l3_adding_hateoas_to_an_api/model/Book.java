package com.example.l3_adding_hateoas_to_an_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookId;

    @NotBlank(message = "{book.titleNotEmpty}")
    private String title;

    @NotBlank(message="{book.publisherNotEmpty}")
    @Size(min = 5, message="{book.publisherSize}")
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;

    @NotNull(message="{book.priceNull}")
    @DecimalMin("1.0")
    private Double price;

    @NotBlank(message="{book.isbnNull}")
    private String isbn;

    private long authorID;

    public Book(String title, String publisher, Date publicationDate, Double price, String isbn, long authorID) {
        this.title = title;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.price = price;
        this.isbn = isbn;
        this.authorID = authorID;
    }

    public Book() {
    }

    public long getBookId() {
        return this.bookId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public long getAuthorID() {
        return this.authorID;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public String toString() {
        return "Book(bookId=" + this.getBookId() + ", title=" + this.getTitle() + ", publisher=" + this.getPublisher() + ", publicationDate=" + this.getPublicationDate() + ", price=" + this.getPrice() + ", isbn=" + this.getIsbn() + ", authorID=" + this.getAuthorID() + ")";
    }
}

