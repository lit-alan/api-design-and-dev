
package com.example.orderservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@RequestBody(required=false) Map<String,Object> body) {
        return ResponseEntity.ok(Map.of(
            "orderId", UUID.randomUUID().toString(),
            "status", "CREATED",
            "request", body
        ));
    }

    @GetMapping("/{id}")
    public Map<String,Object> get(@PathVariable String id) {
        return Map.of("orderId", id, "status", "PROCESSING");
    }
}
