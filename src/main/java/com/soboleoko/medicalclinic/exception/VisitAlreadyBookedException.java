package com.soboleoko.medicalclinic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VisitAlreadyBookedException extends RuntimeException {
    public final HttpStatus httpStatus;

    public VisitAlreadyBookedException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
