CREATE TABLE IF NOT EXISTS devices (
    id BIGSERIAL PRIMARY KEY,
    house_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    serial_number varchar(128) NOT NULL,
    status varchar(32) NOT NULL,
    current_value DOUBLE PRECISION NULL,
    target_value DOUBLE PRECISION NULL
);

CREATE TABLE IF NOT EXISTS device_type (
    id BIGSERIAL PRIMARY KEY,
    title varchar(128) NOT NULL
);
