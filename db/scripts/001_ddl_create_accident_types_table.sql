CREATE TABLE accident_types
(
    id   serial primary key,
    type_name VARCHAR not null unique
);