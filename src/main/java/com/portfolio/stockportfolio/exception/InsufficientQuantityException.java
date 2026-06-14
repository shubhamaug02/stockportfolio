package com.portfolio.stockportfolio.exception;

public class InsufficientQuantityException extends ApiException{
    public InsufficientQuantityException(String message) {
        super(message);
    }

    public InsufficientQuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
