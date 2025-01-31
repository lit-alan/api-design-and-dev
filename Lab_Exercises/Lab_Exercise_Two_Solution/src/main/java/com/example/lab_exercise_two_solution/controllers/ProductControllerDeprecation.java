package com.example.lab_exercise_two_solution.controllers;


import com.example.lab_exercise_two_solution.model.ProductV1;
import com.example.lab_exercise_two_solution.model.ProductV2;
import com.example.lab_exercise_two_solution.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//
//@RestController
//@RequestMapping("/api/products")
//class ProductControllerDeprecation {
//
//    private final ProductService productService;
//
//    public ProductControllerDeprecation(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping(produces = "application/vnd.myapp.v1+json")
//    public ResponseEntity<List<ProductV1>> getProductsV1() {
//        return ResponseEntity.ok()
//                .header("Warning", "199 - \"API version v1 is deprecated and will be removed on Jan 31st 2025\"")
//                .body(productService.getAllProductsSimple());
//    }
//
//    @GetMapping(produces = "application/vnd.myapp.v2+json")
//    public ResponseEntity<List<ProductV2>> getProductsV2() {
//        return ResponseEntity.ok()
//                .body(productService.getAllProductsDetailed());
//    }
//}
