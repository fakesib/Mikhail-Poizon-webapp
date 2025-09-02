package com.fakesibwork.common.exceptions;

public class ProfileUpdateException extends RuntimeException {
    public ProfileUpdateException(String message) {
        super("Profile update exception cause: " + message);
    }
}
