package com.chitfund.util.exceptions;


public class ChitGroupException extends RuntimeException {
    public ChitGroupException(String message) {
        super(message);
    }

    public ChitGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}

