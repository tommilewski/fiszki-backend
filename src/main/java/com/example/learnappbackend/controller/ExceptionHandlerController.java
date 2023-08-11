package com.example.learnappbackend.controller;

import com.example.learnappbackend.exceptions.UserExistsWithEmailException;
import com.example.learnappbackend.exceptions.UserExistsWithUsernameException;
import com.example.learnappbackend.model.dto.AuthResponse;
import com.example.learnappbackend.model.enums.Code;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(UserExistsWithEmailException.class)
    public ResponseEntity<AuthResponse> handleUserExistsWithEmailException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(Code.A4));
    }

    @ExceptionHandler(UserExistsWithUsernameException.class)
    public ResponseEntity<AuthResponse> handleUserExistsWithNameException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(Code.A5));
    }

    @ExceptionHandler({IllegalArgumentException.class, ExpiredJwtException.class})
    public ResponseEntity<AuthResponse> handleJwtException() {
        return ResponseEntity.status(401).body(new AuthResponse(Code.A3));
    }
}
