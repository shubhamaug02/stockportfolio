package com.portfolio.stockportfolio.exception;

public class HoldingNotFoundException extends ApiException {
    public HoldingNotFoundException(String message) {
        super(message);
    }

    public HoldingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
