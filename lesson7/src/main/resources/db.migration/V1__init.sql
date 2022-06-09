create table if not exists students
(
    id         bigserial primary key,
    name       VARCHAR(255),
    age        integer,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into students (name, age)
values ('Student 1', 20),
       ('Student 2', 40),
       ('Student 3', 60);
