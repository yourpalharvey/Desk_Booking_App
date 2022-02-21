create table desk
(
    desk_id         int auto_increment
        primary key,
    desk_image_name varchar(255) null,
    desk_name       varchar(255) null,
    desk_position   varchar(255) null,
    desk_status     varchar(255) null,
    desk_type       varchar(255) null,
    monitor_option  int          not null,
    office_id       int          not null,
    constraint FKbshj16dgg95i9usk8b23yuqyy
        foreign key (office_id) references office (office_id)
);
