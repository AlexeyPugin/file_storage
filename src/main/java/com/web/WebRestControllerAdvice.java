package com.web;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseMessage handleNotFoundException(NotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseMessage handleRuntimeException(RuntimeException ex) {
        return new ResponseMessage(ex.getMessage());
    }
}
