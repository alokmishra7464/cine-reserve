package com.cinereserve.cine_reserve.exception;

public class ShowNotFoundException extends RuntimeException {
    public ShowNotFoundException(String message) {
        super(message);
    }
}
