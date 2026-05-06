package com.habitos.hatbit_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.habitos.hatbit_service.entity.Habit;

@Repository
public interface HabitRepository extends JpaRepository<Habit, UUID> {
	List<Habit> findByUserIdAndIsActive(UUID userId, String isActive);

	Optional<Habit> findByIdAndIsActive(UUID id, String isActive);
}
