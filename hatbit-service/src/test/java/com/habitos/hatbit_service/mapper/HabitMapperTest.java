package com.habitos.hatbit_service.mapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.habitos.hatbit_service.dto.request.HabitCreateRequest;
import com.habitos.hatbit_service.entity.Frequency;
import com.habitos.hatbit_service.entity.HabitType;

class HabitMapperTest {

    private final HabitMapper mapper = new HabitMapper();

    @Test
    void toEntity_setsExpectedFields() {
        UUID userId = UUID.randomUUID();
        HabitCreateRequest request = new HabitCreateRequest(
                "Drink Water",
                "Drink 2L daily",
                HabitType.BINARY,
                Frequency.DAILY,
                null,
                "water",
                "blue"
        );

		var habit = mapper.toEntity(userId, request);

        assertThat(habit.getUserId()).isEqualTo(userId);
        assertThat(habit.getName()).isEqualTo("Drink Water");
        assertThat(habit.getDescription()).isEqualTo("Drink 2L daily");
        assertThat(habit.getType()).isEqualTo(HabitType.BINARY);
        assertThat(habit.getFrequency()).isEqualTo(Frequency.DAILY);
        assertThat(habit.getIcon()).isEqualTo("water");
        assertThat(habit.getColour()).isEqualTo("blue");
        assertThat(habit.getIsActive()).isEqualTo("true");
        assertThat(habit.getCreated_at()).isNotNull();
        assertThat(habit.getUpdated_at()).isNotNull();
    }
}
