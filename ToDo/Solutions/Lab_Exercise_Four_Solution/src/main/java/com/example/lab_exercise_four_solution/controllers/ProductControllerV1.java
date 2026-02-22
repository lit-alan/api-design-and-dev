package com.example.lab_exercise_four_solution.controllers;

import com.example.lab_exercise_four_solution.model.ProductV1;
import com.example.lab_exercise_four_solution.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerV1 {

    private final ProductService productService;

    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductV1> getProducts() {
        return productService.getAllProductsSimple();
    }
}