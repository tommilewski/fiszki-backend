package com.example.learnappbackend.services;

import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.AuthResponse;
import com.example.learnappbackend.model.dto.LoginRequest;
import com.example.learnappbackend.model.dto.LoginResponse;
import com.example.learnappbackend.model.dto.UserResponse;
import com.example.learnappbackend.model.enums.Code;
import com.example.learnappbackend.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CookieService cookieService;
    private final JwtService jwtService;

    @Value("${jwt.expired}")
    private int expiredToken;

    @Value("${jwt.refresh}")
    private int refreshToken;
    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Not found user with username: " + loginRequest.getUsername()));


        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            Cookie cookie = cookieService.generateCookie("Authorization", jwtService.generateToken(user.getUsername(), expiredToken), expiredToken);
            Cookie refresh = cookieService.generateCookie("refresh", jwtService.generateToken(user.getUsername(), refreshToken), refreshToken);
            response.addCookie(cookie);
            response.addCookie(refresh);
            return ResponseEntity.ok(
                    UserResponse
                            .builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build()
            );
        }
        return ResponseEntity.ok(new AuthResponse(Code.A1));
    }

    public ResponseEntity<?> loginByToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            jwtService.validateToken(request, response);
            String refresh = null;
            for (Cookie value: Arrays.stream(request.getCookies()).toList()) {
                if (value.getName().equals("refresh")) {
                    refresh = value.getValue();
                }
            }
            String login = jwtService.getSubject(refresh);
            User user = userRepository.findByUsername(login).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(
                        UserResponse
                                .builder()
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build()
                );
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(Code.A1));
        } catch (IllegalArgumentException | ExpiredJwtException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(Code.A3));
        }

    }

    public ResponseEntity<LoginResponse> loggedIn(HttpServletRequest request, HttpServletResponse response) {
        try {
            jwtService.validateToken(request, response);
            return ResponseEntity.ok(new LoginResponse(true));
        } catch (IllegalArgumentException | ExpiredJwtException exception) {
            return ResponseEntity.ok(new LoginResponse(false));
        }
    }
}
