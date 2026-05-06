package com.habitos.hatbit_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habitos.hatbit_service.dto.request.HabitCreateRequest;
import com.habitos.hatbit_service.dto.request.HabitUpdateRequest;
import com.habitos.hatbit_service.dto.response.HabitResponse;
import com.habitos.hatbit_service.service.HabitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/habits")
@Validated
public class HabitController {
    private final HabitService habitService;
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody HabitCreateRequest request) {
		UUID userId = resolveUserId();
		HabitResponse response = habitService.createHabit(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable UUID id) {
        return ResponseEntity.ok(habitService.getHabitById(id));
    }

    @GetMapping
        public ResponseEntity<List<HabitResponse>> getHabitsByUserId() {
		UUID userId = resolveUserId();
		return ResponseEntity.ok(habitService.getHabitsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(
            @PathVariable UUID id,
            @RequestBody HabitUpdateRequest request
    ) {
        return ResponseEntity.ok(habitService.updateHabit(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable UUID id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    private UUID resolveUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Unauthorized"
            );
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid;
        }
        try {
            return UUID.fromString(principal.toString());
        } catch (IllegalArgumentException ex) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid user"
            );
        }
    }
}
