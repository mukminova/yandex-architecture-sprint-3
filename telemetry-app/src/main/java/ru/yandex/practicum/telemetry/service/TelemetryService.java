package ru.yandex.practicum.telemetry.service;

import ru.yandex.practicum.telemetry.dto.TelemetryDto;

import java.util.List;

public interface TelemetryService {
    TelemetryDto getTelemetryLatestData(Long id);
    List<TelemetryDto> getTelemetryData(Long id);
    TelemetryDto save(TelemetryDto telemetryDto);
}