package com.example.lab_exercise_four_solution.model;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) for version 2 of the Product API.
 * Represents an enhanced product view that includes a rating field.
 *
 * <p>This DTO is used for the v2 API response, providing additional product
 * details to meet the requirements of newer clients.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product.</li>
 *   <li><b>price</b>: The price of the product.</li>
 *   <li><b>rating</b>: The average user rating of the product, ranging from 1 to 5.</li>
 * </ul>
 */

public class ProductV2 extends RepresentationModel<ProductV2>  {
    private int id;
    private String name;
    private String description;
    private double price;
    private int rating;

    public ProductV2(int id, String name, String description, double price, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}