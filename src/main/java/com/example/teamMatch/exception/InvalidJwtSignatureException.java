package com.example.teamMatch.exception;

public class InvalidJwtSignatureException extends RuntimeException{
    public InvalidJwtSignatureException(String message) {
        super(message);
    }
}
