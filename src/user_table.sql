create table user
(
    user_id    int auto_increment
        primary key,
    is_admin   bit          not null,
    password   varchar(255) null,
    rating     int          not null,
    user_email varchar(255) null,
    username   varchar(255) null
);