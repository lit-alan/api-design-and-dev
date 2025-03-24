package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @GetMapping("/")
    public String getMessage() {
        return "Hello from DEMO " + new Date();
    }
}
