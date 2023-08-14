package com.example.learnappbackend.controller;

import com.example.learnappbackend.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"}, allowCredentials = "true")
public class LogoutController {

    private final LogoutService logoutService;
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request){
        return logoutService.logout(request, response);
    }

}
