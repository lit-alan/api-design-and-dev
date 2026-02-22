package com.example.lab_exercise_four_solution.controllers;


import com.example.lab_exercise_four_solution.model.ProductV2;
import com.example.lab_exercise_four_solution.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/products")
class ProductControllerV2 {

    private final ProductService productService;

    public ProductControllerV2(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductV2> getProducts() {
        return productService.getAllProductsDetailed();
    }
}