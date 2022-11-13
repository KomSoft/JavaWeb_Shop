package com.itea.shop.exception;

public class DataBaseException extends Exception {
    private String message;

    public DataBaseException(String message) {
        this.message = message;
    }

    public DataBaseException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
