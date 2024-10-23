package com.abhi.microservices.order.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
            super(message);
    }

}
