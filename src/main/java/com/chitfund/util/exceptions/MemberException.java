package com.chitfund.util.exceptions;


public class MemberException extends RuntimeException {
    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
}

