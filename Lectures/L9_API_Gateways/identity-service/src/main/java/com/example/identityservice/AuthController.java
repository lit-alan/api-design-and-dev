package com.example.identityservice;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/token")
    public ResponseEntity<Map<String,Object>> token(@RequestBody(required=false) Map<String,Object> body) {
        return ResponseEntity.ok(Map.of(
                "access_token","demo-token-123",
                "token_type","Bearer",
                "expires_in",3600
        ));
    }
}
