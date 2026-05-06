package com.habitos.auth_service.controller;

import com.habitos.auth_service.dto.ApiResponse;
import com.habitos.auth_service.dto.request.LoginRequest;
import com.habitos.auth_service.dto.request.RegisterRequest;
import com.habitos.auth_service.dto.response.AuthResponse;
import com.habitos.auth_service.dto.response.UserResponse;
import com.habitos.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService _authService) {
        this.authService = _authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse res = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest registerRequest) {
        UserResponse res =  authService.register(registerRequest);
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
