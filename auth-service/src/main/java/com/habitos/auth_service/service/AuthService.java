package com.habitos.auth_service.service;

import com.habitos.auth_service.dto.request.LoginRequest;
import com.habitos.auth_service.dto.request.RegisterRequest;
import com.habitos.auth_service.dto.response.AuthResponse;
import com.habitos.auth_service.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    UserResponse register(RegisterRequest registerRequest);
}
