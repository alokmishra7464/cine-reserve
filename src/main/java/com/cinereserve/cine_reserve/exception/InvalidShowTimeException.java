package com.cinereserve.cine_reserve.exception;

public class InvalidShowTimeException extends RuntimeException {
    public InvalidShowTimeException(String message) {
        super(message);
    }
}
