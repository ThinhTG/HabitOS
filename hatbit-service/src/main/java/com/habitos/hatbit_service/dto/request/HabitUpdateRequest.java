package com.habitos.hatbit_service.dto.request;

import com.habitos.hatbit_service.entity.Frequency;
import com.habitos.hatbit_service.entity.HabitType;

public record HabitUpdateRequest(
        String name,
        String description,
        HabitType type,
        Frequency frequency,
        String frequencyDays,
        String icon,
        String colour,
        String isActive
) {
}
