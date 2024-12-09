package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatientAlreadyExistsException extends RuntimeException {

    public final HttpStatus httpStatus;

    public PatientAlreadyExistsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}