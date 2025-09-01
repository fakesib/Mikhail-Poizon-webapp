package com.fakesibwork.common.exceptions;

public class ConfirmMailException extends RuntimeException {
    public ConfirmMailException(String message) {
        super("Cannot confirm mail \n" + message);
    }
}
