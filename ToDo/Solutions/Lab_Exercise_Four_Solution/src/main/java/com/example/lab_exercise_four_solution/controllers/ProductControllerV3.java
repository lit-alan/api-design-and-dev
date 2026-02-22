package com.example.lab_exercise_four_solution.controllers;


import com.example.lab_exercise_four_solution.model.ProductV2;
import com.example.lab_exercise_four_solution.service.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v3/products")
class ProductControllerV3 {

    private final ProductService productService;

    public ProductControllerV3(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductV2>> getProductV2(@PathVariable int id) {
        Optional<ProductV2> optionalProduct = productService.getProductV2ById(id);

        if (optionalProduct.isPresent()) {
            ProductV2 product = optionalProduct.get();

            //add HATEOAS self-link
            Link selfLink = linkTo(methodOn(ProductControllerV3.class).getProductV2(id)).withSelfRel();
            Link allProductsLink = linkTo(methodOn(ProductControllerV3.class).getProducts()).withRel("all-products");

            //wrap the product in an EntityModel with links
            EntityModel<ProductV2> entityModel = EntityModel.of(product, selfLink, allProductsLink);

            return ResponseEntity.ok(entityModel);
        } else {
            //eeturn 404 if the product is not found
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public CollectionModel<ProductV2> getProducts() {

        List<ProductV2> pList = productService.getAllProductsDetailed();

        for (ProductV2 p : pList) {
            int id = p.getId();
            Link selfLink = linkTo(methodOn(ProductControllerV3.class).getProductV2(id)).withSelfRel();
            p.add(selfLink);
        }

        Link link = linkTo(methodOn(ProductControllerV3.class).getProducts()).withRel("all-products");
        return CollectionModel.of(pList, link);
    }
}