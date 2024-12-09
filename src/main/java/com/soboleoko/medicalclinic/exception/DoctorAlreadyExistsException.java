package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorAlreadyExistsException extends RuntimeException {

    public final HttpStatus httpStatus;

    public DoctorAlreadyExistsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}