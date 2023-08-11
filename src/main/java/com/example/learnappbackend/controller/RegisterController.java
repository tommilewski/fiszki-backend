package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.RegisterRequest;
import com.example.learnappbackend.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        registerService.register(request);
        return ResponseEntity.ok("");
    }
}
