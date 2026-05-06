package com.habitos.hatbit_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private UUID userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private HabitType type;   // BINARY (làm hay không làm) | COUNT (đếm số lần làm)  | TIME (làm bao nhiều phút)
    @Enumerated(EnumType.STRING)
    private Frequency frequency;   //DAILY | WEEKLY | CUSTOM
    private String frequency_days; // nếu CUSTOM (frequency) thì chọn ngày trong tuaan
    // giao diện
    private String icon;
    private String colour;
    @Column(name = "is_active")
    private String isActive;    //Soft delete — mặc định true
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
