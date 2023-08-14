package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.AuthResponse;
import com.example.learnappbackend.model.dto.RegisterRequest;
import com.example.learnappbackend.model.enums.Code;
import com.example.learnappbackend.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin()
public class RegisterController {

    private final RegisterService registerService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request) {
        registerService.register(request);
        return ResponseEntity.ok(new AuthResponse(Code.SUCCESS));
    }
}
