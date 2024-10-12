package ru.yandex.practicum.devicedata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.devicedata.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}