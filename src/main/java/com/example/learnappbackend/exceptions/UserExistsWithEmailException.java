package com.example.learnappbackend.exceptions;

public class UserExistsWithEmailException extends RuntimeException {

    public UserExistsWithEmailException(String message) {
        super(message);
    }

    public UserExistsWithEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsWithEmailException(Throwable cause) {
        super(cause);
    }
}
