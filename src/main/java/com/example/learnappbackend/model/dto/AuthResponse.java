package com.example.learnappbackend.model.dto;

import com.example.learnappbackend.model.enums.Code;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AuthResponse {

    private String timestamp;
    private String message;
    private Code code;

    public AuthResponse(Code code) {
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.message = code.label;
        this.code = code;
    }
}
