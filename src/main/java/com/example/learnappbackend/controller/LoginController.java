package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.LoginRequest;
import com.example.learnappbackend.model.dto.LoginResponse;
import com.example.learnappbackend.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return loginService.login(loginRequest, response);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/auto-login")
    public ResponseEntity<?> autoLogin(HttpServletRequest request, HttpServletResponse response) {
        return loginService.loginByToken(request, response);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/logged-in")
    public ResponseEntity<LoginResponse> loggedIn(HttpServletRequest request, HttpServletResponse response) {
        return loginService.loggedIn(request, response);
    }
}
