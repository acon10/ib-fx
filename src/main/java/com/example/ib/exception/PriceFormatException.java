package com.example.ib.exception;

public class PriceFormatException extends RuntimeException{
    public PriceFormatException(String message) {
        super(message);
    }
}
