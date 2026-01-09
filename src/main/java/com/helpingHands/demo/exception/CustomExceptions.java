package com.helpingHands.demo.exception;

@SuppressWarnings("serial")
public class CustomExceptions extends RuntimeException {
    public CustomExceptions(String message) {
        super(message);
    }
}
