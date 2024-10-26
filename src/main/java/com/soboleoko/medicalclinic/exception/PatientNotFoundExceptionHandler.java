package com.soboleoko.medicalclinic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PatientNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({PatientNotFoundException.class})
    public ResponseEntity<Object> handlePatientNotFound(Exception exception, WebRequest webRequest) {
        return new  ResponseEntity<Object>("Patient not found", new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
}
