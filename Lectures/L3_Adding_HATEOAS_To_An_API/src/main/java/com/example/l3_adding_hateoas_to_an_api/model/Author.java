package com.example.l3_adding_hateoas_to_an_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Author extends RepresentationModel<Author> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorID;
    private String firstName;
    private String lastName;
    private int yearBorn;

    public Author(String firstName, String lastName, int yearBorn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearBorn = yearBorn;
    }

    public Author() {
    }

    public long getAuthorID() {
        return this.authorID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getYearBorn() {
        return this.yearBorn;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    public String toString() {
        return "Author(authorID=" + this.getAuthorID() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", yearBorn=" + this.getYearBorn() + ")";
    }
}

