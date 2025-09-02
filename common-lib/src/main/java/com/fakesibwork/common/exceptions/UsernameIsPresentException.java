package com.fakesibwork.common.exceptions;

public class UsernameIsPresentException extends UserUpdateException{
    public UsernameIsPresentException(String message) {
        super(message + " is already presents");
    }
}
