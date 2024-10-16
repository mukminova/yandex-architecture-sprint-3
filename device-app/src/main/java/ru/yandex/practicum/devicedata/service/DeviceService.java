package ru.yandex.practicum.devicedata.service;

import ru.yandex.practicum.devicedata.dto.DeviceDto;
import ru.yandex.practicum.devicedata.dto.DeviceStatusDto;

import java.util.Optional;

public interface DeviceService {
    Optional<DeviceDto> getDeviceData(Long id);
    Optional<DeviceDto> updateDeviceStatus(Long id, DeviceStatusDto deviceDto);
    DeviceDto executeCommand(Long id);

}