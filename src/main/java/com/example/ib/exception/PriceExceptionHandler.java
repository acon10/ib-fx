package com.example.ib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class PriceExceptionHandler {
    @ExceptionHandler(PriceFormatException.class)
    public ResponseEntity<?> PriceFormatExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDetail(LocalDate.now(), exception.getMessage(), request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }
}
