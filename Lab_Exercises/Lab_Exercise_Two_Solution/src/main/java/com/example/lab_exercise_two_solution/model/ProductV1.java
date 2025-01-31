package com.example.lab_exercise_two_solution.model;

/**
 * Data Transfer Object (DTO) for version 1 of the Product API.
 * Represents a simplified product view without a rating field.
 *
 * <p>This DTO is used for the v1 API response to ensure backward compatibility
 * and maintain a minimal data structure for older clients.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product.</li>
 *   <li><b>price</b>: The price of the product.</li>
 * </ul>
 */




public class ProductV1 {

    private int id;
    private String name;
    private String description;
    private double price;

    public ProductV1(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
