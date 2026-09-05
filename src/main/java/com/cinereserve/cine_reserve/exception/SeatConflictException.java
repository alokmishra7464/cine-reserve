package com.cinereserve.cine_reserve.exception;

public class SeatConflictException extends RuntimeException {
    public SeatConflictException(String message) {
        super(message);
    }
}
