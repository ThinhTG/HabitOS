package com.habitos.auth_service.service.Impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.habitos.auth_service.dto.request.LoginRequest;
import com.habitos.auth_service.dto.request.RegisterRequest;
import com.habitos.auth_service.dto.response.AuthResponse;
import com.habitos.auth_service.dto.response.UserResponse;
import com.habitos.auth_service.entity.User;
import com.habitos.auth_service.exception.UserAlreadyExistsException;
import com.habitos.auth_service.mapper.UserMapper;
import com.habitos.auth_service.repository.UserRepository;
import com.habitos.auth_service.service.AuthService;
import com.habitos.auth_service.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        var user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new UserAlreadyExistsException("User with email " + registerRequest.email() + " already exists");
        }
        User user = userMapper.convertToEntity(registerRequest);
        user.setPassword_hash(passwordEncoder.encode(registerRequest.password()));
        user.set_active(true);
        user.set_locked(false);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return userMapper.convertToDto(savedUser);
    }
}
