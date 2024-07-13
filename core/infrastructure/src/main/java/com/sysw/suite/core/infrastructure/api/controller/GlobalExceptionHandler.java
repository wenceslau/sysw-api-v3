package com.sysw.suite.core.infrastructure.api.controller;

import com.sysw.suite.core.exception.DomainException;
import com.sysw.suite.core.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<?> handleDomainException(DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex))   ;
    }

    record ApiError(String message, List<Error> errors) {
        public static Object from(DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
            //return new ApiError(ex.getMessage(), ex.getErrors().stream().map(Error::getMessage).toList());
        }
    }
}
