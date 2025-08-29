package com.fakesibwork.database.exception;

public class EmailIsPresentException extends RuntimeException {
    public EmailIsPresentException() {
        super("Email is already presents");
    }
}
