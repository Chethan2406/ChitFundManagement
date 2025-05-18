package com.chitfund.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.chitfund.util.exceptions.MenuException;
import com.chitfund.util.exceptions.ResourceNotFoundException;
import com.chitfund.util.exceptions.UserException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MenuException.class)
    public ResponseEntity<String> handleMenuRelatedException(MenuException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 600);
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserRelatedException(UserException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 601);
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 500);
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationExceptionExceptions(Exception ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 400);
        errorMap.put("message", "Duplicate entry or constraint violation occurred. Please check the data.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadJson(HttpMessageNotReadableException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 500);
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("statusCode", 400);
        errorMap.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }

}
