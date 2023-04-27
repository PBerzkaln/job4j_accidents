CREATE TABLE accident_rules
(
    id   serial primary key,
    rule_name VARCHAR not null unique
);