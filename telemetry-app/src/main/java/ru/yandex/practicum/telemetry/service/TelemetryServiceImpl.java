package ru.yandex.practicum.telemetry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.repository.TelemetryRepository;
import ru.yandex.practicum.telemetry.dto.TelemetryDto;
import ru.yandex.practicum.telemetry.entity.Telemetry;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryServiceImpl implements TelemetryService {
    private final TelemetryRepository telemetryRepository;
    
    @Override
    public TelemetryDto getTelemetryLatestData(Long id) {
        Telemetry telemetry = telemetryRepository.findFirstByDeviceIdOrderByChangeDateDesc(id)
                .orElseThrow(() -> new RuntimeException("Telemetry data not found"));
        return convertToDto(telemetry);
    }

    @Override
    public List<TelemetryDto> getTelemetryData(Long id) {
        return telemetryRepository.findAllByDeviceId(id)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public TelemetryDto save(TelemetryDto telemetryDto) {
        Telemetry saved = telemetryRepository.save(convertToEntity(telemetryDto));
        return convertToDto(saved);
    }


    private TelemetryDto convertToDto(Telemetry telemetry) {
        TelemetryDto dto = new TelemetryDto();
        dto.setId(telemetry.getId());
        dto.setCommand(telemetry.getCommand());
        dto.setDeviceId(telemetry.getDeviceId());
        dto.setStartValue(telemetry.getStartValue());
        dto.setFinishValue(telemetry.getFinishValue());
        dto.setChangeDate(telemetry.getChangeDate());
        return dto;
    }


    private Telemetry convertToEntity(TelemetryDto telemetryDto) {
        Telemetry entity = new Telemetry();
        entity.setId(telemetryDto.getId());
        entity.setCommand(telemetryDto.getCommand());
        entity.setDeviceId(telemetryDto.getDeviceId());
        entity.setStartValue(telemetryDto.getStartValue());
        entity.setFinishValue(telemetryDto.getFinishValue());
        entity.setChangeDate(telemetryDto.getChangeDate());
        return entity;
    }
}