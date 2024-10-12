package ru.yandex.practicum.telemetry.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.telemetry.dto.TelemetryDto;
import ru.yandex.practicum.telemetry.service.TelemetryService;

import java.util.List;

@RestController
@RequestMapping("/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    private static final Logger logger = LoggerFactory.getLogger(TelemetryController.class);

    @GetMapping("/{id}/latest")
    public ResponseEntity<TelemetryDto> getTelemetryLatestData(@PathVariable("id") Long deviceId) {
        logger.info("Fetching telemetry latest data by device id {}", deviceId);
        return ResponseEntity.ok(telemetryService.getTelemetryLatestData(deviceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TelemetryDto>> getTelemetryData(@PathVariable("id") Long deviceId) {
        logger.info("Fetching telemetry data by device id {}", deviceId);
        return ResponseEntity.ok(telemetryService.getTelemetryData(deviceId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<TelemetryDto> postTelemetryData(@PathVariable("id") Long deviceId,
                                                                @RequestBody TelemetryDto telemetryDto) {
        logger.info("Save telemetry data by device id {}", deviceId);
        telemetryDto.setDeviceId(deviceId);
        return ResponseEntity.ok(telemetryService.save(telemetryDto));
    }

}
