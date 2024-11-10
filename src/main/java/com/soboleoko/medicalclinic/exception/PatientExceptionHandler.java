package com.soboleoko.medicalclinic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class PatientExceptionHandler  {
    @ExceptionHandler({PatientNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlePatientNotFound(PatientNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMessage> handleDefaultException(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage("Unknown message"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> handleEmailExistsException(EmailAlreadyExistsException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public Map<String, String> handleMethodNotValidException(MethodArgumentNotValidException exception) {
//        HashMap<String, String> errorMap = new HashMap<>();
//        exception.getBindingResult().getFieldErrors().forEach(error ->{
//
//         errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}

