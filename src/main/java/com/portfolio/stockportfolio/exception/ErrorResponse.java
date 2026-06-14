package com.portfolio.stockportfolio.exception;

import java.time.LocalDateTime;

public record ErrorResponse( Integer status,
        String message,
        String path,
        LocalDateTime timestamp) {

}
