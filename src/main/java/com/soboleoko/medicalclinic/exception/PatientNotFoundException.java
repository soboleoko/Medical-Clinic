package com.soboleoko.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends RuntimeException{

  public final HttpStatus httpStatus;

    public PatientNotFoundException(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
