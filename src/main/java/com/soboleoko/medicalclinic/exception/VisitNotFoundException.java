package com.soboleoko.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends RuntimeException {
    public final HttpStatus httpStatus;

    public VisitNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
