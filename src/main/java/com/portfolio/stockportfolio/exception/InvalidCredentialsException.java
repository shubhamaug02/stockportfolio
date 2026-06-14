package com.portfolio.stockportfolio.exception;

public class InvalidCredentialsException extends ApiException{
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
