package com.habitos.hatbit_service.service;

import java.util.List;
import java.util.UUID;

import com.habitos.hatbit_service.common.PageResponse;
import com.habitos.hatbit_service.dto.request.HabitCreateRequest;
import com.habitos.hatbit_service.dto.request.HabitUpdateRequest;
import com.habitos.hatbit_service.dto.response.HabitResponse;

public interface HabitService {
	HabitResponse createHabit(UUID userId, HabitCreateRequest request);

	HabitResponse getHabitById(UUID id);

	List<HabitResponse> getHabitsByUserId(UUID userId);

	HabitResponse updateHabit(UUID id, HabitUpdateRequest request);

	void deleteHabit(UUID id);

    PageResponse<HabitResponse> getUserHabits(UUID userId, int offset, int limit);
}
