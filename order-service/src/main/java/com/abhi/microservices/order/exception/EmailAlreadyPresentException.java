package com.abhi.microservices.order.exception;

public class EmailAlreadyPresentException extends RuntimeException{
    public EmailAlreadyPresentException(String message){
        super(message);
    }
}
