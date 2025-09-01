package com.fakesibwork.common.exceptions;

public class EmailIsPresentException extends RuntimeException {
    public EmailIsPresentException() {
        super("Email is already presents");
    }
}
