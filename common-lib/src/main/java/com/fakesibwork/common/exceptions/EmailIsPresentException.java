package com.fakesibwork.common.exceptions;

public class EmailIsPresentException extends UserUpdateException {
    public EmailIsPresentException(String email) {
        super(email + " email is already presents");
    }
}
