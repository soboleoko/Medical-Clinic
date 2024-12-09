package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VisitNotAvailableException extends RuntimeException {
    public final HttpStatus httpStatus;

    public VisitNotAvailableException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}