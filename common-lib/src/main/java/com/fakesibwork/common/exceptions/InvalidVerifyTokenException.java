package com.fakesibwork.common.exceptions;

public class InvalidVerifyTokenException extends RuntimeException {
    public InvalidVerifyTokenException() {
        super("Verify token is missing or empty");
    }
}
