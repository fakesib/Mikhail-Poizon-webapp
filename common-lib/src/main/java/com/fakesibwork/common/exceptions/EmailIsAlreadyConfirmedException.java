package com.fakesibwork.common.exceptions;

public class EmailIsAlreadyConfirmedException extends ConfirmMailException {
    public EmailIsAlreadyConfirmedException() {
        super("Email: is already confirmed");
    }
}
