package com.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Author  {

    //RegX for the first and last name.
    //letters only at start
    //allows internal segments joined by hyphen/apostrophe
    //no spaces, no leading/trailing punctuation, no consecutive punctuation
    private static final String HUMAN_NAME_REGEX = "^(?=.{1,80}$)[A-Za-z]+(?:[-'][A-Za-z]+)*$";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorID;

    @NotBlank(message = "firstName is required")
    @Size(min = 1, max = 50, message = "firstName must be 1..50 characters")
    @Pattern(regexp = HUMAN_NAME_REGEX, message = "firstName contains invalid characters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(min = 1, max = 80, message = "lastName must be 1..80 characters")
    @Pattern(regexp = HUMAN_NAME_REGEX, message = "lastName contains invalid characters")
    private String lastName;

    @Min(value = 1000, message = "yearBorn must be >= 1000")
    @Max(value = 2026, message = "yearBorn cannot be in the future") //I'm hard coding the year here - not ideal
    private int yearBorn;
}

