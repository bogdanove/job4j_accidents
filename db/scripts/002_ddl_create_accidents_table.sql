create table if not exists accidents(
    id serial primary key,
    name varchar not null,
    text varchar not null,
    address varchar not null,
    type_id int references types (id)
);