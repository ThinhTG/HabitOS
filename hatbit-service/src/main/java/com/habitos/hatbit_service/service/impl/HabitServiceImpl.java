package com.habitos.hatbit_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.habitos.hatbit_service.dto.request.HabitCreateRequest;
import com.habitos.hatbit_service.dto.request.HabitUpdateRequest;
import com.habitos.hatbit_service.dto.response.HabitResponse;
import com.habitos.hatbit_service.mapper.HabitMapper;
import com.habitos.hatbit_service.repository.HabitRepository;
import com.habitos.hatbit_service.service.HabitService;

@Service
public class HabitServiceImpl implements HabitService {
	private static final String ACTIVE_FLAG = "true";
	private static final String INACTIVE_FLAG = "false";

	private final HabitRepository habitRepository;
	private final HabitMapper habitMapper;

	public HabitServiceImpl(HabitRepository habitRepository, HabitMapper habitMapper) {
		this.habitRepository = habitRepository;
		this.habitMapper = habitMapper;
	}

	@Override
	public HabitResponse createHabit(UUID userId, HabitCreateRequest request) {
		var habit = habitMapper.toEntity(userId, request);
		var saved = habitRepository.save(habit);
		return habitMapper.toResponse(saved);
	}

	@Override
	public HabitResponse getHabitById(UUID id) {
		var habit = habitRepository.findByIdAndIsActive(id, ACTIVE_FLAG)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Habit not found"));
		return habitMapper.toResponse(habit);
	}

	@Override
	public List<HabitResponse> getHabitsByUserId(UUID userId) {
		return habitRepository.findByUserIdAndIsActive(userId, ACTIVE_FLAG)
				.stream()
				.map(habitMapper::toResponse)
				.toList();
	}

	@Override
	public HabitResponse updateHabit(UUID id, HabitUpdateRequest request) {
		var habit = habitRepository.findByIdAndIsActive(id, ACTIVE_FLAG)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Habit not found"));
		habitMapper.applyUpdate(habit, request);
		habit.setUpdated_at(LocalDateTime.now());
		var saved = habitRepository.save(habit);
		return habitMapper.toResponse(saved);
	}

	@Override
	public void deleteHabit(UUID id) {
		var habit = habitRepository.findByIdAndIsActive(id, ACTIVE_FLAG)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Habit not found"));
		habit.setIsActive(INACTIVE_FLAG);
		habit.setUpdated_at(LocalDateTime.now());
		habitRepository.save(habit);
	}
}
