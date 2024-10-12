CREATE TABLE IF NOT EXISTS telemetry_data (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL,
    command varchar(32) NOT NULL,
    start_value DOUBLE PRECISION,
    finish_value DOUBLE PRECISION,
    change_date TIMESTAMP NOT NULL
);
