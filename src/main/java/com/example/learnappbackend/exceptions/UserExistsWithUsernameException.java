package com.example.learnappbackend.exceptions;

public class UserExistsWithUsernameException extends RuntimeException {

    public UserExistsWithUsernameException(String message) {
        super(message);
    }

    public UserExistsWithUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsWithUsernameException(Throwable cause) {
        super(cause);
    }
}
