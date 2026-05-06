package com.habitos.auth_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailExists(EmailAlreadyExistsException ex, HttpServletRequest req) {
        return problem(HttpStatus.CONFLICT, "Email đã tồn tại", ex.getMessage(), req);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest req) {
        return problem(HttpStatus.UNAUTHORIZED, "Xác thực thất bại", ex.getMessage(), req);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ProblemDetail handleLocked(AccountLockedException ex, HttpServletRequest req) {
        var pd = problem(HttpStatus.LOCKED, "Tài khoản bị khoá", ex.getMessage(), req);
        pd.setProperty("lockedUntil", ex.getLockedUntil());
        return pd;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ProblemDetail handleInvalidToken(InvalidTokenException ex, HttpServletRequest req) {
        return problem(HttpStatus.UNAUTHORIZED, "Token không hợp lệ", ex.getMessage(), req);
    }

    // Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (a, b) -> a));
        var pd = problem(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ",
                "Vui lòng kiểm tra lại các trường bên dưới", req);
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception at {}", req.getRequestURI(), ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống",
                "Đã có lỗi xảy ra, vui lòng thử lại sau", req);
    }

    // ----------------------------------------------------------------
    // Helper
    // ----------------------------------------------------------------

    private ProblemDetail problem(HttpStatus status, String title, String detail,
                                  HttpServletRequest req) {
        var pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}