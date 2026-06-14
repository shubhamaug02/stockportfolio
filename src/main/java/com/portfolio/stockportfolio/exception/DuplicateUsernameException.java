package com.portfolio.stockportfolio.exception;

public class DuplicateUsernameException extends ApiException{
    public DuplicateUsernameException(String message) {
        super(message);
    }

    public DuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
