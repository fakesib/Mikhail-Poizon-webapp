package com.fakesibwork.common.exceptions;

public class IncorrectUsernameException extends RuntimeException {

    public IncorrectUsernameException(String username) {
        super(username + " is incorrect");
    }
}
