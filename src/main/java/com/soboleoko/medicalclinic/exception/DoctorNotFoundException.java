package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorNotFoundException extends RuntimeException {
    public final HttpStatus httpStatus;

    public DoctorNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}