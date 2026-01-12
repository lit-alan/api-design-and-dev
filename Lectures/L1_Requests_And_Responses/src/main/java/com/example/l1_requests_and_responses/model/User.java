package com.example.l1_requests_and_responses.model;

/**
 * Represents an immutable User record with first name, last name, and email address.
 *
 * @param firstName   The user's first name.
 * @param lastName    The user's last name.
 * @param emailAddress The user's email address.
 */
public record User(String firstName, String lastName, String emailAddress) {
}
