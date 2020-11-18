package com.yaangvu.mypassword.handler;

import com.yaangvu.mypassword.responses.ErrorResponse;
import com.yaangvu.mypassword.responses.ValidationErrorResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExHandler {
    @Autowired
    ValidationErrorResponse errorResponse;
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        errorResponse = new ValidationErrorResponse();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.addViolations(fieldName, errorMessage);
        });
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        
        return errorResponse.getResponse();
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        errorResponse = new ValidationErrorResponse();
        
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorResponse.addViolations(violation.getPropertyPath().toString(), violation.getMessageTemplate());
        }
        
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        return errorResponse.getResponse();
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getLocalizedMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        
        return errorResponse.getResponse();
    }
    
    //    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getLocalizedMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        
        return errorResponse.getResponse();
    }
}
