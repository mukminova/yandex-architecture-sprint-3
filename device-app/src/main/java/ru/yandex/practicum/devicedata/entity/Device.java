package ru.yandex.practicum.devicedata.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "devices")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long houseId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    private DeviceType deviceType;

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private String status;

//    @Column(nullable = false)
//    private double currentTemperature;
//
//    @Column(nullable = false)
//    private LocalDateTime lastUpdated;
}