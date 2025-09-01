package com.fakesibwork.common.exceptions;

public class UserIsPresentException extends RuntimeException {
    public UserIsPresentException() {
        super("User is already presents");
    }
}
