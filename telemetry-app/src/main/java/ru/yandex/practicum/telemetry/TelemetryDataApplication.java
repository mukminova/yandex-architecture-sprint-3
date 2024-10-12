package ru.yandex.practicum.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
//@EnableSwagger2
public class TelemetryDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelemetryDataApplication.class, args);
    }
}
