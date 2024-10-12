package ru.yandex.practicum.telemetry.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TelemetryDto {
    private Long id;
    private Long deviceId;
    private String command;
    private String status;
    private Double startValue;
    private Double finishValue;
    private LocalDateTime changeDate;
}

