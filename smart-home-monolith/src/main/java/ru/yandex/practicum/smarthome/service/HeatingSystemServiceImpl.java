package ru.yandex.practicum.smarthome.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.smarthome.dto.DeviceDto;
import ru.yandex.practicum.smarthome.dto.DeviceStatusDto;
import ru.yandex.practicum.smarthome.dto.HeatingSystemDto;
import ru.yandex.practicum.smarthome.dto.TelemetryDto;
import ru.yandex.practicum.smarthome.entity.HeatingSystem;
import ru.yandex.practicum.smarthome.repository.HeatingSystemRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HeatingSystemServiceImpl implements HeatingSystemService {
    private final HeatingSystemRepository heatingSystemRepository;

    private static final Logger logger = LoggerFactory.getLogger(HeatingSystemServiceImpl.class);

    @Override
    public HeatingSystemDto getHeatingSystem(Long id) {
        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        return convertToDto(heatingSystem);
    }

    @Override
    public HeatingSystemDto updateHeatingSystem(Long id, HeatingSystemDto heatingSystemDto) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8082/devices/" + id + "/status";
        logger.info("Execute request for url {}", resourceUrl);
        DeviceStatusDto deviceStatusDto = new DeviceStatusDto();
        deviceStatusDto.setStatus(heatingSystemDto.isOn() ? "on" : "off");
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, new HttpEntity<>(deviceStatusDto), DeviceDto.class);

        HeatingSystem existingHeatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        existingHeatingSystem.setOn(heatingSystemDto.isOn());
        existingHeatingSystem.setTargetTemperature(heatingSystemDto.getTargetTemperature());


        HeatingSystem updatedHeatingSystem = heatingSystemRepository.save(existingHeatingSystem);
        return convertToDto(updatedHeatingSystem);
    }

    @Override
    public void turnOn(Long id) {
        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        heatingSystem.setOn(true);
        heatingSystemRepository.save(heatingSystem);
    }

    @Override
    public void turnOff(Long id) {
        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        heatingSystem.setOn(false);
        heatingSystemRepository.save(heatingSystem);
    }

    @Override
    public void setTargetTemperature(Long id, double temperature) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8081/telemetry/" + id;
        logger.info("Execute save telemetry request for url {}", resourceUrl);
        TelemetryDto telemetryDto = new TelemetryDto();
        telemetryDto.setCommand("changeTemperature");
        telemetryDto.setStartValue(getCurrentTemperature(id));
        telemetryDto.setFinishValue(temperature);
        telemetryDto.setChangeDate(LocalDateTime.now());
        restTemplate.exchange(resourceUrl, HttpMethod.POST, new HttpEntity<>(telemetryDto), TelemetryDto.class);

        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        heatingSystem.setTargetTemperature(temperature);
        heatingSystemRepository.save(heatingSystem);
    }

    @Override
    public Double getCurrentTemperature(Long id) {
        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        return heatingSystem.getCurrentTemperature();
    }

    private HeatingSystemDto convertToDto(HeatingSystem heatingSystem) {
        HeatingSystemDto dto = new HeatingSystemDto();
        dto.setId(heatingSystem.getId());
        dto.setOn(heatingSystem.isOn());
        dto.setTargetTemperature(heatingSystem.getTargetTemperature());
        return dto;
    }
}