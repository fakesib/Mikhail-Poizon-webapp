package com.fakesibwork.common.exceptions;

import lombok.Getter;

@Getter
public class IncorrectRegistrationPathException extends RuntimeException {

    private String path;

    public IncorrectRegistrationPathException(String path) {
        super("Path is incorrect to be registered");
        this.path = path;
    }

    @Override
    public String toString() {
        return super.toString() + "Description: Incorrect path in " + path;
    }
}
