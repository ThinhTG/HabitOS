package com.habitos.hatbit_service.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.habitos.hatbit_service.entity.Frequency;
import com.habitos.hatbit_service.entity.HabitType;

public record HabitResponse(
        UUID id,
        UUID userId,
        String name,
        String description,
        HabitType type,
        Frequency frequency,
        String frequencyDays,
        String icon,
        String colour,
        String isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
