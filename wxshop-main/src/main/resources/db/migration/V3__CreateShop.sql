create table shop
(
    id            bigint primary key auto_increment,
    name          varchar(100),
    description   varchar(1024),
    img_url       varchar(1024),
    owner_user_id bigint,
    status        varchar(16),
    created_at    timestamp not null default now(),
    updated_at    timestamp not null default now()
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci;

insert into shop (id, name, description, img_url, owner_user_id, status)
values (1, 'shop1', 'desc1', 'url1', 1, 'ok');
insert into shop (id, name, description, img_url, owner_user_id, status)
values (2, 'shop2', 'desc2', 'url2', 1, 'ok');