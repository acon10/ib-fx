package com.example.ib.exception;

public class PriceCsvValidationException extends NumberFormatException{
    public PriceCsvValidationException(String message) {
        super(message);
    }
}
