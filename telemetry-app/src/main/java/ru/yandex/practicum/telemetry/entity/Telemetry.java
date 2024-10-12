package ru.yandex.practicum.telemetry.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry_data")
@Data
public class Telemetry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long deviceId;

    @Column(nullable = false)
    private String command;

    @Column(nullable = false)
    private Double startValue;

    @Column(nullable = false)
    private Double finishValue;

    @Column(nullable = false)
    private LocalDateTime changeDate;

}