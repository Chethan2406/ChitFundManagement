package com.chitfund.util.exceptions;

public class UserException extends RuntimeException{

    public UserException(String message,Throwable reason) {
        super(message,reason);
    }

     public UserException(String message) {
        super(message);
    }
}
