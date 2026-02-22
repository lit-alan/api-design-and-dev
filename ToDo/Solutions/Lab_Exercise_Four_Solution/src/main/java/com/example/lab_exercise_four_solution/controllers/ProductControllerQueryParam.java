package com.example.lab_exercise_four_solution.controllers;

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
