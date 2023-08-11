package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.AuthResponse;
import com.example.learnappbackend.model.enums.Code;
import com.example.learnappbackend.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(HttpServletRequest request, HttpServletResponse response) {
        jwtService.validateToken(request, response);
        return ResponseEntity.ok(new AuthResponse(Code.PERMIT));

    }
}
