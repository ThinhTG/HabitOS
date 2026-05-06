package com.habitos.auth_service.exception;

import java.time.Instant;

public class AccountLockedException extends RuntimeException {
    private final Instant lockedUntil;

    public AccountLockedException(Instant lockedUntil) {
        super("Tài khoản bị khoá tạm thời do đăng nhập sai nhiều lần");
        this.lockedUntil = lockedUntil;
    }

    public Instant getLockedUntil() {
        return lockedUntil;
    }
}
