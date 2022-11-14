package com.itea.shop.exception;

public class ValidationException extends Exception {
//    private String message;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
    }

}
