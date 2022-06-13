package com.homework.izicap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = { ResourceNotFoundException.class, HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails noHandlerFoundException(Exception ex) {
        return new ErrorDetails(404, 4041, ex.getMessage());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails unknownException(Exception ex) {
        return new ErrorDetails(500, 5002, ex.getMessage());
    }
}
