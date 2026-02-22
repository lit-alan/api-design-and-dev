package com.example.lab_exercise_four_solution.service;

import com.example.lab_exercise_four_solution.model.Product;
import com.example.lab_exercise_four_solution.model.ProductV1;
import com.example.lab_exercise_four_solution.model.ProductV2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final Map<Integer, Product> productMap = Map.of(
            1, new Product(1, "Wireless Headphones", "Noise-cancelling over-ear headphones", 199.99),
            2, new Product(2, "Smartphone", "Latest model with 128GB storage and 5G connectivity", 899.00),
            3, new Product(3, "Laptop", "15-inch laptop with 16GB RAM and 512GB SSD", 1299.50),
            4, new Product(4, "Gaming Console", "Next-gen gaming console with 1TB storage", 499.99),
            5, new Product(5, "Electric Kettle", "1.7L electric kettle with temperature control", 59.99),
            6, new Product(6, "Smartwatch", "Fitness tracking smartwatch with GPS and heart rate monitor", 149.95),
            7, new Product(7, "Bluetooth Speaker", "Portable speaker with 12-hour battery life", 79.99),
            8, new Product(8, "4K TV", "55-inch 4K UHD Smart TV with HDR support", 699.00),
            9, new Product(9, "Air Purifier", "HEPA filter air purifier for large rooms", 249.50),
            10, new Product(10, "Robot Vacuum Cleaner", "Wi-Fi connected robot vacuum with app control", 399.00)
    );


    //retrieve a single product by ID
    public Optional<ProductV2> getProductV2ById(int id) {
        //fetch the Product by ID from the map
        return Optional.ofNullable(productMap.get(id))
                .map(product -> new ProductV2(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        generateRandomRating() // Set the rating dynamically for v2
                ));
    }


    //this is a basic version without ratings
    public List<ProductV1> getAllProductsSimple() {
        return productMap.values().stream()
                .map(product -> new ProductV1(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .collect(Collectors.toList());
    }

    //a more detailed version with product ratings
    public List<ProductV2> getAllProductsDetailed() {
        return productMap.values().stream()
                .map(product -> new ProductV2(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        generateRandomRating() //Set the rating dynamically for v2
                ))
                .collect(Collectors.toList());
    }



    //Generate a random rating between 1 and 5
    private int generateRandomRating() {
        return (int) (1 + Math.random() * 5);
    }
}
