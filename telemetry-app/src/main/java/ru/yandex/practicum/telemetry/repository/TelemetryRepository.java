package ru.yandex.practicum.telemetry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.telemetry.entity.Telemetry;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
    Optional<Telemetry> findFirstByDeviceIdOrderByChangeDateDesc(Long deviceId);
    List<Telemetry> findAllByDeviceId(Long deviceId);
}