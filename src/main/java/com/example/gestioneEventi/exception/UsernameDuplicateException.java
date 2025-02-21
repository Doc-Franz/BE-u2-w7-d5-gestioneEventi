package com.example.gestioneEventi.exception;

public class UsernameDuplicateException extends RuntimeException {
    public UsernameDuplicateException(String message) {
        super(message);
    }
}
