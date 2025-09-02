package com.fakesibwork.common.exceptions;

public class UserUpdateException extends RuntimeException {
    public UserUpdateException(String message) {
        super("User cannot be updated because: " + message);
    }
}
