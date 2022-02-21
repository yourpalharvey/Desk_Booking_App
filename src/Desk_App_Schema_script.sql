create database desk_booking;
use desk_booking;


drop table if exists comment;
drop table if exists office;
drop table if exists desk;
drop table if exists user;
drop table if exists booking;

create table if not exists comment (
    id int auto_increment primary key,
    comment varchar(255) null,
    created_time datetime null,
    name varchar(255) null
);

create table office (
    office_id int auto_increment primary key,
    office_name varchar(255) null
);

create table if not exists desk (
    desk_id int auto_increment primary key,
    desk_image_name varchar(255) null,
    desk_name varchar(255) null,
    desk_position varchar(255) null,
    desk_status varchar(255) null,
    desk_type varchar(255) null,
    monitor_option int not null,
    office_id int not null,
    constraint FKbshj16dgg95i9usk8b23yuqyy foreign key (office_id) references office (office_id)
);

create table if not exists user (
    user_id int auto_increment primary key,
    is_admin bit not null,
    password varchar(255) null,
    rating int not null,
    user_email varchar(255) null,
    username varchar(255) null
);

create table if not exists booking (
    booking_id int auto_increment primary key,
    approved bit not null,
    checked bit not null,
    start_date date null,
    desk_id int not null,
    user_id int not null,
    constraint FKkgseyy7t56x7lkjgu3wah5s3t foreign key (user_id) references user (user_id),
    constraint FKs8u9q0251nmlnoahefiakkmm foreign key (desk_id) references desk (desk_id)
);
