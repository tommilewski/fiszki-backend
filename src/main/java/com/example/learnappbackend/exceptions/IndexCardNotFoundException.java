package com.example.learnappbackend.exceptions;

public class IndexCardNotFoundException extends RuntimeException {

    public IndexCardNotFoundException(String message) {
        super(message);
    }

    public IndexCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexCardNotFoundException(Throwable cause) {
        super(cause);
    }
}
