package com.chitfund.util.exceptions;

public class MenuException extends RuntimeException { 

    public MenuException(String message,Throwable reason) {
        super(message,reason);
    }

}
