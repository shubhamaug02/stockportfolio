package com.portfolio.stockportfolio.exception;

public class RateLimitExceededException extends ApiException{
    public RateLimitExceededException(String message) {
        super(message);
    }

    public RateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
