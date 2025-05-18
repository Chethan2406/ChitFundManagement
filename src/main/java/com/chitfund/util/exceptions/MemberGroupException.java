package com.chitfund.util.exceptions;

public class MemberGroupException extends RuntimeException {
    public MemberGroupException(String message) {
        super(message);
    }

    public MemberGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
