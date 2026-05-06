package com.habitos.hatbit_service.dto.request;

import com.habitos.hatbit_service.entity.Frequency;
import com.habitos.hatbit_service.entity.HabitType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HabitCreateRequest(
        @NotBlank(message = "Name không được để trống")
        @Size(max = 255, message = "Name tối đa 255 ký tự")
        String name,

        @NotBlank(message = "Description không được để trống")
        @Size(max = 500, message = "Description tối đa 500 ký tự")
        String description,

        @NotNull(message = "Habit type không được để trống")
        HabitType type,

        @NotNull(message = "Frequency không được để trống")
        Frequency frequency,

        String frequencyDays,
        String icon,
        String colour
) {
}
