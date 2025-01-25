package com.soboleoko.medicalclinic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler({PatientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handlePatientNotFound(PatientNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleDefaultException(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage("Unknown message"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PatientAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handlePatientAlreadyExists(PatientAlreadyExistsException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({DoctorNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleDoctorNotFound(DoctorNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

    @ExceptionHandler({DoctorAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleDoctorAlreadyExists(DoctorAlreadyExistsException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({VisitNotAvailableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleVisitNotAvailable(VisitNotAvailableException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({VisitAlreadyBookedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleVisitAlreadyBooked(VisitAlreadyBookedException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({VisitNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleVisitNotFound(VisitNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler({InstitutionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleInstitutionNotFound(InstitutionNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }
}