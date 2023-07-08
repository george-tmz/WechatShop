create table shopping_cart
(
    id         bigint primary key auto_increment,
    user_id    bigint,
    goods_id   bigint,
    number     int,
    status     varchar(16),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci;

insert into shopping_cart(user_id, goods_id, number, status)
values (1, 1, 100, 'ok');
insert into shopping_cart(user_id, goods_id, number, status)
values (1, 4, 200, 'ok');
insert into shopping_cart(user_id, goods_id, number, status)
values (1, 5, 300, 'ok');