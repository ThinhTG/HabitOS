package com.habitos.auth_service.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Email hoặc password không đúng");
    }
}