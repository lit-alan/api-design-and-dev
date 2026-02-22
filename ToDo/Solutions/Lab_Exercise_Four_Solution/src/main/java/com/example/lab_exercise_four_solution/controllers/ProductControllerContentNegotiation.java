package com.example.lab_exercise_four_solution.controllers;

import com.example.lab_exercise_four_solution.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductControllerContentNegotiation {

    private final ProductService productService;

    public ProductControllerContentNegotiation(ProductService productService) {
        this.productService = productService;
    }
//
//    @GetMapping(produces = "application/vnd.myapp.v1+json")
//    public ResponseEntity<List<ProductV1>> getProductsV1() {
//        return ResponseEntity.ok()
//              .body(productService.getAllProductsSimple());
//    }
//
//    @GetMapping(produces = "application/vnd.myapp.v2+json")
//    public ResponseEntity<List<ProductV2>> getProductsV2() {
//        return ResponseEntity.ok()
//                .body(productService.getAllProductsDetailed());
//    }

}
