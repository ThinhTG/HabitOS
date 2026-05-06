package com.habitos.hatbit_service.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.habitos.hatbit_service.dto.request.HabitCreateRequest;
import com.habitos.hatbit_service.dto.request.HabitUpdateRequest;
import com.habitos.hatbit_service.dto.response.HabitResponse;
import com.habitos.hatbit_service.entity.Habit;

@Component
public class HabitMapper {

    public Habit toEntity(UUID userId, HabitCreateRequest request) {
        Habit habit = new Habit();
        habit.setUserId(userId);
        habit.setName(request.name());
        habit.setDescription(request.description());
        habit.setType(request.type());
        habit.setFrequency(request.frequency());
        habit.setFrequency_days(request.frequencyDays());
        habit.setIcon(request.icon());
        habit.setColour(request.colour());
    habit.setIsActive("true");
        habit.setCreated_at(LocalDateTime.now());
        habit.setUpdated_at(LocalDateTime.now());
        return habit;
    }

    public void applyUpdate(Habit habit, HabitUpdateRequest request) {
        if (request.name() != null) {
            habit.setName(request.name());
        }
        if (request.description() != null) {
            habit.setDescription(request.description());
        }
        if (request.type() != null) {
            habit.setType(request.type());
        }
        if (request.frequency() != null) {
            habit.setFrequency(request.frequency());
        }
        if (request.frequencyDays() != null) {
            habit.setFrequency_days(request.frequencyDays());
        }
        if (request.icon() != null) {
            habit.setIcon(request.icon());
        }
        if (request.colour() != null) {
            habit.setColour(request.colour());
        }
        if (request.isActive() != null) {
            habit.setIsActive(request.isActive());
        }
        habit.setUpdated_at(LocalDateTime.now());
    }

    public HabitResponse toResponse(Habit habit) {
        return new HabitResponse(
                habit.getId(),
                habit.getUserId(),
                habit.getName(),
                habit.getDescription(),
                habit.getType(),
                habit.getFrequency(),
                habit.getFrequency_days(),
                habit.getIcon(),
                habit.getColour(),
                habit.getIsActive(),
                habit.getCreated_at(),
                habit.getUpdated_at()
        );
    }
}
