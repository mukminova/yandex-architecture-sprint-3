package ru.yandex.practicum.devicedata.dto;

import lombok.Data;

@Data
public class DeviceDto {
    private Long id;
    private String houseAddress;
    private String deviceType;
    private String serialNumber;
    private String status;
}
