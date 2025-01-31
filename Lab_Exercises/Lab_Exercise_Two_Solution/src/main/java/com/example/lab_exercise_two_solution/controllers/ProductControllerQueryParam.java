package com.example.lab_exercise_two_solution.controllers;

import com.example.lab_exercise_two_solution.model.Product;
import com.example.lab_exercise_two_solution.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/products")
//class ProductControllerQueryParam {
//
//    private final ProductService productService;
//
//    public ProductControllerQueryParam(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping
//    public Object getProducts(@RequestParam(required = false, defaultValue = "1") String version) {
//        if ("1".equals(version)) {
//            return productService.getAllProductsSimple(); //Returns List<ProductV1>
//        } else if ("2".equals(version)) {
//            return productService.getAllProductsDetailed(); //Returns List<ProductV2>
//        }
//        return productService.getAllProductsSimple(); //Default to v1
//    }
//}
