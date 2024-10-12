package ru.yandex.practicum.devicedata.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.devicedata.dto.DeviceDto;
import ru.yandex.practicum.devicedata.dto.DeviceStatusDto;
import ru.yandex.practicum.devicedata.service.DeviceService;

import java.util.Optional;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceData(@PathVariable("id") Long id) {
        logger.info("Get device data with id {}", id);
        return deviceService.getDeviceData(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeviceDto> updateDeviceStatus(@PathVariable("id") Long id,
                                                         @RequestBody DeviceStatusDto deviceDto) {
        logger.info("Updating  device data with id {}", id);

        return deviceService.updateDeviceStatus(id, deviceDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/commands")
    public ResponseEntity<Void> executeCommand(@PathVariable("id") Long id) {
        logger.info("Turning on heating system with id {}", id);
        deviceService.executeCommand(id);
        return ResponseEntity.noContent().build();
    }

}
