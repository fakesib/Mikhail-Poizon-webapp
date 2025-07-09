package com.fakesibwork.database.exception;

public class UserIsPresentException extends RuntimeException {
    public UserIsPresentException() {
        super("User is already presents");
    }
}
