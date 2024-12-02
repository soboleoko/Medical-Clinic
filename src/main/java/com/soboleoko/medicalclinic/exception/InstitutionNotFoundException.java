package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class InstitutionNotFoundException extends RuntimeException {
    public final HttpStatus httpStatus;

    public InstitutionNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
