create table if not exists todo_user
(
    id       serial primary key,
    name     text        not null,
    login    text unique not null,
    password text        not null
);