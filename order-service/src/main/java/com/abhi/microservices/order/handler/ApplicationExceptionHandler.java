package com.abhi.microservices.order.handler;

import com.abhi.microservices.order.exception.EmailAlreadyPresentException;
import com.abhi.microservices.order.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyPresentException.class)
    public Map<String, String> handleEmailAlreadyPresentException(EmailAlreadyPresentException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }
}
