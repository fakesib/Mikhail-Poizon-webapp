package com.fakesibwork.common.exceptions;

public class PostCreatingException extends RuntimeException{
    public PostCreatingException() {
        super("Cannot create post");
    }
}
