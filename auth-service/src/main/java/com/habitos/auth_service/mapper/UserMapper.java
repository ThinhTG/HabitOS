package com.habitos.auth_service.mapper;

import org.springframework.stereotype.Component;

import com.habitos.auth_service.dto.request.RegisterRequest;
import com.habitos.auth_service.dto.response.UserResponse;
import com.habitos.auth_service.entity.User;

@Component
public class UserMapper {
    public User convertToEntity(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setFirstname(registerRequest.firstname());
        user.setLastname(registerRequest.lastname());
        return user;
    }

    public UserResponse convertToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.is_active(),
                user.getFirstname(),
                user.getLastname()
        );
    }
}
