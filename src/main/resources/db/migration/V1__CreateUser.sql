create table user
(
    id         bigint primary key auto_increment,
    name       varchar(100) not null default "",
    tel        varchar(20) unique,
    avatar_url varchar(1024),
    address    varchar(1024),
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);
insert into user(id, name, tel, avatar_url, address)
values (1, 'george', '13800000000', 'http://url', '火星');