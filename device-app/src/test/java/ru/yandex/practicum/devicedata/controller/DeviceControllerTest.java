package ru.yandex.practicum.devicedata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.practicum.devicedata.entity.DeviceType;
import ru.yandex.practicum.devicedata.repository.DeviceRepository;
import ru.yandex.practicum.devicedata.entity.Device;
import ru.yandex.practicum.devicedata.repository.DeviceTypeRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class DeviceControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Test
    void testGetDevice() throws Exception {
        DeviceType deviceType = new DeviceType();
        deviceType.setTitle("conditioner");
        deviceTypeRepository.save(deviceType);

        Device device = new Device();
        device.setStatus("on");
        device.setHouseId(2L);
        device.setDeviceType(deviceType);
        device.setSerialNumber("daqj65ag25fms");
        device = deviceRepository.save(device);

        mockMvc.perform(get("/devices/{id}", device.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device.getId()))
                .andExpect(jsonPath("$.status").value("on"))
                .andExpect(jsonPath("$.serialNumber").value("daqj65ag25fms"));
    }

}