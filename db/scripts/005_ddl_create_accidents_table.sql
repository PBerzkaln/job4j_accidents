CREATE TABLE accidents
(
    id      serial primary key,
    name    varchar,
    text    text,
    address varchar,
    type_id int REFERENCES accident_types (id)
);