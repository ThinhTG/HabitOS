package com.habitos.hatbit_service.controller;

import java.util.List;
import java.util.UUID;

import com.habitos.hatbit_service.config.SecurityUtils;
import com.habitos.hatbit_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final SecurityUtils securityUtils;

    public HabitController(HabitService habitService, SecurityUtils securityUtils) {
        this.habitService = habitService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HabitResponse>> createHabit(@Valid @RequestBody HabitCreateRequest request) {
		UUID userId = securityUtils.getCurrentUserId();
		HabitResponse response = habitService.createHabit(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitResponse>> getHabitById(@PathVariable UUID id) {
        HabitResponse response = habitService.getHabitById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
        public ResponseEntity<ApiResponse<List<HabitResponse>>> getHabitsByUserId() {
		UUID userId = securityUtils.getCurrentUserId();
        List<HabitResponse> response = habitService.getHabitsByUserId(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitResponse>> updateHabit(
            @PathVariable UUID id,
            @Valid @RequestBody HabitUpdateRequest request
    ) {
        HabitResponse response = habitService.updateHabit(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable UUID id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }
}
