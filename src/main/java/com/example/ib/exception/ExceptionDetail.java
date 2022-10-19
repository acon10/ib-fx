package com.example.ib.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDetail {
    private LocalDate timestamp;
    private String message;
    private String detail;

}
