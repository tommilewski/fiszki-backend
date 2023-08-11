package com.example.learnappbackend.model.dto;

import com.example.learnappbackend.model.enums.Code;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LoginResponse {

    private String timestamp;
    private boolean message;
    private Code code;

    public LoginResponse(boolean message) {
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.message = message;
        this.code = Code.SUCCESS;
    }
}
