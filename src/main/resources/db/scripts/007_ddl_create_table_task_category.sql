create table task_category
(
    task_id     int references tasks (id),
    category_id int references categories (id),
    primary key (task_id, category_id),
    unique (task_id, category_id)
);