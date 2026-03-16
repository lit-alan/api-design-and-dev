package com.example.catalogservice;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @GetMapping("/products")
    public List<Map<String,Object>> products() {
        return List.of(
                Map.of("id",1,"name","Laptop","price",999.99),
                Map.of("id",2,"name","Phone","price",599.99)
        );
    }

    @GetMapping("/products/{id}")
    public Map<String,Object> product(@PathVariable int id) {
        return Map.of("id", id, "name", "Product-"+id, "price", 123.45);
    }
}
