package com.habitos.auth_service.dto.response;

import java.util.UUID;

public record UserResponse (
        UUID id,
        String email,
        boolean isActive,
        String firstname,
        String lastname
){
}
