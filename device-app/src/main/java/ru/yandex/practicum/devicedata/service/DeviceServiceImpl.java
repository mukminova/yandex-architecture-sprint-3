package ru.yandex.practicum.devicedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.devicedata.dto.DeviceDto;
import ru.yandex.practicum.devicedata.dto.DeviceStatusDto;
import ru.yandex.practicum.devicedata.entity.Device;
import ru.yandex.practicum.devicedata.repository.DeviceRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public Optional<DeviceDto> getDeviceData(Long id) {
        return deviceRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public Optional<DeviceDto> updateDeviceStatus(Long id, DeviceStatusDto deviceDto) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setStatus(deviceDto.getStatus());
                    return deviceRepository.save(device);
                })
                .map(this::convertToDto);
    }

    @Override
    public DeviceDto executeCommand(Long id) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        Device updatedDevice = deviceRepository.save(existingDevice);
        return convertToDto(updatedDevice);
    }


    private DeviceDto convertToDto(Device device) {
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setHouseAddress("some address");
        dto.setDeviceType(device.getDeviceType().getTitle());
        dto.setSerialNumber(device.getSerialNumber());
        dto.setStatus(device.getStatus());
        return dto;
    }
}