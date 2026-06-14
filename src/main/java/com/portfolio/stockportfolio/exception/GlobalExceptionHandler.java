package com.portfolio.stockportfolio.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HoldingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleHoldingNotFound(HoldingNotFoundException ex, HttpServletRequest request){
        return new ErrorResponse(404, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleInsufficientQuantityException(InsufficientQuantityException ex, HttpServletRequest request){
        return new ErrorResponse(422, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }


    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateUsernameException(DuplicateUsernameException ex, HttpServletRequest request){
        return new ErrorResponse(409, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request){
        return new ErrorResponse(401, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleApiException(ApiException ex, HttpServletRequest request){
        return new ErrorResponse(400, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex, HttpServletRequest request){
        return new ErrorResponse(500, ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
    }

}
