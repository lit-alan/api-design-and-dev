package com.example.lab_exercise_four_solution.model;
/**
 * Represents the core data model for a product in the application.
 * This class serves as the central entity for product-related operations,
 * containing all fields necessary for internal processing and storage.
 *
 * <p>This class is the definitive data model for a product within the application
 * API responses should use version-specific DTOs (e.g., {@link ProductV1},
 * {@link ProductV2}) to ensure backward compatibility and to control exposed fields.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product's features.</li>
 *   <li><b>price</b>: The price of the product.</li>
 *   <li><b>rating</b>: The average user rating of the product, ranging from 1 to 5.</li>
 * </ul>
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int rating;

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }
    public Product(int id, String name, String description, double price, int rating) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}