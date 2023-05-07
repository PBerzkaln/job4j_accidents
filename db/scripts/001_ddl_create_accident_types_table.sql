CREATE TABLE IF NOT EXISTS accident_types
(
    id   serial primary key,
    type_name VARCHAR not null unique
);