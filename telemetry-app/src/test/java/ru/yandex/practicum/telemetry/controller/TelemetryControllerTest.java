package ru.yandex.practicum.telemetry.controller;

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
import ru.yandex.practicum.telemetry.repository.TelemetryRepository;
import ru.yandex.practicum.telemetry.entity.Telemetry;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class TelemetryControllerTest {

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
    private TelemetryRepository telemetryRepository;

    @Test
    void testGetTelemetry() throws Exception {
        Telemetry telemetry = new Telemetry();
        telemetry.setDeviceId(1L);
        telemetry.setCommand("increase");
        telemetry.setStartValue(10.0);
        telemetry.setFinishValue(15.0);
        telemetry.setChangeDate(LocalDateTime.now());
        telemetry = telemetryRepository.save(telemetry);

        mockMvc.perform(get("/telemetry/{id}/latest", telemetry.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(telemetry.getId()))
                .andExpect(jsonPath("$.command").value("increase"))
                .andExpect(jsonPath("$.startValue").value("10.0"))
                .andExpect(jsonPath("$.finishValue").value("15.0"));
    }

}