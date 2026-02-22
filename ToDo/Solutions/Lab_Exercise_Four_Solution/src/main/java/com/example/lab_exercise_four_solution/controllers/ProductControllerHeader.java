package com.example.lab_exercise_four_solution.controllers;

import com.example.lab_exercise_four_solution.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductControllerHeader {

    private final ProductService productService;

    public ProductControllerHeader(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getProducts(@RequestHeader(value = "X-API-Version", defaultValue = "1") String version) {
        if ("1".equals(version)) {
            return productService.getAllProductsSimple();
        } else if ("2".equals(version)) {
            return productService.getAllProductsDetailed();
        }
        return productService.getAllProductsSimple(); //Default to v1
    }
}
